package net.andreho.haxxor.cgen.instr.arithmetic;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class FMUL
    extends AbstractZeroOperandInstruction {

  public FMUL() {
    super(Opcodes.FMUL);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.FMUL();
  }

  @Override
  public List<Object> compute(HxComputingContext context) {
    return PUSH_FLOAT;
  }
}
