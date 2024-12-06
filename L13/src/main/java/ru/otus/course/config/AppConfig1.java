package ru.otus.course.config;


import ru.otus.course.appcontainer.api.AppComponent;
import ru.otus.course.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.course.service.EquationPreparer;
import ru.otus.course.service.EquationPreparerImpl;
import ru.otus.course.service.IOService;
import ru.otus.course.service.IOServiceStreams;

@AppComponentsContainerConfig(order = 0)
public class AppConfig1 {

  @AppComponent(order = 0, name = "equationPreparer")
  public EquationPreparer equationPreparer() {
    return new EquationPreparerImpl();
  }

  @SuppressWarnings("squid:S106")
  @AppComponent(order = 0, name = "ioService")
  public IOService ioService() {
    return new IOServiceStreams(System.out, System.in);
  }

}
