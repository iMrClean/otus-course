package ru.otus.course;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Slf4j
public class Main {

  private static final int MIN_VALUE = 1;
  private static final int MAX_VALUE = 10;
  private static final long DELAY = 1000;

  private static final Object lock = new Object();

  private static boolean isFirstThreadTurn = true;

  public static void main(String[] args) {
    var executor = Executors.newFixedThreadPool(2);

    var thread1 = CompletableFuture.runAsync(() -> runThread("Thread 1", true), executor);
    var thread2 = CompletableFuture.runAsync(() -> runThread("Thread 2", false), executor);

    CompletableFuture.allOf(thread1, thread2).join();
    executor.shutdown();
  }

  private static void runThread(String threadName, boolean isFirstThread) {
    int[] sequence = generateSequence();

    Arrays.stream(sequence)
      .forEach(num -> {
        synchronized (lock) {
          while (isFirstThreadTurn != isFirstThread) {
            pleaseWait();
          }

          log.info("{}: {}", threadName, num);
          sleep();
          isFirstThreadTurn = !isFirstThreadTurn;
          lock.notifyAll();
        }
      });
  }

  private static void pleaseWait() {
    try {
      lock.wait();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  private static void sleep() {
    try {
      Thread.sleep(DELAY);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  private static int[] generateSequence() {
    int[] sequence = new int[(MAX_VALUE - MIN_VALUE + 1) * 2 - 1];
    int index = 0;

    for (int i = MIN_VALUE; i <= MAX_VALUE; i++) {
      sequence[index++] = i;
    }

    for (int i = MAX_VALUE - 1; i >= MIN_VALUE; i--) {
      sequence[index++] = i;
    }

    return sequence;
  }

}
