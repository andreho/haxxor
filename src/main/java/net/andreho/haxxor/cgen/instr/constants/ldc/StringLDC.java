package net.andreho.haxxor.cgen.instr.constants.ldc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.constants.LDC;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 03:26.
 */
public class StringLDC
    extends LDC<String> {
  public StringLDC(final String value) {
    super(value, ConstantType.STRING);
  }

  @Override
  public void visit(final HxCodeStream codeStream) {
    codeStream.LDC(getValue());
  }

  @Override
  public StringLDC clone() {
    return new StringLDC(getValue());
  }

  @Override
  public String toString() {
    return "LDC (\""+getValue()+"\")";
  }
}
