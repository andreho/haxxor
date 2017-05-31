package net.andreho.haxxor.cgen.instr.array.load;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractArrayLoadInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class BALOAD
    extends AbstractArrayLoadInstruction {

  public BALOAD() {
    super(Opcodes.BALOAD);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.BALOAD();
  }

  @Override
  protected void checkArrayType(final Object arrayType) {
    super.checkArrayType(arrayType);
    if (!"[Z".equals(arrayType) && !"[B".equals(arrayType)) {
      throw new IllegalArgumentException("Expected an array of current type: boolean[] or byte[]");
    }
  }
}
