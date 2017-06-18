package net.andreho.aop.spi.impl.matchers;

import net.andreho.haxxor.spec.api.HxExecutable;

/**
 * <br/>Created by a.hofmann on 18.06.2017 at 07:08.
 */
public class PositionedMatcher<E extends HxExecutable<E>>
    extends AbstractMatcher<E> {

  private final int[] positions;
  private final boolean asRange;

  public PositionedMatcher(final int[] positions,
                           final boolean asRange) {
    this.positions = positions;
    this.asRange = asRange;
  }

  @Override
  public boolean isAny() {
    return positions.length == 0;
  }

  @Override
  public boolean match(final E element) {
    if (isAny()) {
      return true;
    }

    final int[] positions = this.positions;

    if (asRange) {
      for (int i = 0, len = positions.length; (i + 1) < len; i += 2) {
        final int from = positions[i];
        final int to = positions[i + 1];

        if (!matchRange(from, to, element)) {
          return false;
        }
      }
    } else {
      for (int i = 0; i < positions.length; i++) {
        if (!element.hasParameterAt(positions[i])) {
          return false;
        }
      }
    }

    return true;
  }

  private boolean matchRange(final int from,
                             final int to,
                             final E element) {
    for (int idx = from; idx < to; idx++) {
      if (!element.hasParameterAt(idx)) {
        return false;
      }
    }
    return true;
  }
}
