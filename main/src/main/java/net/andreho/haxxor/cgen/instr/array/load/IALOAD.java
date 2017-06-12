package net.andreho.haxxor.cgen.instr.array.load;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractArrayLoadInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class IALOAD
    extends AbstractArrayLoadInstruction {

  public IALOAD() {
    super(Opcodes.IALOAD);
  }

  @Override
  public void dumpTo(HxComputingContext context, HxCodeStream codeStream) {
    codeStream.IALOAD();
  }

  @Override
  protected void checkArrayType(final Object arrayType) {
    super.checkArrayType(arrayType);
    if (!"[I".equals(arrayType)) {
      throw new IllegalArgumentException("Expected an array of current type: int[]");
    }
  }
}
