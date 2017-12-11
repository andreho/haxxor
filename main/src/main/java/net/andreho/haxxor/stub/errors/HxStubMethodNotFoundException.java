package net.andreho.haxxor.stub.errors;

/**
 * <br/>Created by a.hofmann on 10.12.2017 at 14:03.
 */
public class HxStubMethodNotFoundException
  extends RuntimeException {
  public HxStubMethodNotFoundException() {
  }
  public HxStubMethodNotFoundException(final String message) {
    super(message);
  }
  public HxStubMethodNotFoundException(final String message,
                                       final Throwable cause) {
    super(message, cause);
  }
  public HxStubMethodNotFoundException(final Throwable cause) {
    super(cause);
  }
}
