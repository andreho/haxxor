package net.andreho.haxxor.cgen.instr.constants.ldc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.constants.LDC;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 03:26.
 */
public class DoubleLDC
    extends LDC<Double> {
  public DoubleLDC(final double value) {
    super(value, ConstantType.DOUBLE);
  }

  @Override
  public void visit(final HxCodeStream codeStream) {
    codeStream.LDC(getValue());
  }

  @Override
  public DoubleLDC clone() {
    return new DoubleLDC(getValue());
  }
}
