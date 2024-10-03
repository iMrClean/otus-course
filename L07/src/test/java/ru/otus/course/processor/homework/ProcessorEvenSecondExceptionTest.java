package ru.otus.course.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.course.model.Message;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class ProcessorEvenSecondExceptionTest {

  @Test
  @DisplayName("Нечётная секунда")
  void processAnOddSecond() {

    ProcessorEvenSecondException processor = new ProcessorEvenSecondException(() -> LocalDateTime.of(2000, Month.JANUARY, 1, 1, 1, 1));

    var message = new Message.Builder(1L).build();

    assertDoesNotThrow(() -> processor.process(message));
  }

  @Test
  @DisplayName("Чётная секунда")
  void processAnEvenSecond() {

    ProcessorEvenSecondException processor = new ProcessorEvenSecondException(() -> LocalDateTime.of(2000, Month.JANUARY, 1, 1, 1, 2));

    var message = new Message.Builder(1L).build();

    Throwable exception = assertThrows(RuntimeException.class, () -> processor.process(message));
    assertEquals("Exception for even second: 2", exception.getMessage());
  }

}
