package net.andreho.haxxor.cgen.instr.constants.ldc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxMethodType;
import net.andreho.haxxor.cgen.instr.constants.LDC;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 03:26.
 */
public class MethodTypeLDC
    extends LDC<HxMethodType> {
  public MethodTypeLDC(final HxMethodType value) {
    super(value, ConstantType.METHOD_TYPE);
  }

  @Override
  public void visit(final HxCodeStream codeStream) {
    codeStream.METHOD(getValue());
  }

  @Override
  public MethodTypeLDC clone() {
    return new MethodTypeLDC(getValue());
  }
}
