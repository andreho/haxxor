package net.andreho.haxxor.cgen.instr.array.store;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.abstr.AbstractArrayStoreInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class BASTORE
    extends AbstractArrayStoreInstruction {

  public BASTORE() {
    super(Opcodes.BASTORE);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.BASTORE();
  }

  @Override
  protected void checkArrayType(final Object arrayType, final int depth) {
    super.checkArrayType(arrayType, depth);
    if (!"[Z".equals(arrayType) && !"[B".equals(arrayType)) {
      throw new IllegalArgumentException("Expected an boolean[] or byte[] array type, but got: " + arrayType);
    }
  }

}
