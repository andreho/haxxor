package net.andreho.haxxor.cgen.instr.constants;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class ICONST_2
    extends AbstractZeroOperandInstruction {

  public ICONST_2() {
    super(Opcodes.ICONST_2);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.ICONST_2();
  }

  @Override
  public List<Object> getStackPushList(final HxComputingContext context) {
    return PUSH_INT;
  }
}
