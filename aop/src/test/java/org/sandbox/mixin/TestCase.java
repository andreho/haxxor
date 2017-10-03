package org.sandbox.mixin;

import net.andreho.haxxor.Debugger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * -ea -javaagent:C:/Workspace/java/haxxor/agent/build/libs/agent-1.0.0.jar
 * <br/>Created by a.hofmann on 30.09.2017 at 04:14.
 */
public class TestCase {
  @Test
  @DisplayName("Class must implement the defined interfaces of MIXIN")
  void testInstanceOf() {
    DynamicallyComposedClass instance = new DynamicallyComposedClass();
    Assertions.assertTrue(instance instanceof Named);
    Assertions.assertTrue(instance instanceof Identifiable);
  }

  @Test
  @DisplayName("Class must provide the same functionality as MIXIN and implement 'Named' interface")
  void testAccessToNamedProperties() {
    DynamicallyComposedClass instance = new DynamicallyComposedClass();
    Named named = (Named) instance;
    Assertions.assertEquals(null, named.getName());
    named.setName("successful");
    Assertions.assertEquals("successful", named.getName());
    Assertions.assertEquals("DynamicallyComposedClass{id=0, name='successful'}", named.toString());
  }

  @Test
  @DisplayName("Class must provide the same functionality as MIXIN and implement 'Identifiable' interface")
  void testAccessToIdentifiableProperties() {
    DynamicallyComposedClass instance = new DynamicallyComposedClass();
    Identifiable identifiable = (Identifiable) instance;
    Assertions.assertEquals(0, identifiable.getId());
  }

  @Test
  @Disabled
  void print() {
    Debugger.trace(SpecificMixin.class);
  }
}
