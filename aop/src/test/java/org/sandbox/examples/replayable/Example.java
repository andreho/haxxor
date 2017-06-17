package org.sandbox.examples.replayable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 22:13.
 */
@Replayable
public class Example implements Callable<String> {

   @Override
   public String call() {
      ReplayableException cause;
      int retries = 3;

      do {
         try {
            return fooBar("Foo");
         } catch (ReplayableException r) {
            cause = r;
         }
      } while(retries-- > 0);

      throw new IllegalStateException("Unable to execute: "+cause);
   }

   private String fooBar(String name) {
      return fooBar1(name, 2);
   }

   private String fooBar1(String s, int i) {
      return fooBar2(s, i, Arrays.asList("Bar"));
   }

   private String fooBar2(String s, int i, List<String> list) {
      return "Result: " + s + list.get(i); //=> IndexOutOfBoundsException
   }

}
