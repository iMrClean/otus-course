package ru.otus.course.transaction;

import java.util.function.Supplier;

public interface TransactionAction<T> extends Supplier<T> { }
