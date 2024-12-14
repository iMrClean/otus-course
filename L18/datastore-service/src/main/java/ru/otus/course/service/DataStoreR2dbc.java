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

  @Override
  public Mono<Message> saveMessage(Message message) {
    log.info("saveMessage:{}", message);
    return messageRepository.save(message);
  }

  @Override
  public Flux<Message> loadMessages(String roomId) {
    log.info("loadMessages roomId:{}", roomId);
    return messageRepository.findByRoomId(roomId).delayElements(Duration.of(3, SECONDS), workerPool);
  }

}
