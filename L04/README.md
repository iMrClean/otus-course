# Результаты измерений работы сборщиков мусора

**Рабочая станция**: 13th Gen Intel(R) Core(TM) i9-13900KF 3.00 GHz, 64,0 ГБ

### Он поддерживает следующие параметры:

- **Размер кучи:** 256 MB, 512 MB, 1 GB, 1.5 GB, 2 GB
- **Тип сборщика мусора:** Serial, G1
- **Оптимизация:** boolean

## Скрипт запуска

Скрипт `run.sh` принимает три аргумента:

1. **Размер кучи** (256m, 512m, 1024m, 1536m, 2048m)
2. **Тип сборщика мусора** (Serial, G1)
3. **Включить оптимизацию** (true, false)

Скрипт `run_all.sh` запускает все варианты аргументов

## Результат будет записываться в resource в формате json

### Синтаксис

```shell
./run.sh <heap size> <gc type> <with optimization>
```

### Запуск с размером кучи 256 MB

#### Оптимизация выключена

```shell
./run.sh 256m G1 false
```

```shell
./run.sh 256m Serial false
```

#### Оптимизация включена

```shell
./run.sh 256m G1 true
```

```shell
./run.sh 256m Serial true
```

### Запуск с размером кучи 512 MB

#### Оптимизация выключена

```shell
./run.sh 512m G1 false
```

```shell
./run.sh 512m Serial false
```

#### Оптимизация включена

```shell
./run.sh 512m G1 true
```

```shell
./run.sh 512m Serial true
```

### Запуск с размером кучи 1 GB

#### Оптимизация выключена

```shell
./run.sh 1024m G1 false
```

```shell
./run.sh 1024m Serial false
```

#### Оптимизация включена

```shell
./run.sh 1024m G1 true
```

```shell
./run.sh 1024m Serial true
```

### Запуск с размером кучи 1.5 GB

#### Оптимизация выключена

```shell
./run.sh 1536m G1 false
```

```shell
./run.sh 1536m Serial false
```

#### Оптимизация включена

```shell
./run.sh 1536m G1 true
```

```shell
./run.sh 1536m Serial true
```

### Запуск с размером кучи 2 GB

#### Оптимизация выключена

```shell
./run.sh 2048m G1 false
```

```shell
./run.sh 2048m Serial false
```

#### Оптимизация включена

```shell
./run.sh 2048m G1 true
```

```shell
./run.sh 2048m Serial true
```
