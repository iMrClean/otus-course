package ru.otus.course.appcontainer;

import org.reflections.Reflections;
import ru.otus.course.appcontainer.api.AppComponent;
import ru.otus.course.appcontainer.api.AppComponentsContainer;
import ru.otus.course.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

  private static final String NOT_FOUND = "Component %s not found in context.";
  private static final String DUPLICATED = "Component %s is duplicated in context.";
  private static final String NOT_CONFIG = "Given class is not a valid config: %s";

  private final List<Object> appComponents = new ArrayList<>();
  private final Map<String, Object> appComponentsByName = new HashMap<>();

  public AppComponentsContainerImpl(Class<?>... initialConfigClasses) {
    processConfigurations(initialConfigClasses);
  }

  public AppComponentsContainerImpl(String initialConfigClassesPath) {
    var configClasses = findConfigClasses(initialConfigClassesPath);
    processConfigurations(configClasses);
  }

  @Override
  public <C> C getAppComponent(Class<C> componentClass) {
    List<Object> matchedComponents = appComponents.stream()
      .filter(component -> componentClass.isAssignableFrom(component.getClass()))
      .toList();

    if (matchedComponents.isEmpty()) {
      throw new RuntimeException(String.format(NOT_FOUND, componentClass.getName()));
    }

    if (matchedComponents.size() > 1) {
      throw new RuntimeException(String.format(DUPLICATED, componentClass.getName()));
    }

    return componentClass.cast(matchedComponents.get(0));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <C> C getAppComponent(String componentName) {
    var component = appComponentsByName.get(componentName);
    if (component == null) {
      throw new RuntimeException(String.format(NOT_FOUND, componentName));
    }
    return (C) component;
  }

  private void processConfigurations(Class<?>[] configClasses) {
    var sortedConfigs = sortConfigClasses(configClasses);
    sortedConfigs.forEach(this::processSingleConfig);
  }

  private Class<?>[] findConfigClasses(String packagePath) {
    return new Reflections(packagePath)
      .getTypesAnnotatedWith(AppComponentsContainerConfig.class)
      .toArray(Class<?>[]::new);
  }

  private List<Class<?>> sortConfigClasses(Class<?>[] configClasses) {
    return Arrays.stream(configClasses)
      .filter(clazz -> clazz.isAnnotationPresent(AppComponentsContainerConfig.class))
      .sorted(Comparator.comparingInt(
        clazz -> clazz.getAnnotation(AppComponentsContainerConfig.class).order()))
      .toList();
  }

  private void processSingleConfig(Class<?> configClass) {
    validateConfigClass(configClass);
    var sortedMethods = getSortedMethods(configClass);

    try {
      var configInstance = configClass.getConstructor().newInstance();
      for (Method method : sortedMethods) {
        registerComponent(configInstance, method);
      }
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException("Error processing configuration: " + configClass.getName(), e);
    }
  }

  private void registerComponent(Object configInstance, Method method) throws InvocationTargetException, IllegalAccessException {
    var componentName = method.getAnnotation(AppComponent.class).name();
    var parameters = resolveParameters(method);
    var component = method.invoke(configInstance, parameters);

    if (appComponentsByName.containsKey(componentName)) {
      throw new RuntimeException(String.format(DUPLICATED, componentName));
    }

    appComponents.add(component);
    appComponentsByName.put(componentName, component);
  }

  private Object[] resolveParameters(Method method) {
    return Arrays.stream(method.getParameterTypes())
      .map(this::getAppComponent)
      .toArray();
  }

  private List<Method> getSortedMethods(Class<?> configClass) {
    return Arrays.stream(configClass.getDeclaredMethods())
      .filter(method -> method.isAnnotationPresent(AppComponent.class))
      .sorted(Comparator.comparingInt(
        method -> method.getAnnotation(AppComponent.class).order()))
      .toList();
  }

  private void validateConfigClass(Class<?> configClass) {
    if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
      throw new IllegalArgumentException(String.format(NOT_CONFIG, configClass.getName()));
    }
  }

}
