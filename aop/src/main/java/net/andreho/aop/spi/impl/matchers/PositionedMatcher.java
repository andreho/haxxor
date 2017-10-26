package net.andreho.aop.spi.impl.matchers;

import net.andreho.haxxor.api.HxOrdered;

import java.util.Arrays;

import static java.util.Arrays.binarySearch;

/**
 * <br/>Created by a.hofmann on 18.06.2017 at 07:08.
 */
public class PositionedMatcher<I extends HxOrdered>
  extends AbstractMatcher<I> {

  private final int[] positions;
  private final boolean asRange;

  public PositionedMatcher(final int[] positions,
                           final boolean asRange) {
    this.positions = positions;
    this.asRange = asRange;
    if (!asRange) {
      Arrays.sort(positions);
    }
  }

  @Override
  public boolean isAny() {
    return positions.length == 0;
  }

  @Override
  public boolean matches(final I ordered) {
    if (isAny()) {
      return true;
    }

    final int[] positions = this.positions;
    if (asRange) {
      return matchRanged(ordered, positions);
    }
    return matchPresence(ordered, positions);
  }

  private boolean matchPresence(final I ordered,
                                final int[] positions) {
    for (int i = 0; i < positions.length; i++) {
      if (binarySearch(positions, ordered.getIndex()) < 0) {
        return false;
      }
    }
    return true;
  }

  private boolean matchRanged(final I ordered,
                              final int[] positions) {
    for (int i = 0, len = positions.length; (i + 1) < len; i += 2) {
      final int from = positions[i];
      final int to = positions[i + 1];

      if (!matchRange(from, to, ordered)) {
        return false;
      }
    }
    return true;
  }

  private boolean matchRange(final int from,
                             final int to,
                             final I ordered) {
    final int index = ordered.getIndex();
    return from >= index && index < to;
  }
}
