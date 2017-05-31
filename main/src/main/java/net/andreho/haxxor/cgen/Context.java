package net.andreho.haxxor.cgen;

import net.andreho.haxxor.cgen.instr.LABEL;

import java.util.Map;

/**
 * <br/>Created by a.hofmann on 12.03.2016.<br/>
 */
public interface Context {

  /**
   * @return context bound list with pushed types
   */
  StackPush getStackPush();

  /**
   * @return current state of local variables
   */
  Locals getLocals();

  /**
   * @return current state of the operand stack
   */
  Stack getStack();

  /**
   * @return currently visited labels
   */
  Map<LABEL, Instruction> getVisitedLabels();
}
