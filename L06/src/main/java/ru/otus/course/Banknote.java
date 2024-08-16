package ru.otus.course;

/**
 * Класс, представляющий банкноту с определенным номиналом и количеством.
 */
public class Banknote {

  /**
   * Номинал банкноты.
   */
  private final int denomination;

  /**
   * Количество банкнот.
   */
  private int quantity;

  /**
   * Конструктор для создания банкноты.
   *
   * @param denomination Номинал банкноты.
   * @param quantity     Количество банкнот.
   */
  public Banknote(int denomination, int quantity) {
    this.denomination = denomination;
    this.quantity = quantity;
  }

  /**
   * Получить номинал банкноты.
   *
   * @return Номинал банкноты.
   */
  public int getDenomination() {
    return denomination;
  }

  /**
   * Получить количество банкнот.
   *
   * @return Количество банкнот.
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Установить количество банкнот.
   *
   * @param quantity Количество банкнот.
   */
  public void setQuantity(int quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Количество банкнот не может быть отрицательным.");
    }
    this.quantity = quantity;
  }

  /**
   * Увеличить количество банкнот.
   *
   * @param quantity Количество банкнот для добавления.
   */
  public void increaseQuantity(int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Количество банкнот должно быть положительным.");
    }
    this.quantity += quantity;
  }

  /**
   * Уменьшить количество банкнот.
   *
   * @param quantity Количество банкнот для уменьшения.
   */
  public void decreaseQuantity(int quantity) {
    if (quantity <= 0 || quantity > this.quantity) {
      throw new IllegalArgumentException("Неверное количество банкнот для уменьшения.");
    }
    this.quantity -= quantity;
  }

}
