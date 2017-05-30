package net.andreho.aop.service;

import net.andreho.aop.transform.ClassTransformer;
import net.andreho.aop.transform.ordering.OrderUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.instrument.ClassFileTransformer;
import java.util.ServiceLoader;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 18:31.
 */
@DisplayName("Test functionality of DelegatingClassFileTransformer")
class DelegatingClassFileTransformerTest {

   @Test
   @DisplayName("Find all three class-transformers and check their order")
   void findTestClassTransformers() {
      final DelegatingClassFileTransformer delegate = new DelegatingClassFileTransformer();
      final ClassTransformer[] transformers = delegate.locateClassfileTransformers(getClass().getClassLoader());

      assertThat(transformers)
         .hasSize(3)
         .doesNotContainNull()
         .hasOnlyElementsOfType(ClassTransformer.class)
         .isSortedAccordingTo(OrderUtils.comparator());
   }

   @Test
   @DisplayName("The delegating class-file-transformer must be visible from ServiceLoader")
   void findService() {
      final ServiceLoader<ClassFileTransformer> loader =
         ServiceLoader.load(ClassFileTransformer.class, getClass().getClassLoader());

      assertThat(loader)
         .hasSize(1)
         .hasOnlyElementsOfType(DelegatingClassFileTransformer.class);
   }
}