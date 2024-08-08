package ru.otus.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Stepnin Konstantin. Created on 08.08.2024
 */
public class Utils {

  private static final Logger log = LoggerFactory.getLogger(Utils.class);

  public static void logMemoryUsage() {
    // Получение текущего размера хипа
    long heapSize = Runtime.getRuntime().totalMemory();
    long maxHeapSize = Runtime.getRuntime().maxMemory();
    long freeHeapSize = Runtime.getRuntime().freeMemory();

    // Получение списка сборщиков мусора
    List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();

    // Сборка информации о сборщиках мусора
    String gcInfo = gcBeans.stream().map(gcBean -> gcBean.getName() + " ").collect(Collectors.joining());

    // Вывод размеров и информации о сборщиках мусора в консоль
    log.info("Current heap size (totalMemory): {} MB", heapSize / (1024 * 1024));
    log.info("Maximum heap size (maxMemory): {} MB", maxHeapSize / (1024 * 1024));
    log.info("Free heap size (freeMemory): {} MB", freeHeapSize / (1024 * 1024));
    log.info("Garbage collectors: {}", gcInfo.trim());
  }

  public static void logResults(Summator summator, long startTime, boolean withOptimization, long heapSize, String gcType) {
    long delta = System.currentTimeMillis() - startTime;

    if (withOptimization) {
      logOptimizedResults(summator);
    } else {
      logDefaultResults(summator);
    }

    log.info("spend msec:{}, sec:{}", delta, (delta / 1000));

    writeResultsToJson(heapSize, gcType, withOptimization, delta / 1000);
  }

  private static void writeResultsToJson(long heapSize, String gcType, boolean withOptimization, long executionTimeInSeconds) {
    String json = String.format(
      """
        {
          "heap_size": "%d MB",
          "time": "%d sec",
          "gc": "%s",
          "optimization": %b
        }""",
      heapSize, executionTimeInSeconds, gcType, withOptimization
    );

    String opt = withOptimization ? "opt" : "non";
    String fileName = String.format("%s_%s_%dMB.json", gcType.replace(" ", "_"), opt, heapSize);

    // Определение пути к файлу
    String path = "src/main/resources/" + fileName;

    try (FileWriter file = new FileWriter(path)) {
      file.write(json);
    } catch (IOException e) {
      log.error("Error writing result to JSON", e);
    }
  }

  private static void logOptimizedResults(Summator summator) {
    log.info("PrevValue:{}", summator.get_prevValue());
    log.info("PrevPrevValue:{}", summator.get_prevPrevValue());
    log.info("SumLastThreeValues:{}", summator.get_sumLastThreeValues());
    log.info("SomeValue:{}", summator.get_someValue());
    log.info("Sum:{}", summator.get_sum());
  }

  private static void logDefaultResults(Summator summator) {
    log.info("PrevValue:{}", summator.getPrevValue());
    log.info("PrevPrevValue:{}", summator.getPrevPrevValue());
    log.info("SumLastThreeValues:{}", summator.getSumLastThreeValues());
    log.info("SomeValue:{}", summator.getSomeValue());
    log.info("Sum:{}", summator.getSum());
  }
}
