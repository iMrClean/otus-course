package ru.otus.course.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.course.model.Message;

public class ListenerPrinterConsole implements Listener {

  private static final Logger logger = LoggerFactory.getLogger(ListenerPrinterConsole.class);

  @Override
  public void onUpdated(Message message) {
    logger.info("oldMsg:{}", message);
  }

}
