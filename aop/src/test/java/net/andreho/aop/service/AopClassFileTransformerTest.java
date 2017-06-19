package net.andreho.aop.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sandbox.elements.AnyType;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 18:31.
 */
@DisplayName("Test functionality of AopClassFileTransformer")
class AopClassFileTransformerTest {

  @Test
  void checkAgentLoad() {
    AnyType.controllableMethod(42);

    AnyType anyType = new AnyType();

    anyType.test("ok", true, (byte) 1, (short) 1, (char) 1, 1, 1, 1, 1, 1, 1, 1);
  }
}