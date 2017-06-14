package net.andreho.haxxor.cgen.instr.array.store;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.abstr.AbstractArrayStoreInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class IASTORE
    extends AbstractArrayStoreInstruction {

  public IASTORE() {
    super(Opcodes.IASTORE);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IASTORE();
  }

  @Override
  protected void checkArrayType(final Object arrayType, final int depth) {
    super.checkArrayType(arrayType, depth);
    if (!"[I".equals(arrayType)) {
      throw new IllegalArgumentException("Expected an int[] array type, but got: " + arrayType);
    }
  }
}
