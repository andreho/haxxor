package net.andreho.haxxor.cgen.instr.arithmetic;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class LNEG
    extends AbstractZeroOperandInstruction {

  public LNEG() {
    super(Opcodes.LNEG);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.LNEG();
  }

  @Override
  public List<Object> apply(Context context) {
    return PUSH_LONG;
  }

  @Override
  public int getStackPopCount() {
    return 2;
  }
}