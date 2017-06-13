package net.andreho.haxxor.cgen.instr.constants;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class ICONST_4
    extends AbstractZeroOperandInstruction {

  public ICONST_4() {
    super(Opcodes.ICONST_4);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.ICONST_4();
  }

  @Override
  public List<Object> apply(final HxComputingContext context) {
    return PUSH_INT;
  }
}
