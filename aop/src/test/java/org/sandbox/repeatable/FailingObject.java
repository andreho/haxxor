package org.sandbox.repeatable;

import org.sandbox.aspects.repeatable.Repeatable;

/**
 * <br/>Created by a.hofmann on 10.07.2017 at 17:27.
 */
public class FailingObject {
  private final int mask;

  public FailingObject(final int mask) {
    this.mask = mask;
  }

  @Repeatable
  public void execute(int value) {
    executeInner(value);
  }

  @Repeatable
  private void executeInner(int value) {
    check(value, this.mask);
  }

  @Repeatable
  private static void check(final int value,
                            final int mask) {
    if((value & mask) != 0) {
      throw new IllegalStateException(value + " AND " + mask);
    }
  }
}
