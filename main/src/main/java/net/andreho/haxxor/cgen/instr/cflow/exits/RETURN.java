package net.andreho.haxxor.cgen.instr.cflow.exits;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class RETURN
    extends AbstractInstruction {

  public RETURN() {
    super(Opcodes.RETURN);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.RETURN();
  }

  @Override
  public List<Object> apply(final Context context) {
    return NO_STACK_PUSH;
  }

  @Override
  public int getStackPopCount() {
    return 0;
  }
}
