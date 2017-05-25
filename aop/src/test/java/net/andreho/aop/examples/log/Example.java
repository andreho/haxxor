package net.andreho.aop.examples.log;

import java.util.logging.Logger;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 19:50.
 */
public class Example {
   @Log private static volatile Logger LOG;

   public void fooBar() {
      LOG.fine("OK");
   }
}
