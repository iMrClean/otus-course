package ru.otus.course.core.sessionmanager;

public interface TransactionRunner {

  <T> T doInTransaction(TransactionAction<T> action);

}
