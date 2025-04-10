import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PhoneDictionaryTest {

  @Test
  void setPhoneNumber() {
    PhoneDictionary testPhoneDictionary = new PhoneDictionary("+7XXXXXXXXXX", 1741884235650L);
    testPhoneDictionary.setPhoneNumber("+70000000000");
    assertEquals("+70000000000", testPhoneDictionary.getPhoneNumber());
  }

  @Test
  void getTimeModified() {
    PhoneDictionary testPhoneDictionary = new PhoneDictionary("+7XXXXXXXXXX", 1741884235650L);
    assertEquals(1741884235650L, testPhoneDictionary.getTimeModified());
  }

  @Test
  void getPhoneNumber() {
    PhoneDictionary testPhoneDictionary = new PhoneDictionary("+7XXXXXXXXXX", 1741884235650L);
    assertEquals("+7XXXXXXXXXX", testPhoneDictionary.getPhoneNumber());
  }
}