package net.andreho.haxxor;


import org.junit.Ignore;
import org.junit.jupiter.api.Test;

/**
 * <br/>Created by a.hofmann on 31.05.2017 at 00:28.
 */
class SomeObservations {
   @Test
   @Ignore
   void printByteCodeOfObject() {
      Debugger.trace(Object.class);
   }


   @Test
   @Ignore
   void printInterfacesOfArray() {
      Object array =
         //new Object[0];
         new byte[0];
      for(Class<?> itf : array.getClass().getInterfaces()) {
         System.out.println(itf);
      }
   }
}