package ru.otus.course.transaction;

public interface TransactionManager {

  <T> T doInTransaction(TransactionAction<T> action);

}
