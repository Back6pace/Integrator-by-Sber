import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Класс, реализующий метод заполнения телефонного справочника в многопоточной среде.
 * Использует интерфейс {@link Runnable} для выполнения в отдельных потоках.
 * Заполняет телефонный справочник номерами в заданном диапазоне.
 */
public class PhoneDictionaryFiller implements Runnable {
  /** Начальный индекс для заполения */
  private final int start;
  /** Конечный индекс для заполения */
  private final int end;
  /** Потокобезопасная хэш-таблица = телефонный справочник*/
  private final ConcurrentHashMap<String, PhoneDictionary> phoneDictionary;
  /** Генератор случайных чисел для создания телефонных номеров. */
  private static final Random RANDOM = new Random();
  /** Логгер для записи информации о процессе заполнения. */
  private static final Logger LOGGER = Logger.getLogger(PhoneDictionaryFiller.class.getName());

  /**
   * Создает объект для заполнения телефонного справочника
   *
   * @param start начальный индекс диапазона записей
   * @param end конечный индекс диапазона записей (не включительно)
   * @param phoneDictionary потокобезопасный справочник для хранения номеров
   */
  public PhoneDictionaryFiller(int start, int end, ConcurrentHashMap<String, PhoneDictionary> phoneDictionary) {
    this.start = start;
    this.end = end;
    this.phoneDictionary = phoneDictionary;
  }
  /**
   * Метод, выполняемый в отдельном потоке.
   * Заполняет телефонный справочник номерами пользователей в заданном диапазоне.
   */
  @Override
  public void run() {
    LOGGER.fine("Thread " + Thread.currentThread().getName() + " filling range " + start + " to " + (end - 1));
    for(int i = start; i < end; i++) {
      String name = "User" + i;
      String phone = generatePhoneNumber();
      phoneDictionary.put(name, new PhoneDictionary(phone, System.currentTimeMillis()));
    }
    LOGGER.fine("Thread " + Thread.currentThread().getName() + " completed filling range");
  }

  /**
   * Генерирует случайный телефонный номер в формате +7XXXXXXXXX.
   *
   * @return строка с телефонным номером
   */
  private static String generatePhoneNumber(){
    return "+7" + String.format("%09d", RANDOM.nextInt(1_000_000_000));
  }
}
