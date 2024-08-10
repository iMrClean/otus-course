#!/bin/bash

# Определение размеров кучи
HEAP_SIZES=("256m" "512m" "1024m" "1536m" "2048m")

# Определение типов сборщиков мусора
GC_TYPES=("G1" "Serial")

# Определение состояний оптимизации
OPTIMIZATIONS=("true" "false")

# Путь к скрипту
SCRIPT="./run.sh"

# Выполнение всех комбинаций параметров
for HEAP_SIZE in "${HEAP_SIZES[@]}"; do
  for GC_TYPE in "${GC_TYPES[@]}"; do
    for OPTIMIZATION in "${OPTIMIZATIONS[@]}"; do
      echo "Running: $SCRIPT $HEAP_SIZE $GC_TYPE $OPTIMIZATION"
      $SCRIPT "$HEAP_SIZE" "$GC_TYPE" "$OPTIMIZATION"
    done
  done
done
