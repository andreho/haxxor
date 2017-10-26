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
  private static final HxVerificationResult OK = new HxVerificationResult(null, null, "Passed") {
    @Override
    public void setNext(final HxVerificationResult next) {
      throw new UnsupportedOperationException("Only failed verification results may be chained together.");
    }
  };

  /**
   * @return singleton OK instance
   */
  public static HxVerificationResult ok() {
    return OK;
  }

  /**
   * @param verificator
   * @param target
   * @param message
   * @return
   */
  public static HxVerificationResult failed(HxVerificator<?> verificator,
                                            Object target,
                                            String message) {
    return new HxVerificationResult(verificator, target, message);
  }

  private final HxVerificator<?> verificator;
  private final String message;
  private final Object target;
  protected HxVerificationResult next;

  protected HxVerificationResult(final HxVerificator<?> verificator,
                                 final Object target,
                                 final String message) {
    this(verificator, target, message, null);
  }

  protected HxVerificationResult(final HxVerificator<?> verificator,
                                 final Object target,
                                 final String message,
                                 final HxVerificationResult next) {
    this.message = Objects.requireNonNull(message, "Verification's message can't be null.");
    this.verificator = verificator;
    this.target = target;
    this.next = next;
  }

  /**
   * @return
   */
  public boolean isPassed() {
    return this == ok();
  }

  /**
   * @return
   */
  public boolean isFailed() {
    return this != ok();
  }

  /**
   * @return associated verificator instance; may be null
   */
  public HxVerificator<?> getVerificator() {
    return verificator;
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
    return Optional.of(target);
  }

  /**
   * @return <b>true</b> if there is the next verification problem
   */
  public boolean hasNext() {
    return next != null;
  }

  /**
   * @return
   */
  public HxVerificationResult getNext() {
    return next;
  }

  /**
   * @param next encountered problem
   */
  public void setNext(final HxVerificationResult next) {
    this.next = next;
  }

  /**
   * @param next encountered problem
   * @return the given next verification result
   */
  public HxVerificationResult append(final HxVerificationResult next) {
    HxVerificationResult current = this;
    while(current.hasNext()) {
      current = current.getNext();
    }
    current.setNext(next);
    return next;
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
