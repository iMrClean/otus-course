package ru.otus.course;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ./gradlew build
 * -> build/libs/L01-fat.jar
 * -> build/libs/L01-gradle.jar
 */
@SuppressWarnings("java:S106")
public class Application {

  public static void main(String... args) {
    List<Integer> example = IntStream.range(0, 100)
      .boxed()
      .collect(Collectors.toList());

    System.out.println(Lists.reverse(example));
  }

}
