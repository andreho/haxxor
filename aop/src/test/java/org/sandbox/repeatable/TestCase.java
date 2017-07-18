package org.sandbox.repeatable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * -ea -javaagent:C:/Workspace/java/haxxor/agent/build/libs/agent-1.0.0.jar
 * <br/>Created by a.hofmann on 10.07.2017 at 19:12.
 */
@DisplayName("Installs Try-Catch blocks for specific exception-types")
class TestCase {
  @Test
  @DisplayName("Creates a repeatable stackable exception")
  void fails() {
    final FailingObject failingObject = new FailingObject(1);

    final RepeatableException repeatableException =
      assertThrows(RepeatableException.class, () -> failingObject.execute(1));

    assertNotNull(repeatableException);
    assertTrue(repeatableException.getExecutable() instanceof Method);
    assertEquals("execute", repeatableException.getExecutable().getName());
    assertTrue(Arrays.equals(new Object[]{1}, repeatableException.getArguments()));
  }
}
