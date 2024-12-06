package ru.otus.course;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.otus.course.appcontainer.AppComponentsContainerImpl;
import ru.otus.course.appcontainer.api.AppComponent;
import ru.otus.course.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.course.config.AppConfig;
import ru.otus.course.service.*;

import java.io.PrintStream;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class MainTest {

  @DisplayName("Из контекста тремя способами должен корректно доставаться компонент с проставленными полями")
  @ParameterizedTest(name = "Достаем по: {0}")
  @CsvSource(value = {"GameProcessor, ru.otus.course.service.GameProcessor", "GameProcessorImpl, ru.otus.course.service.GameProcessor", "gameProcessor, ru.otus.course.service.GameProcessor", "IOService, ru.otus.course.service.IOService", "IOServiceStreams, ru.otus.course.service.IOService", "ioService, ru.otus.course.service.IOService", "PlayerService, ru.otus.course.service.PlayerService", "PlayerServiceImpl, ru.otus.course.service.PlayerService", "playerService, ru.otus.course.service.PlayerService", "EquationPreparer, ru.otus.course.service.EquationPreparer", "EquationPreparerImpl, ru.otus.course.service.EquationPreparer", "equationPreparer, ru.otus.course.service.EquationPreparer"})
  void shouldExtractFromContextCorrectComponentWithNotNullFields(String classNameOrBeanId, Class<?> rootClass) throws Exception {
    var ctx = new AppComponentsContainerImpl(AppConfig.class);

    assertThat(classNameOrBeanId).isNotEmpty();
    Object component;
    if (classNameOrBeanId.charAt(0) == classNameOrBeanId.toUpperCase().charAt(0)) {
      Class<?> gameProcessorClass = Class.forName("ru.otus.course.service." + classNameOrBeanId);
      assertThat(rootClass).isAssignableFrom(gameProcessorClass);

      component = ctx.getAppComponent(gameProcessorClass);
    } else {
      component = ctx.getAppComponent(classNameOrBeanId);
    }
    assertThat(component).isNotNull();
    assertThat(rootClass).isAssignableFrom(component.getClass());

    var fields = Arrays.stream(component.getClass().getDeclaredFields()).filter(f -> !Modifier.isStatic(f.getModifiers())).peek(f -> f.setAccessible(true)).toList();

    for (var field : fields) {
      var fieldValue = field.get(component);
      assertThat(fieldValue).isNotNull().isInstanceOfAny(IOService.class, PlayerService.class, EquationPreparer.class, PrintStream.class, Scanner.class);
    }
  }

  @DisplayName("В контексте не должно быть компонентов с одинаковым именем")
  @Test
  void shouldNotAllowTwoComponentsWithSameName() {
    assertThatCode(() -> new AppComponentsContainerImpl(ConfigWithTwoComponentsWithSameName.class)).isInstanceOf(Exception.class);
  }

  @DisplayName("При попытке достать из контекста отсутствующий или дублирующийся компонент, должно выкидываться исключение")
  @Test
  void shouldThrowExceptionWhenContainerContainsMoreThanOneOrNoneExpectedComponents() {
    var ctx = new AppComponentsContainerImpl(ConfigWithTwoSameComponents.class);

    assertThatCode(() -> ctx.getAppComponent(EquationPreparer.class)).isInstanceOf(Exception.class);

    assertThatCode(() -> ctx.getAppComponent(PlayerService.class)).isInstanceOf(Exception.class);

    assertThatCode(() -> ctx.getAppComponent("equationPreparer3")).isInstanceOf(Exception.class);
  }

  @AppComponentsContainerConfig(order = 1)
  public static class ConfigWithTwoComponentsWithSameName {
    public ConfigWithTwoComponentsWithSameName() {
    }

    @AppComponent(order = 1, name = "equationPreparer")
    public EquationPreparer equationPreparer1() {
      return new EquationPreparerImpl();
    }

    @AppComponent(order = 1, name = "equationPreparer")
    public IOService ioService() {
      return new IOServiceStreams(System.out, System.in);
    }
  }

  @AppComponentsContainerConfig(order = 1)
  public static class ConfigWithTwoSameComponents {

    @AppComponent(order = 1, name = "equationPreparer1")
    public EquationPreparer equationPreparer1() {
      return new EquationPreparerImpl();
    }

    @AppComponent(order = 1, name = "equationPreparer2")
    public EquationPreparer equationPreparer2() {
      return new EquationPreparerImpl();
    }
  }

}
