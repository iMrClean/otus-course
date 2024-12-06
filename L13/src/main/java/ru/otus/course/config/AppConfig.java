package ru.otus.course.config;

import ru.otus.course.appcontainer.api.AppComponent;
import ru.otus.course.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.course.service.*;

@AppComponentsContainerConfig(order = 1)
public class AppConfig {

  @AppComponent(order = 0, name = "equationPreparer")
  public EquationPreparer equationPreparer() {
    return new EquationPreparerImpl();
  }

  @AppComponent(order = 1, name = "playerService")
  public PlayerService playerService(IOService ioService) {
    return new PlayerServiceImpl(ioService);
  }

  @AppComponent(order = 2, name = "gameProcessor")
  public GameProcessor gameProcessor(IOService ioService, PlayerService playerService, EquationPreparer equationPreparer) {
    return new GameProcessorImpl(ioService, equationPreparer, playerService);
  }

  @AppComponent(order = 0, name = "ioService")
  public IOService ioService() {
    return new IOServiceStreams(System.out, System.in);
  }

}
