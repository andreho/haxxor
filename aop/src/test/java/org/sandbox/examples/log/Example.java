package org.sandbox.examples.log;


import static org.sandbox.examples.log.Logging.trace;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 19:50.
 */
@TransparentLog
public class Example {
  //private static final Logger LOG = LoggerFactory.getLogger(Example.class);

  public void fooBar() {
    //LOG.trace("fooBar");
    trace("fooBar");
  }
}