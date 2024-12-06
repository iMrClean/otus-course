package ru.otus.course.service;

import ru.otus.course.model.Equation;

import java.util.List;

public interface EquationPreparer {

  List<Equation> prepareEquationsFor(int base);

}
