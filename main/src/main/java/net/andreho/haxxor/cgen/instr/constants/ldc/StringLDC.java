package net.andreho.haxxor.cgen.instr.constants.ldc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.constants.LDC;

import java.util.List;

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
  public List<Object> compute(final HxComputingContext context) {
    return PUSH_STRING;
  }

  @Override
  public String toString() {
    return "LDC (\""+getValue()+"\")";
  }
}
