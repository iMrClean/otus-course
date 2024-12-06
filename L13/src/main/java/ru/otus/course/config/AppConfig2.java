package ru.otus.course.config;

import ru.otus.course.appcontainer.api.AppComponent;
import ru.otus.course.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.course.service.*;

@AppComponentsContainerConfig(order = 1)
public class AppConfig2 {

  @AppComponent(order = 1, name = "playerService")
  public PlayerService playerService(IOService ioService) {
    return new PlayerServiceImpl(ioService);
  }

  @AppComponent(order = 2, name = "gameProcessor")
  public GameProcessor gameProcessor(IOService ioService, PlayerService playerService, EquationPreparer equationPreparer) {
    return new GameProcessorImpl(ioService, equationPreparer, playerService);
  }

}
