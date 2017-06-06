package net.andreho.haxxor.spi;

/**
 * <br/>Created by a.hofmann on 04.06.2017 at 00:46.
 */
public class HxVerificationResult {
  private static final HxVerificationResult OK = new HxVerificationResult(false, "", null);

  /**
   * @return
   */
  public static HxVerificationResult ok() {
    return OK;
  }

  /**
   * @param message
   * @param target
   * @return
   */
  public static HxVerificationResult failed(String message, Object target) {
    return new HxVerificationResult(true, message, target);
  }

  private final boolean failed;
  private final String message;
  private final Object target;

  protected HxVerificationResult(final boolean failed,
                              final String message,
                              final Object target) {
    this.failed = failed;
    this.message = message;
    this.target = target;
  }

  /**
   * @return
   */
  public boolean hasFailed() {
    return failed;
  }

  /**
   * @return
   */
  public String getMessage() {
    return message;
  }

  /**
   * @return
   */
  public Object getTarget() {
    return target;
  }
}
