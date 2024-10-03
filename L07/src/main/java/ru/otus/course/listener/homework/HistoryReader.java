package ru.otus.course.listener.homework;

import ru.otus.course.model.Message;

import java.util.Optional;

public interface HistoryReader {

  Optional<Message> findMessageById(long id);

}
