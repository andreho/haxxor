package net.andreho.haxxor.cgen.instr;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public class BEGIN
    extends AbstractInstruction {

  public BEGIN() {
    super();
  }

  @Override
  public int getOpcode() {
    return -1;
  }

  @Override
  public boolean isBegin() {
    return true;
  }

  @Override
  public void visit(final HxCodeStream codeStream) {
    codeStream.BEGIN();
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
  }

  @Override
  public int getStackPushSize() {
    return 0;
  }

  @Override
  public int getStackPopSize() {
    return 0;
  }

  @Override
  public HxInstruction prepend(final HxInstruction inst) {
    throw new IllegalStateException("BEGIN must be the first instruction.");
  }

  @Override
  public void setPrevious(final HxInstruction previous) {
    throw new IllegalStateException("BEGIN must be the first instruction.");
  }

  @Override
  public HxInstruction remove() {
    throw new IllegalStateException("BEGIN instruction can't be removed.");
  }

  @Override
  public END clone() {
    throw new IllegalStateException("Not supported.");
  }

  @Override
  protected String print() {
    return getInstructionName();
  }
}
