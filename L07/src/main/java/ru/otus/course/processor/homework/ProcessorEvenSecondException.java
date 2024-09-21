package ru.otus.course.processor.homework;

import ru.otus.course.model.Message;
import ru.otus.course.processor.Processor;

import java.time.LocalDateTime;

public class ProcessorEvenSecondException implements Processor {

  @Override
  public Message process(Message message) {
    if (LocalDateTime.now().getSecond() % 2 == 0) {
      throw new RuntimeException("Обработка сообщения невозможна в четную секунду");
    }

    return message;
  }

}
