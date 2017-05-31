package net.andreho.haxxor.cgen.instr.jumps;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSingleOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class RET
    extends AbstractSingleOperandInstruction {

  public RET(int var) {
    super(Opcodes.RET, var);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.RET(this.operand);
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
