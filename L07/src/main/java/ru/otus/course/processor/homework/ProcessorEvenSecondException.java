package ru.otus.course.processor.homework;

import ru.otus.course.model.Message;
import ru.otus.course.processor.Processor;

public class ProcessorEvenSecondException implements Processor {

  private final DateTimeProvider dateTimeProvider;

  public ProcessorEvenSecondException(DateTimeProvider dateTimeProvider) {
    this.dateTimeProvider = dateTimeProvider;
  }

  @Override
  public Message process(Message message) {
    int second = dateTimeProvider.getDateTime().getSecond();

    if (second % 2 == 0) {
      throw new RuntimeException("Exception for even second: 2");
    }

    return message;
  }

}
