package ru.otus.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryManagerMXBean;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/*
-Xms256m
-Xmx256m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/heapdump.hprof
-XX:+UseG1GC
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
*/
public class CalcDemo {

  private static final Logger log = LoggerFactory.getLogger(CalcDemo.class);

  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Usage: java Main <withOptimization>");
      System.exit(1);
    }
    boolean withOptimization = Boolean.parseBoolean(args[0]);

    Utils.logMemoryUsage();

    long counter = 500_000_000;
    var summator = new Summator();
    long startTime = System.currentTimeMillis();

    var data = withOptimization ? null : new Data(); // Инициализация `Data` в зависимости от оптимизации
    for (int idx = 0; idx < counter; idx++) {
      if (withOptimization) {
        data = new Data(idx); // Создание нового объекта `Data` при оптимизации
      } else {
        data.set_value(idx); // Установка значения при отсутствии оптимизации
      }
      summator.calc(data, withOptimization);

      if (idx % 10_000_000 == 0) {
        log.info("{} current idx:{}", LocalDateTime.now(), idx);
      }
    }

    Utils.logResults(summator, startTime, withOptimization,
      Runtime.getRuntime().totalMemory() / (1024 * 1024),
      ManagementFactory.getGarbageCollectorMXBeans().stream().map(MemoryManagerMXBean::getName).collect(Collectors.joining())
    );
  }

}
