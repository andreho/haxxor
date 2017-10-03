package net.andreho.haxxor.spi;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * A class to wrap a verification of a class prototype and its bytecode
 * <br/>Created by a.hofmann on 04.06.2017 at 00:46.
 */
public class HxVerificationResult implements Iterable<HxVerificationResult> {
  private static final HxVerificationResult OK = new HxVerificationResult(false, "Passed", null) {
    @Override
    public HxVerificationResult setNext(final HxVerificationResult next) {
      throw new UnsupportedOperationException();
    }
  };

  /**
   * @return singleton OK instance
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
  private final Optional<Object> target;
  protected HxVerificationResult next;

  protected HxVerificationResult(final boolean failed,
                                 final String message,
                                 final Object target) {
    this(failed, message, target, null);
  }

  protected HxVerificationResult(final boolean failed,
                                 final String message,
                                 final Object target,
                                 final HxVerificationResult next) {
    this.failed = failed;
    this.message = Objects.requireNonNull(message, "Verification's message can't be null.");
    this.target = Optional.ofNullable(target);
    this.next = next;
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
  public Optional<Object> getTarget() {
    return target;
  }

  /**
   * @return
   */
  public HxVerificationResult getNext() {
    return next;
  }

  /**
   * @return
   */
  public HxVerificationResult setNext(final HxVerificationResult next) {
    this.next = next;
    return this;
  }

  @Override
  public String toString() {
    return message;
  }

  @Override
  public Iterator<HxVerificationResult> iterator() {
    return new Iterator<HxVerificationResult>() {
      HxVerificationResult current = HxVerificationResult.this;

      @Override
      public boolean hasNext() {
        return current != null;
      }

      @Override
      public HxVerificationResult next() {
        if(!hasNext()) {
          throw new NoSuchElementException();
        }
        HxVerificationResult actual = this.current;
        this.current = actual.getNext();
        return actual;
      }
    };
  }
}
