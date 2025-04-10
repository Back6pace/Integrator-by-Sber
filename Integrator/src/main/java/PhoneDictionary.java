/**
 * Класс представляет собой запись в телефонном справочнике
 */
public class PhoneDictionary {
  /** Строковое представление номера телефона */
  private String phoneNumber;
  /** Переменная для хранения времени изменения в миллисекундах*/
  private long timeModified;
  /**
   * Конструктор класса
   * @param phoneNumber -> номер телефона, который будет храниться в справочнике
   * @param timeModified -> время последнего изменения номера телефона
   */
  public PhoneDictionary(String phoneNumber, long timeModified) {
    this.phoneNumber = phoneNumber;
    this.timeModified = timeModified;
  }

  /**
   * Сеттер, принимает новый номер телефона и устанавливает новое время изменения
   * @param phoneNumber -> новый номер телефона
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    timeModified = System.currentTimeMillis();
  }

  /**
   *  Возвращает время последнего изменения номера телефона
   * @return -> Время последнего изменения
   */
  public long getTimeModified() {
    return timeModified;
  }

  /**
   *  Возвращает номер телефона
   * @return -> номер телефона
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }
}
