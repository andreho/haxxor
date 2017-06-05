package net.andreho.haxxor.cgen.instr.cflow;

import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.Frames;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public class FRAME
    extends AbstractInstruction {

  private final Frames type;

  //----------------------------------------------------------------------------------------------------------------
  private final int nLocal;
  private final Object[] local;
  private final int nStack;
  private final Object[] stack;

  public FRAME(final Frames type, final int nLocal, final Object[] local, final int nStack, final Object[] stack) {
    super(-1);
    this.type = type;
    this.nLocal = nLocal;
    this.local = local;
    this.nStack = nStack;
    this.stack = stack;
  }

  //----------------------------------------------------------------------------------------------------------------

  @Override
  public void dumpTo(final Context context, final CodeStream codeStream) {
    codeStream.FRAME(this.type, this.nLocal, this.local, this.nStack, this.stack);
  }

  @Override
  public List<Object> apply(final Context context) {
    return NO_STACK_PUSH;
  }

  @Override
  public int getStackPopCount() {
    return 0;
  }

  //----------------------------------------------------------------------------------------------------------------

  public Frames getType() {
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
}
