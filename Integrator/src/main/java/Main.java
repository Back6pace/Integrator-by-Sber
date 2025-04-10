import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * <p>
 * Главный класс программы, реализующий телефонный справочник и его обновление в многопоточной среде.
 * <p>
 * Программа выполняет три задачи:<br>
 * 1. Заполняет справочник телефонных номеров.<br>
 * 2. Периодически обновляет случайные записи в справочнике.<br>
 * 3. Через заданные интервалы времени выводит записи, которые существовали M секунд назад.
 */
public class Main {
  /** Логгер для записи информации о работе программы. */
  private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
  /** Генератор случайных чисел для вычисления параметров программы и генерации номеров. */
  private static final Random RANDOM = new Random();
  /**
   * Точка входа в программу.
   *
   * @param args аргументы командной строки (не используются)
   */
  public static void main(String[] args) {
    FillPhoneDictionary fillPhoneDictionary = new FillPhoneDictionary();
    boolean filled = fillPhoneDictionary.fillPhoneDictionary();
    if(!filled){
      LOGGER.severe("Failed to initialize phone dictionary. Exiting.");
      return;
    }

    ConcurrentHashMap<String, PhoneDictionary> phoneDictionary = fillPhoneDictionary.getPhoneDictionary();
    LOGGER.info("Phone dictionary initialized with " + phoneDictionary.size() + " records");

    int N = RANDOM.nextInt(4500) + 500;
    int M = RANDOM.nextInt(30);
    int T = RANDOM.nextInt(240) + 60;
    LOGGER.info("Generated N = " + N + " ms, M = " + M + " sec, T = " + T + " sec");

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    Runnable updatePhone = () -> {
      int updateCount = RANDOM.nextInt(5000) + 1;
      for(int i = 0; i < updateCount; i++){
        String name = "User" + RANDOM.nextInt(100_000);
        phoneDictionary.computeIfPresent(name, (key, phoneNumber) -> {
          phoneNumber.setPhoneNumber(generatePhoneNumber());
          return phoneNumber;
        });
      }
    };

    Runnable printPhone = () -> {
      long mSecondAgo = System.currentTimeMillis() - (M * 1000L);
      LOGGER.info("Printing phone dictionary (records from " + M + " seconds ago):");
      phoneDictionary.forEach((name, phoneNumber) -> {
        if(phoneNumber.getTimeModified() <= mSecondAgo){
          System.out.println(name + " : " + phoneNumber.getPhoneNumber());
        }
      });
      System.out.flush();
    };

    ScheduledFuture<?> updateFuture = scheduler.scheduleAtFixedRate(updatePhone, 0, N, TimeUnit.MILLISECONDS);
    ScheduledFuture<?> printFuture = scheduler.scheduleAtFixedRate(printPhone, T, T, TimeUnit.SECONDS);

    scheduler.schedule(() -> {
      updateFuture.cancel(true);
      printFuture.cancel(true);
      scheduler.shutdown();
      LOGGER.info("Program finished execution.");
    }, T + 300, TimeUnit.SECONDS);
  }
  /**
   * Генерирует случайный номер телефона в формате +7XXXXXXXXX.
   *
   * @return строка с телефонным номером
   */
  private static String generatePhoneNumber(){
    return "+7" + String.format("%09d", RANDOM.nextInt(1_000_000_000));
  }
}
