package net.andreho.haxxor.cgen.instr.array.store;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.abstr.AbstractArrayStoreInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class SASTORE
    extends AbstractArrayStoreInstruction {

  public SASTORE() {
    super(Opcodes.SASTORE);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.SASTORE();
  }

  @Override
  protected void checkArrayType(final Object arrayType, final int depth) {
    super.checkArrayType(arrayType, depth);
    if (!"[S".equals(arrayType)) {
      throw new IllegalArgumentException("Expected an short[] array type, but got: " + arrayType);
    }
  }
}
