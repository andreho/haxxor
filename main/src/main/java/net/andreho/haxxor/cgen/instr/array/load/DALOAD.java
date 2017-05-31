package net.andreho.haxxor.cgen.instr.array.load;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractArrayLoadInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class DALOAD
    extends AbstractArrayLoadInstruction {

  public DALOAD() {
    super(Opcodes.DALOAD);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.DALOAD();
  }

  @Override
  protected void checkArrayType(final Object arrayType) {
    super.checkArrayType(arrayType);
    if (!"[D".equals(arrayType)) {
      throw new IllegalArgumentException("Expected an array of current type: double[]");
    }
  }
}
