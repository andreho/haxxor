package net.andreho.haxxor.stub.errors;

/**
 * <br/>Created by a.hofmann on 10.12.2017 at 14:03.
 */
public class HxStubFieldNotFoundException
  extends RuntimeException {
  public HxStubFieldNotFoundException() {
  }
  public HxStubFieldNotFoundException(final String message) {
    super(message);
  }
  public HxStubFieldNotFoundException(final String message,
                                      final Throwable cause) {
    super(message, cause);
  }
  public HxStubFieldNotFoundException(final Throwable cause) {
    super(cause);
  }
}
