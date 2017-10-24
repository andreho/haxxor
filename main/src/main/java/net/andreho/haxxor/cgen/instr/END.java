package net.andreho.haxxor.cgen.instr;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public class END
    extends AbstractInstruction {

  public END() {
    super();
  }

  @Override
  public boolean isEnd() {
    return true;
  }

  @Override
  public void visit(final HxCodeStream codeStream) {
    codeStream.END();
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return NO_STACK_PUSH;
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
  public void setNext(final HxInstruction next) {
    throw new IllegalStateException("END must be the last instruction.");
  }

  @Override
  public HxInstruction append(final HxInstruction inst) {
    throw new IllegalStateException("END must be the last instruction.");
  }

  @Override
  public HxInstruction remove() {
    throw new IllegalStateException("END instruction can't be removed.");
  }
}
