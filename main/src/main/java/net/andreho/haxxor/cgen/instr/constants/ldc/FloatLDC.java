package net.andreho.haxxor.cgen.instr.constants.ldc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.constants.LDC;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 03:26.
 */
public class FloatLDC
    extends LDC<Float> {
  public FloatLDC(final float value) {
    super(value, ConstantType.FLOAT);
  }

  @Override
  public void dumpTo(final HxComputingContext context,
                     final HxCodeStream codeStream) {
    codeStream.LDC(getValue());
  }

  @Override
  public List<Object> apply(final HxComputingContext context) {
    return PUSH_FLOAT;
  }
}
