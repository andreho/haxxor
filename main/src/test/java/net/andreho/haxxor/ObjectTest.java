package net.andreho.haxxor;


import org.junit.jupiter.api.Test;

/**
 * <br/>Created by a.hofmann on 31.05.2017 at 00:28.
 */
class ObjectTest {
   @Test
   void printObject() {
      Debugger.trace(Object.class);
   }
}