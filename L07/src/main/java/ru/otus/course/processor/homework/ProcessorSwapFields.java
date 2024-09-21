package ru.otus.course.processor.homework;

import ru.otus.course.model.Message;
import ru.otus.course.processor.Processor;

public class ProcessorSwapFields implements Processor {

  @Override
  public Message process(Message message) {
    return message.toBuilder()
      .field11(message.getField12())
      .field12(message.getField11())
      .build();
  }

}
