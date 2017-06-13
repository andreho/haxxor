package net.andreho.haxxor.cgen.instr.array.load;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.abstr.AbstractArrayLoadInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class SALOAD
    extends AbstractArrayLoadInstruction {

  public SALOAD() {
    super(Opcodes.SALOAD);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.SALOAD();
  }

  @Override
  protected void checkArrayType(final Object arrayType) {
    super.checkArrayType(arrayType);
    if (!"[S".equals(arrayType)) {
      throw new IllegalArgumentException("Expected an array of current type: short[]");
    }
  }
}
