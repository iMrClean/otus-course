package ru.otus.course.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.util.HtmlUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.course.domain.MessageDTO;
import ru.otus.course.exception.ChatException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

  private static final String TOPIC_TEMPLATE = "/topic/response.";

  private final WebClient datastoreClient;

  private final SimpMessagingTemplate template;

  @MessageMapping("/message.{roomId}")
  public void getMessage(@DestinationVariable("roomId") String roomId, MessageDTO message) {
    log.info("get message:{}, roomId:{}", message, roomId);
    saveMessage(roomId, message).subscribe(msgId -> log.info("message send id:{}", msgId));

    template.convertAndSend(String.format("%s%s", TOPIC_TEMPLATE, roomId), new MessageDTO(HtmlUtils.htmlEscape(message.text())));
  }

  @EventListener
  public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
    var genericMessage = (GenericMessage<byte[]>) event.getMessage();
    var simpDestination = (String) genericMessage.getHeaders().get("simpDestination");
    if (simpDestination == null) {
      log.error("Can not get simpDestination header, headers:{}", genericMessage.getHeaders());
      throw new ChatException("Can not get simpDestination header");
    }
    if (!simpDestination.startsWith(template.getUserDestinationPrefix())) {
      return;
    }
    var roomId = parseRoomId(simpDestination);
    log.info("subscription for:{}, roomId:{}", simpDestination, roomId);
//    /user/3c3416b8-9b24-4c75-b38f-7c96953381d1/topic/response.1

    getMessagesByRoomId(roomId)
      .doOnError(ex -> log.error("getting messages for roomId:{} failed", roomId, ex))
      .subscribe(message -> template.convertAndSend(simpDestination, message));
  }

  private long parseRoomId(String simpDestination) {
    try {
      var idxRoom = simpDestination.lastIndexOf(TOPIC_TEMPLATE);
      return Long.parseLong(simpDestination.substring(idxRoom).replace(TOPIC_TEMPLATE, ""));
    } catch (Exception ex) {
      log.error("Can not get roomId", ex);
      throw new ChatException("Can not get roomId");
    }
  }

  private Mono<Long> saveMessage(String roomId, MessageDTO message) {
    return datastoreClient
      .post()
      .uri(String.format("/msg/%s", roomId))
      .accept(MediaType.APPLICATION_JSON)
      .bodyValue(message)
      .exchangeToMono(response -> response.bodyToMono(Long.class));
  }


  private Flux<MessageDTO> getMessagesByRoomId(long roomId) {
    return datastoreClient
      .get()
      .uri(String.format("/msg/%s", roomId))
      .accept(MediaType.APPLICATION_NDJSON)
      .exchangeToFlux(response -> {
        if (response.statusCode().equals(HttpStatus.OK)) {
          return response.bodyToFlux(MessageDTO.class);
        } else {
          return response.createException().flatMapMany(Mono::error);
        }
      });
  }

}
