package net.andreho.haxxor.cgen.instr.constants.ldc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxMethodHandle;
import net.andreho.haxxor.cgen.instr.constants.LDC;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 03:26.
 */
public class MethodHandleLDC
    extends LDC<HxMethodHandle> {
  public MethodHandleLDC(final HxMethodHandle value) {
    super(value, ConstantType.METHOD_HANDLE);
  }

  @Override
  public void visit(final HxCodeStream codeStream) {
    codeStream.HANDLE(getValue());
  }

  @Override
  public List<Object> getStackPushList(final HxComputingContext context) {
    return PUSH_HANDLE;
  }
}
