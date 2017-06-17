package org.sandbox.elements;

import org.sandbox.examples.log.TransparentLog;

import static org.sandbox.examples.log.Logging.info;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 21:10.
 */
@TransparentLog
public class AnyType {

  public void test() {
    info("test");
  }
}
