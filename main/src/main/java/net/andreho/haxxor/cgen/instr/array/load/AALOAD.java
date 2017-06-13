package net.andreho.haxxor.cgen.instr.array.load;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.abstr.AbstractArrayLoadInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class AALOAD
    extends AbstractArrayLoadInstruction {

  public AALOAD() {
    super(Opcodes.AALOAD);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.AALOAD();
  }

  @Override
  protected void checkArrayType(final Object arrayType) {
    super.checkArrayType(arrayType);
    String array = arrayType.toString();

    if (array.length() == 2) {
      switch (array.charAt(1)) {
        case 'Z':
        case 'B':
        case 'S':
        case 'C':
        case 'I':
        case 'F':
        case 'J':
        case 'D':
          throw new IllegalArgumentException("Expected an reference array type, but got: " + arrayType);
      }
    }
  }
}
