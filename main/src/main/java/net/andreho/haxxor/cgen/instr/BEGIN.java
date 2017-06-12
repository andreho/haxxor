package net.andreho.haxxor.cgen.instr;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public class BEGIN
    extends AbstractInstruction {

  public BEGIN() {
    super(-1);
  }

  @Override
  public boolean isBegin() {
    return true;
  }

  @Override
  public void dumpTo(final HxComputingContext context, final HxCodeStream codeStream) {
    codeStream.BEGIN();
  }

  @Override
  public List<Object> apply(final HxComputingContext context) {
    return NO_STACK_PUSH;
  }

  @Override
  public int getPushSize() {
    return 0;
  }

  @Override
  public int getPopSize() {
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
}
