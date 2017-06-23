package net.andreho.aop.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sandbox.elements.AnyType;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 18:31.
 */
@DisplayName("Test functionality of AopClassFileTransformer")
class AopClassFileTransformerTest {
/*
  -javaagent:C:/Workspace/java/haxxor/agent/build/libs/agent-1.0.0.jar
*/
  @Test
  void checkAgentLoad() {
    AnyType.staticMethod(42);
    try {
      AnyType.staticFailingMethod("ok");
    } catch (RuntimeException e) {
      e.printStackTrace();
    }

    AnyType anyType = new AnyType();

    anyType.complexMethod("ok", true, (byte) 1, (short) 1, (char) 1, 1, 1, 1, 1, 1, 1, 1);

    try {
      anyType.failingMethod();
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
  }
}