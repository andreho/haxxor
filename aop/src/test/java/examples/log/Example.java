package examples.log;


import static examples.log.Logging.trace;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 19:50.
 */
@Log
public class Example {
  //private static final Logger LOG = LoggerFactory.getLogger(Example.class);

  public void fooBar() {
    //LOG.trace("fooBar");
    trace("fooBar");
  }
}
