import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс созданный для заполнения телефонного справочника
 */
public class FillPhoneDictionary {

  /**
   * Общее количество записей которые должны быть в справочнике
   */
  private static final int RECORDS = 100_000;
  /**
   * Телефонный справочник, представленный в виде
   * ConcurrentHashMap(потокобезопасная коллекция) хранящая в себе: Имя
   * пользователя и телефонную запись
   */
  private final ConcurrentHashMap<String, PhoneDictionary> phoneDictionary = new ConcurrentHashMap<>();
  /** Логгер для записи информации о процессе заполнения. */
  private static final Logger LOGGER = Logger.getLogger(FillPhoneDictionary.class.getName());

  /**
   * Заполняет справочник телефонных номеров многопоточно.
   * Использует фиксированный пул потоков (4 потока) для распределения работы.
   * Каждый поток обрабатывает свою часть записей.
   *
   * @return -> true, если заполнение успешно завершилось в течение 10 секунд, иначе false.
   */
  public boolean fillPhoneDictionary() {
    int THREADS = 4;
    LOGGER.info("Starting to fill phone dictionary with " + RECORDS + " records using " + THREADS + " threads");
    ExecutorService executor = Executors.newFixedThreadPool(THREADS);

    for (int i = 0; i < THREADS; i++) {
      int BORDER_SIZE = RECORDS / THREADS;
      int start = i * BORDER_SIZE;
      int end = (i == THREADS - 1) ? RECORDS : (i + 1) * BORDER_SIZE;
      executor.submit(new PhoneDictionaryFiller(start, end, phoneDictionary));
    }
    executor.shutdown();
    try {
      boolean completed =  executor.awaitTermination(10, TimeUnit.SECONDS);
      if(completed) {
        LOGGER.info("Phone dictionary filled successfully, size: " + phoneDictionary.size());
      } else {
        LOGGER.warning("Phone dictionary filling timed out after 10 seconds");
      }
      return completed;
    } catch (InterruptedException e) {
      LOGGER.log(Level.SEVERE, "Phone dictionary filling thread interrupted", e);
      return false;
    }
  }

  /**
   *
   * @return -> Возвращает телефонный справочник
   */
  public ConcurrentHashMap<String, PhoneDictionary> getPhoneDictionary() {
    return phoneDictionary;
  }
}
