package net.andreho.haxxor.cgen.instr.constants.ldc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.constants.LDC;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 03:26.
 */
public class TypeLDC
    extends LDC<String> {
  public TypeLDC(final String typeDescriptor) {
    super(typeDescriptor, ConstantType.TYPE);
  }

  @Override
  public void visit(final HxCodeStream codeStream) {
    codeStream.TYPE(getValue());
  }

  @Override
  public TypeLDC clone() {
    return new TypeLDC(getValue());
  }

  @Override
  protected String print() {
    return "LDC ("+getValue()+".class)";
  }
}
