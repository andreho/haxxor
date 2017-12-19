package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.api.HxMethodBody;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.instr.misc.FRAME;
import net.andreho.haxxor.spi.HxFrameCalculator;

import java.util.Collections;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 25.10.2017 at 01:15.
 */
public class DefaultFrameCalculator implements HxFrameCalculator {

  @Override
  public List<FRAME> calculate(final HxMethodBody methodBody) {
    if(!methodBody.isAvailable()) {
      return Collections.emptyList();
    }
    return calculate(methodBody.getFirst());
  }

  private List<FRAME> calculate(final HxInstruction instruction) {
    return Collections.emptyList();
  }
}
