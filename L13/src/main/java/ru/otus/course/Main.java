package ru.otus.course;

import ru.otus.course.appcontainer.AppComponentsContainerImpl;
import ru.otus.course.appcontainer.api.AppComponentsContainer;
import ru.otus.course.config.AppConfig;
import ru.otus.course.config.AppConfig1;
import ru.otus.course.config.AppConfig2;
import ru.otus.course.service.GameProcessor;
import ru.otus.course.service.GameProcessorImpl;

public class Main {

  public static void main(String[] args) {
    // Опциональные варианты
//     AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig1.class, AppConfig2.class);
    // Обязательный вариант
    AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig.class);

    // Приложение должно работать в каждом из указанных ниже вариантов
    GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
//     GameProcessor gameProcessor = container.getAppComponent(GameProcessorImpl.class);
//     GameProcessor gameProcessor = container.getAppComponent("gameProcessor");

    gameProcessor.startGame();
  }

}
