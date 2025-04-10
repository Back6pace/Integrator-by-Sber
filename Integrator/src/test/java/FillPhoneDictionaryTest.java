import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FillPhoneDictionaryTest {

  @Test
  void fillPhoneDictionary() {
    FillPhoneDictionary fillPhoneDictionary = new FillPhoneDictionary();
    boolean flag = fillPhoneDictionary.fillPhoneDictionary();
    assertTrue(flag);
  }

  @Test
  void getPhoneDictionary() {
    FillPhoneDictionary fillPhoneDictionary = new FillPhoneDictionary();
    fillPhoneDictionary.fillPhoneDictionary();
    var dictionary = fillPhoneDictionary.getPhoneDictionary();
    assertNotNull(dictionary);
  }

  @Test
  void sizePhoneDictionary() {
    FillPhoneDictionary fillPhoneDictionary = new FillPhoneDictionary();
    fillPhoneDictionary.fillPhoneDictionary();
    var dictionary = fillPhoneDictionary.getPhoneDictionary();
    assertEquals(100000, dictionary.size());
  }
}