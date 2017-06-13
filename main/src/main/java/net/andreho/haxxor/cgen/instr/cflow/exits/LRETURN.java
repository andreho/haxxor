package net.andreho.haxxor.cgen.instr.cflow.exits;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class LRETURN
    extends AbstractInstruction {

  public LRETURN() {
    super(Opcodes.LRETURN);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.LRETURN();
  }

  @Override
  public List<Object> apply(final HxComputingContext context) {
    return NO_STACK_PUSH;
  }
}
