package org.sandbox.equals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * -ea -javaagent:C:/Workspace/java/haxxor/agent/build/libs/agent-1.0.0.jar
 * <br/>Created by a.hofmann on 05.07.2017 at 19:12.
 */
class TestCase {
  @Test
  @DisplayName("Creates dynamically equals() and hashCode() methods")
  void checkEquality() {
    final Color RED_A = new Color(255, 0, 0);
    final Color RED_B = new Color(255, 0, 0);

    final Color BLUE_A = new Color(0, 0, 255);
    final Color BLUE_B = new Color(0, 0, 255);

    assertEquals(RED_A, RED_B);
    assertEquals(RED_B, RED_A);
    assertEquals(RED_A.hashCode(), RED_B.hashCode());

    assertEquals(BLUE_A, BLUE_B);
    assertEquals(BLUE_B, BLUE_A);
    assertEquals(BLUE_A.hashCode(), BLUE_B.hashCode());

    assertNotEquals(RED_A, BLUE_A);
    assertNotEquals(BLUE_A, RED_A);
    assertNotEquals(BLUE_A.hashCode(), RED_A.hashCode());
  }
}
