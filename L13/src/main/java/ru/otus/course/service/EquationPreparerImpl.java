package ru.otus.course.service;

import ru.otus.course.model.DivisionEquation;
import ru.otus.course.model.Equation;
import ru.otus.course.model.MultiplicationEquation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EquationPreparerImpl implements EquationPreparer {

  @Override
  public List<Equation> prepareEquationsFor(int base) {
    var equations = new ArrayList<Equation>();
    for (int i = 1; i < 11; i++) {
      var multiplicationEquation = new MultiplicationEquation(base, i);
      var divisionEquation = new DivisionEquation(multiplicationEquation.getResult(), base);
      equations.add(multiplicationEquation);
      equations.add(divisionEquation);
    }
    Collections.shuffle(equations);
    return equations;
  }

}
