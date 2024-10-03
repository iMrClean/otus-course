package ru.otus.course.listener;

import ru.otus.course.model.Message;

public interface Listener {

  void onUpdated(Message message);

}
