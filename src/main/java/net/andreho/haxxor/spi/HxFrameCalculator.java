package net.andreho.haxxor.spi;

import net.andreho.haxxor.api.HxMethodBody;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.instr.misc.FRAME;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 24.10.2017 at 18:50.
 */
@FunctionalInterface
public interface HxFrameCalculator {
  /**
   * @param methodBody to enrich with frame entries
   * @return a list with frames to inject (each frame contains the injection point in its {@link HxInstruction#getPrevious()} reference)
   */
  List<FRAME> calculate(HxMethodBody methodBody);
}
