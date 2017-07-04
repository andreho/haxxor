package org.sandbox.secured;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * <br/>Created by a.hofmann on 04.07.2017 at 22:39.
 */
public class TestCase {
  @BeforeAll
  static void prepareUsers() {
  }

  @Test
  void testSecurityManager() {
    Subject subject = new Subject("test", "klaus", "ralf", "udo");
    subject.setName("newTest");
  }
}
