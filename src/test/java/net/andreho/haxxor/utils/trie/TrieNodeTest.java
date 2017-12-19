package net.andreho.haxxor.utils.trie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <br/>Created by a.hofmann on 27.10.2017 at 14:23.
 */
class TrieNodeTest {

  private static final String HIGH_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String LOW_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
  private static final String DIGITS = "0123456789";
  private static final String SIGNS = "+-*/^~!?.,:;§$%&|()[]{}=<>'\"\\#";


  private static String randomString(String prefix, char[] alphabet, int length) {
    final ThreadLocalRandom random = ThreadLocalRandom.current();
    final StringBuilder stb = new StringBuilder(prefix);

    for(int i = prefix.length(); i < length; i++) {
      char c = alphabet[random.nextInt(alphabet.length)];
      stb.append(c);
    }
    return stb.toString();
  }

  private static final int TEST_RUNS = 10;
  private static final int TEST_SIZE = 100_000;
  private static final String PREFIX = "x";
  private static final int STRING_MIN_LENGTH = 4;
  private static final int STRING_MAX_LENGTH = 64;
  private static final char[] ALPHABET = LOW_ALPHABET.toCharArray();


  @Test
  void testBasicFunctionality() {
    String[] DATA = {
      "home", "homework", "homing", "hallo", "homeworking", "halloween"
    };

    TrieNode first = null;

    for (String key : DATA) {
      char[] chars = key.toCharArray();
      if(first == null) {
        first = new TrieNode(chars, chars.length, key);
        continue;
      }
      first.insert(chars, chars.length).setValue(key);
    }

    for (String key : DATA) {
      char[] chars = key.toCharArray();
      TrieNode node = first.insert(chars, chars.length);
      if(!Objects.equals(key, node.getValue())) {
        node = first.insert(chars, chars.length);
      }
      Assertions.assertEquals(key, node.getValue());
    }
  }

  @Test
  void testRandomPreparedEntries()
  throws InterruptedException {
    System.out.println("Start in 10 sec ...");
    Thread.sleep(1 * 1000);
    System.out.println("Start!");

    for(int i = 0; i<TEST_RUNS; i++) {
      final Map<String, char[]> TEST_DATA = new LinkedHashMap<>();
      fillMapWithRandomData(TEST_DATA);

      TrieNode first = null;
      long start = System.currentTimeMillis();

      for (Map.Entry<String, char[]> entry : TEST_DATA.entrySet()) {
        final String key = entry.getKey();
        final char[] chars = entry.getValue();

        if(first == null) {
          first = new TrieNode(chars, key.length(), key);
          continue;
        }

        first.insert(chars, chars.length).setValue(key);
      }

      long filled = System.currentTimeMillis();

      for (Map.Entry<String, char[]> entry : TEST_DATA.entrySet()) {
        String key = entry.getKey();
        char[] chars = entry.getValue();

        TrieNode node = first.insert(chars, chars.length);
        Assertions.assertEquals(key, node.getValue());
      }

      long read = System.currentTimeMillis();

      System.out.println(i+"#Time for insert: "+(filled - start) + "ms, time for read: " + (read - filled) +"ms");
    }
  }

  private static void fillMapWithRandomData(final Map<String, char[]> TEST_DATA) {
    final ThreadLocalRandom random = ThreadLocalRandom.current();
    for (int i = 0; i < TEST_SIZE; ) {
      int length = random.nextInt(STRING_MIN_LENGTH, STRING_MAX_LENGTH);
      String s = randomString(PREFIX, ALPHABET, length);

      if(TEST_DATA.putIfAbsent(s, s.toCharArray()) == null) {
        i++;
      }
    }
  }

  @Test
  void testRandomlyGeneratedEntries()
  throws InterruptedException {
    final ThreadLocalRandom random = ThreadLocalRandom.current();
    final List<String> list = new ArrayList<>(TEST_SIZE);
    for (int j = 0; j<TEST_SIZE; j++) {
      final String key = randomString(PREFIX, ALPHABET, random.nextInt(STRING_MIN_LENGTH, STRING_MAX_LENGTH));
      list.add(key);
    }
    final char[] chars = new char[STRING_MAX_LENGTH];

    System.gc();

    System.out.println("Start in 10 sec ...");
    Thread.sleep(1 * 1000);
    System.out.println("Start!");

    for(int i = 0; i<TEST_RUNS; i++) {
      TrieNode first = null;

      long start = System.currentTimeMillis();
      for (String key : list) {
        key.getChars(0, key.length(), chars, 0);

        if(first == null) {
          first = new TrieNode(chars, key.length(), key);
          continue;
        }

        first.insert(chars, key.length()).setValue(key);
      }
      long filled = System.currentTimeMillis();

      for (String key : list) {
        key.getChars(0, key.length(), chars, 0);

        TrieNode node = first.insert(chars, key.length());

        if(!Objects.equals(key, node.getValue())) {
          node = first.insert(chars, key.length());
        }

        Assertions.assertEquals(key, node.getValue());
      }
      long read = System.currentTimeMillis();

      System.out.println(i+"#Time for insert: "+(filled - start) + "ms, time for read: " + (read - filled) +"ms");
      System.gc();
      //Thread.sleep(3000);
    }
  }

}