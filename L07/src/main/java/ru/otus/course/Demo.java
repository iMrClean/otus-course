package ru.otus.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.course.handler.ComplexProcessor;
import ru.otus.course.listener.ListenerPrinterConsole;
import ru.otus.course.listener.homework.HistoryListener;
import ru.otus.course.model.Message;
import ru.otus.course.model.ObjectForMessage;
import ru.otus.course.processor.LoggerProcessor;
import ru.otus.course.processor.ProcessorConcatFields;
import ru.otus.course.processor.ProcessorUpperField10;
import ru.otus.course.processor.homework.ProcessorEvenSecondException;
import ru.otus.course.processor.homework.ProcessorSwapFields;

import java.time.LocalDateTime;
import java.util.List;

public class Demo {

  private static final Logger logger = LoggerFactory.getLogger(Demo.class);

  private static final boolean isHomework = true;

  public static void main(String[] args) {
    if (!isHomework) {
      processing();
    } else {
      processingHomework();
    }
  }

  private static void processing() {
    var processors = List.of(new ProcessorConcatFields(), new LoggerProcessor(new ProcessorUpperField10()));

    var complexProcessor = new ComplexProcessor(processors, ex -> {
    });
    var listenerPrinter = new ListenerPrinterConsole();
    complexProcessor.addListener(listenerPrinter);

    var message = new Message.Builder(1L)
      .field1("field1")
      .field2("field2")
      .field3("field3")
      .field6("field6")
      .field10("field10")
      .build();

    var result = complexProcessor.handle(message);
    logger.info("result:{}", result);

    complexProcessor.removeListener(listenerPrinter);
  }

  private static void processingHomework() {
    var processors = List.of(new ProcessorEvenSecondException(LocalDateTime::now), new LoggerProcessor(new ProcessorSwapFields()));

    var complexProcessor = new ComplexProcessor(processors, ex -> { });
    var listenerPrinter = new ListenerPrinterConsole();
    var historyListener = new HistoryListener();
    complexProcessor.addListener(listenerPrinter);
    complexProcessor.addListener(historyListener);
    var field13 = new ObjectForMessage();
    field13.setData(List.of("123", "456"));

    var message = new Message.Builder(1L)
      .field11("field11")
      .field12("field12")
      .field13(field13)
      .build();

    var result = complexProcessor.handle(message);
    logger.info("result:{}", result);

    complexProcessor.removeListener(listenerPrinter);
    complexProcessor.removeListener(historyListener);
  }

}
