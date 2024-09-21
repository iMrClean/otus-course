package ru.otus.course.handler;

import ru.otus.course.listener.Listener;
import ru.otus.course.model.Message;

public interface Handler {

  Message handle(Message msg);

  void addListener(Listener listener);

  void removeListener(Listener listener);

}
