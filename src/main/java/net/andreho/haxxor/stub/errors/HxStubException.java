package net.andreho.haxxor.stub.errors;

/**
 * <br/>Created by a.hofmann on 21.11.2017 at 16:16.
 */
public class HxStubException extends Exception {
  public HxStubException() {
  }

  public HxStubException(final String message) {
    super(message);
  }

  public HxStubException(final String message,
                         final Throwable cause) {
    super(message, cause);
  }

  public HxStubException(final Throwable cause) {
    super(cause);
  }
}
