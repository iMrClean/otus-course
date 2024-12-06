package ru.otus.course.service;

import lombok.RequiredArgsConstructor;
import ru.otus.course.model.GameResult;

@RequiredArgsConstructor
public class GameProcessorImpl implements GameProcessor {

  private static final String MSG_HEADER = "Проверка знаний таблицы умножения";
  private static final String MSG_INPUT_BASE = "Введите цифру от 1 до 10";
  private static final String MSG_RIGHT_ANSWER = "Верно\n";
  private static final String MSG_WRONG_ANSWER = "Не верно\n";

  private final IOService ioService;

  private final EquationPreparer equationPreparer;

  private final PlayerService playerService;

  @Override
  public void startGame() {
    ioService.out(MSG_HEADER);
    ioService.out("---------------------------------------");
    var player = playerService.getPlayer();
    var gameResult = new GameResult(player);

    int base = ioService.readInt(MSG_INPUT_BASE);
    var equations = equationPreparer.prepareEquationsFor(base);
    equations.forEach(e -> {
      boolean isRight = ioService.readInt(e.toString()) == e.getResult();
      gameResult.incrementRightAnswers(isRight);
      ioService.out(isRight ? MSG_RIGHT_ANSWER : MSG_WRONG_ANSWER);
    });
    ioService.out(gameResult.toString());
  }

}
