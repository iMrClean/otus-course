package ru.otus.course.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.course.listener.Listener;
import ru.otus.course.model.Message;
import ru.otus.course.processor.Processor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ComplexProcessor implements Handler {

  private static final Logger logger = LoggerFactory.getLogger(ComplexProcessor.class);

  private final List<Listener> listeners = new ArrayList<>();
  private final List<Processor> processors;
  private final Consumer<Exception> errorHandler;

  public ComplexProcessor(List<Processor> processors, Consumer<Exception> errorHandler) {
    this.processors = processors;
    this.errorHandler = errorHandler;
  }

  @Override
  public Message handle(Message msg) {
    Message newMsg = msg;
    for (Processor pros : processors) {
      try {
        newMsg = pros.process(newMsg);
      } catch (Exception e) {
        errorHandler.accept(e);
      }
    }
    notify(newMsg);

    return newMsg;
  }

  @Override
  public void addListener(Listener listener) {
    listeners.add(listener);
  }

  @Override
  public void removeListener(Listener listener) {
    listeners.remove(listener);
  }

  private void notify(Message msg) {
    for (Listener listener : listeners) {
      try {
        listener.onUpdated(msg);
      } catch (Exception e) {
        logger.error(e.getMessage());
      }
    }
  }

}
