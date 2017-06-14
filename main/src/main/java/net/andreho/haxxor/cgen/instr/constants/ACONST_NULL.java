package net.andreho.haxxor.cgen.instr.constants;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class ACONST_NULL
    extends AbstractZeroOperandInstruction {

  public ACONST_NULL() {
    super(Opcodes.ACONST_NULL);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.ACONST_NULL();
  }

  @Override
  public List<Object> getStackPushList(final HxComputingContext context) {
    return PUSH_NULL;
  }
}
