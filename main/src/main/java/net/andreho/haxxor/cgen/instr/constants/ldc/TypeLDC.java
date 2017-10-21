package net.andreho.haxxor.cgen.instr.constants.ldc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.constants.LDC;

import java.util.List;

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
  public List<Object> compute(final HxComputingContext context) {
    return PUSH_TYPE;
  }

  @Override
  public String toString() {
    return "LDC ("+getValue()+".class)";
  }
}
