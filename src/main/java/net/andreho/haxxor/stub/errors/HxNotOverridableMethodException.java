package net.andreho.haxxor.stub.errors;

/**
 * <br/>Created by a.hofmann on 10.12.2017 at 14:03.
 */
public class HxNotOverridableMethodException
  extends RuntimeException {
  public HxNotOverridableMethodException() {
  }
  public HxNotOverridableMethodException(final String message) {
    super(message);
  }
  public HxNotOverridableMethodException(final String message,
                                         final Throwable cause) {
    super(message, cause);
  }
  public HxNotOverridableMethodException(final Throwable cause) {
    super(cause);
  }
}
