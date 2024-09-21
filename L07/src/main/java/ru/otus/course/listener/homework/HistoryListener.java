package ru.otus.course.listener.homework;

import ru.otus.course.listener.Listener;
import ru.otus.course.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

  private final Map<Long, Message> history = new HashMap<>();

  @Override
  public void onUpdated(Message message) {
    history.put(message.getId(), message.clone());
  }

  @Override
  public Optional<Message> findMessageById(long id) {
    return Optional.ofNullable(history.get(id));
  }

}
