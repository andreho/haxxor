package net.andreho.haxxor.cgen.instr.array.store;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.abstr.AbstractArrayStoreInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class DASTORE
    extends AbstractArrayStoreInstruction {

  public DASTORE() {
    super(Opcodes.DASTORE);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.DASTORE();
  }

  @Override
  protected void checkArrayType(final Object arrayType, final int depth) {
    super.checkArrayType(arrayType, depth);
    if (!"[D".equals(arrayType)) {
      throw new IllegalArgumentException("Expected an double[] array type, but got: " + arrayType);
    }
  }
}
