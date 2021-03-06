package net.andreho.haxxor.cgen.instr.constants.ldc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.constants.LDC;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 03:26.
 */
public class IntegerLDC extends LDC<Integer> {
  public IntegerLDC(final int value) {
    super(value, ConstantType.INT);
  }

  @Override
  public void visit(final HxCodeStream codeStream) {
    codeStream.LDC(getValue());
  }

  @Override
  public IntegerLDC clone() {
    return new IntegerLDC(getValue());
  }
}
