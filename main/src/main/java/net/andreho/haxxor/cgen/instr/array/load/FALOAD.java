package net.andreho.haxxor.cgen.instr.array.load;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractArrayLoadInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class FALOAD
    extends AbstractArrayLoadInstruction {

  public FALOAD() {
    super(Opcodes.FALOAD);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.FALOAD();
  }

  @Override
  protected void checkArrayType(final Object arrayType) {
    super.checkArrayType(arrayType);
    if (!"[F".equals(arrayType)) {
      throw new IllegalArgumentException("Expected an array of current type: float[]");
    }
  }
}
