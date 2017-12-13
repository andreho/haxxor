package net.andreho.haxxor.cgen.instr.constants.ldc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.constants.LDC;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 03:26.
 */
public class FloatLDC
    extends LDC<Float> {
  public FloatLDC(final float value) {
    super(value, ConstantType.FLOAT);
  }

  @Override
  public void visit(final HxCodeStream codeStream) {
    codeStream.LDC(getValue());
  }

  @Override
  public FloatLDC clone() {
    return new FloatLDC(getValue());
  }
}
