package ru.otus.course.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.otus.course.domain.Message;
import ru.otus.course.repository.MessageRepository;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataStoreR2dbc implements DataStore {

  private final MessageRepository messageRepository;

  private final Scheduler workerPool;

  private static final String SPECIAL_ROOM_ID = "1408";

  @Override
  public Mono<Message> saveMessage(Message message) {
    log.info("saveMessage:{}", message);

    if (SPECIAL_ROOM_ID.equals(message.roomId())) {
      log.error("Messages cannot be saved to special room: {}", SPECIAL_ROOM_ID);
      return Mono.error(new IllegalArgumentException("Messages cannot be saved to room " + SPECIAL_ROOM_ID));
    }

    return messageRepository.save(message);
  }

  @Override
  public Flux<Message> loadMessages(String roomId) {
    log.info("loadMessages roomId:{}", roomId);

    if (SPECIAL_ROOM_ID.equals(roomId)) {
      log.info("loading all messages for special room: {}", SPECIAL_ROOM_ID);
      return loadAllMessages();
    }

    return messageRepository.findByRoomId(roomId).delayElements(Duration.of(1, SECONDS), workerPool);
  }

  @Override
  public Flux<Message> loadAllMessages() {
    log.info("loadAllMessages");
    return messageRepository.findAll().delayElements(Duration.of(1, SECONDS), workerPool);
  }

}
