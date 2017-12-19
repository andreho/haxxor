package net.andreho.haxxor.spi.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * <br/>Created by a.hofmann on 27.10.2017 at 18:05.
 */
class DefaultTrieBasedDeduplicationCacheTest {
  private static final String[] TEST_DATA = {
    "<init>",
    "()V",
    "Code",
    "LineNumberTable",
    "LocalVariableTable",
    "this",
    "Lexamples/Main;",
    "main",
    "([Ljava/lang/String;)V",
    "args",
    "[Ljava/lang/String;",
    "MethodParameters",
    "SourceFile",
    "Main.java",
    "java/lang/System",
    "Hello World!",
    "java/io/PrintStream",
    "examples/Main",
    "java/lang/Object",
    "java/lang/System",
    "out",
    "Ljava/io/PrintStream;",
    "java/io/PrintStream",
    "println",
    "(Ljava/lang/String;)V"
  };

  private static final int MAX_STRING_LEGTH =
    Stream.of(TEST_DATA)
          .max(Comparator.comparingInt(String::length))
          .get()
          .length();

  @Test
  public void test() {
    final DefaultTrieBasedDeduplicationCache cache = new DefaultTrieBasedDeduplicationCache();
    final List<String> list = new ArrayList<>(TEST_DATA.length);
    final char[] buf = new char[MAX_STRING_LEGTH];

    for(String string : TEST_DATA) {
      string.getChars(0, string.length(), buf, 0);
      String cached = cache.deduplicate(buf, string.length());
      Assertions.assertEquals(string, cached);
      list.add(cached);
    }

    for(int i = 0; i < TEST_DATA.length; i++) {
      String string = TEST_DATA[i];
      String current = list.get(i);

      string.getChars(0, string.length(), buf, 0);
      String cached = cache.deduplicate(buf, string.length());

      Assertions.assertEquals(string, cached);
      Assertions.assertTrue(current == cached);
    }

    cache.print(System.out);
  }
}