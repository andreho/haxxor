package net.andreho.haxxor.cgen.instr;

import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.Instruction;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public class END
    extends AbstractInstruction {

  public END() {
    super(-1);
  }

  //----------------------------------------------------------------------------------------------------------------

  @Override
  public void dumpTo(final Context context, final CodeStream codeStream) {
    codeStream.END();
  }

  @Override
  public List<Object> apply(final Context context) {
    return NO_STACK_PUSH;
  }

  @Override
  public int getStackPopCount() {
    return 0;
  }

  @Override
  public void setNext(final Instruction next) {
    throw new IllegalStateException("END must be the last instruction.");
  }

  @Override
  public Instruction append(final Instruction inst) {
    throw new IllegalStateException("END must be the last instruction.");
  }

  @Override
  public Instruction remove() {
    throw new IllegalStateException("END instruction can't be removed.");
  }
}
