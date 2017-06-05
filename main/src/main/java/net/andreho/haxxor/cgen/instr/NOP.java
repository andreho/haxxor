package net.andreho.haxxor.cgen.instr;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class NOP
    extends AbstractZeroOperandInstruction {

  public NOP() {
    super(Opcodes.NOP);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.NOP();
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