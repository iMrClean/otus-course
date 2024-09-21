package ru.otus.course.processor;

import ru.otus.course.model.Message;

public interface Processor {

  Message process(Message message);

}
