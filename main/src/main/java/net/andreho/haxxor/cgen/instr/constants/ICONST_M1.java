package net.andreho.haxxor.cgen.instr.constants;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class ICONST_M1
    extends AbstractZeroOperandInstruction {

  public ICONST_M1() {
    super(Opcodes.ICONST_M1);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.ICONST_M1();
  }

  @Override
  public List<Object> getStackPushList(final HxComputingContext context) {
    return PUSH_INT;
  }
}
