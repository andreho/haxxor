package net.andreho.haxxor.cgen.instr.arithmetic;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class FREM
    extends AbstractZeroOperandInstruction {

  public FREM() {
    super(Opcodes.FREM);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.FREM();
  }

  @Override
  public List<Object> apply(Context context) {
    return PUSH_FLOAT;
  }

  @Override
  public int getStackPopCount() {
    return 1 + 1;
  }
}
