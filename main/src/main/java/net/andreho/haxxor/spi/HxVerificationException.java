package net.andreho.haxxor.spi;

/**
 * <br/>Created by a.hofmann on 13.10.2017 at 16:23.
 */
public class HxVerificationException extends RuntimeException {
  private final HxVerificationResult result;

  public HxVerificationException(final HxVerificationResult result) {
    this.result = result;
  }

  public HxVerificationResult getResult() {
    return result;
  }
}
