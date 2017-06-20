package net.andreho.haxxor.cgen.instr.misc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxFrames;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionsType;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public class FRAME
    extends AbstractInstruction {

  private final HxFrames type;
  private final int nLocal;
  private final Object[] local;
  private final int nStack;
  private final Object[] stack;

  public FRAME(final HxFrames type, final int nLocal, final Object[] local, final int nStack, final Object[] stack) {
    super(-1);
    this.type = type;
    this.nLocal = nLocal;
    this.local = local;
    this.nStack = nStack;
    this.stack = stack;
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionsType.Special.FRAME;
  }

  public HxFrames getType() {
    return this.type;
  }

  public int getLocalsCount() {
    return this.nLocal;
  }

  public Object[] getLocals() {
    return this.local;
  }

  public int getStackLength() {
    return this.nStack;
  }

  public Object[] getStack() {
    return this.stack;
  }

  @Override
  public void visit(final HxCodeStream codeStream) {
    codeStream.FRAME(this.type, this.nLocal, this.local, this.nStack, this.stack);
  }

  @Override
  public List<Object> getStackPushList(final HxComputingContext context) {
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
}
