package net.andreho.haxxor.cgen.instr.misc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxFrames;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

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
    super();
    this.type = type;
    this.nLocal = nLocal;
    this.local = local;
    this.nStack = nStack;
    this.stack = stack;
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Special.FRAME;
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
}
