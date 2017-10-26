package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethodBody;
import net.andreho.haxxor.spi.HxFrameCalculator;

/**
 * <br/>Created by a.hofmann on 25.10.2017 at 01:15.
 */
public class DefaultFrameCalculator implements HxFrameCalculator {

  @Override
  public void calculate(final HxMethodBody methodBody) {
    if(!methodBody.isAvailable()) {
      return;
    }

    calculate(methodBody.getFirst());
  }

  private void calculate(final HxInstruction instruction) {
    
  }
}
