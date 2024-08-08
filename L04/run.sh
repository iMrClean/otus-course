#!/bin/bash

# Проверка на количество переданных аргументов
if [ "$#" -ne 3 ]; then
  echo "Usage: $0 <heap size> <gc type> <withOptimization>"
  echo "Heap size options: 256m, 512m, 1024m, 1.5g, 2048m"
  echo "GC options: Serial, G1"
  echo "Optimization: true or false"
  exit 1
fi

# Определите параметры из аргументов командной строки
HEAP_SIZE=$1
MAX_HEAP_SIZE=$1
GC_TYPE=$2
WITH_OPTIMIZATION=$3

# Определите путь к вашему Java-приложению и к JAR файлу
JAVA_APP_JAR="./build/libs/L04.jar"

# Определите путь к директории логов
LOG_DIR="../logs"

# Создайте директорию для логов, если она не существует
mkdir -p "$LOG_DIR"

# Определите параметры сборщика мусора на основе типа
case $GC_TYPE in
  Serial)
    GC_PARAMS="-XX:+UseSerialGC"
    ;;
  G1)
    GC_PARAMS="-XX:+UseG1GC"
    ;;
  *)
    echo "Invalid GC type: $GC_TYPE"
    echo "GC options: Serial, G1"
    exit 1
    ;;
esac

# Определите параметры логирования для JVM
LOGGING_PARAMS="-Xlog:gc=debug:file=$LOG_DIR/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m"

# Функция для проверки версии Java и установки JDK 21, если это необходимо
check_and_set_java_version() {
  JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
  REQUIRED_VERSION="21"

  if [[ "$JAVA_VERSION" != "$REQUIRED_VERSION" ]]; then
    echo "Current java version is $JAVA_VERSION"
    echo "Switching to Java $REQUIRED_VERSION."

    # Установите путь к JDK 21 здесь
    JDK21_PATH="$HOME/.jdks/corretto-21.0.3"

    if [ -d "$JDK21_PATH" ]; then
      export JAVA_HOME="$JDK21_PATH"
      export PATH="$JAVA_HOME/bin:$PATH"
    else
      echo "JDK 21 not found at $JDK21_PATH"
      exit 1
    fi
  fi
}

# Проверьте и установите правильную версию Java
check_and_set_java_version

# Запуск приложения с указанными параметрами JVM
if java \
  -Xms"$HEAP_SIZE" \
  -Xmx"$MAX_HEAP_SIZE" \
  -XX:+HeapDumpOnOutOfMemoryError \
  -XX:HeapDumpPath="$LOG_DIR/heapdump.hprof" \
  $GC_PARAMS \
  $LOGGING_PARAMS \
  -jar "$JAVA_APP_JAR" "$WITH_OPTIMIZATION"; then
  echo "Application ran successfully."
else
  echo "Application failed. Removing logs directory."
  rm -rf "$LOG_DIR"
fi
