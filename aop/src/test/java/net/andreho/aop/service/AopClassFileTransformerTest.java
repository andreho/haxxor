package net.andreho.aop.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sandbox.elements.AnyType;

import java.lang.instrument.ClassFileTransformer;
import java.util.ServiceLoader;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 18:31.
 */
@DisplayName("Test functionality of AopClassFileTransformer")
class AopClassFileTransformerTest {

  @Test
  @DisplayName("The delegating class-file-transformer must be visible from ServiceLoader")
  void findService() {
    final ServiceLoader<ClassFileTransformer> loader =
        ServiceLoader.load(ClassFileTransformer.class, getClass().getClassLoader());

    assertThat(loader)
        .hasSize(1)
        .hasOnlyElementsOfType(AopClassFileTransformer.class);
  }

  @Test
  void checkAgentLoad() {
    new AnyType();
  }
}