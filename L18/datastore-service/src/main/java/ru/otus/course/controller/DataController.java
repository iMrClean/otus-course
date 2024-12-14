package ru.otus.course.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.otus.course.domain.Message;
import ru.otus.course.domain.MessageDTO;
import ru.otus.course.service.DataStore;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DataController {

  private final DataStore dataStore;

  private final Scheduler workerPool;

  @PostMapping(value = "/msg/{roomId}")
  public Mono<Long> messageFromChat(@PathVariable("roomId") String roomId, @RequestBody MessageDTO messageDTO) {
    log.info("messageFromChat, roomId:{}", roomId);
    return Mono.just(new Message(null, roomId, messageDTO.text()))
      .doOnNext(msg -> log.info("messageFromChat:{}", msg))
      .flatMap(dataStore::saveMessage)
      .publishOn(workerPool)
      .doOnNext(msgSaved -> log.info("msgSaved id:{}", msgSaved.id()))
      .map(Message::id)
      .subscribeOn(workerPool);
  }

  @GetMapping(value = "/msg/{roomId}", produces = MediaType.APPLICATION_NDJSON_VALUE)
  public Flux<MessageDTO> getMessagesByRoomId(@PathVariable("roomId") String roomId) {
    return Mono.just(roomId)
      .doOnNext(room -> log.info("getMessagesByRoomId, room:{}", room))
      .flatMapMany(dataStore::loadMessages)
      .map(message -> new MessageDTO(message.text()))
      .doOnNext(dto -> log.info("dto:{}", dto))
      .subscribeOn(workerPool);
  }

}
