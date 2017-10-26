package net.andreho.haxxor.spi;

import net.andreho.haxxor.api.HxMethodBody;

/**
 * <br/>Created by a.hofmann on 24.10.2017 at 18:50.
 */
@FunctionalInterface
public interface HxFrameCalculator {

  /**
   * @param methodBody to enrich with frame entries
   */
  void calculate(HxMethodBody methodBody);
}
