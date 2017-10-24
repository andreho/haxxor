package net.andreho.haxxor.cgen;

import net.andreho.haxxor.cgen.instr.misc.LABEL;

import java.util.Map;

/**
 * <br/>Created by a.hofmann on 12.03.2016.<br/>
 */
public interface HxComputingContext {

  /**
   * @return context bound list with pushed types
   */
  HxStackPush getStackPush();

  /**
   * @return current state of local variables
   */
  HxLocals getLocals();

  /**
   * @return current state of the operand stack
   */
  HxStack getStack();

  /**
   * @return already visited labels
   */
  Map<LABEL, HxInstruction> getVisitedLabels();
}
