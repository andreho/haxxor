package net.andreho.haxxor.cgen.instr.stack;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * Duplicate the top one or two operand stack values.<br/>
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class DUP2
    extends AbstractZeroOperandInstruction {

  public DUP2() {
    super(Opcodes.DUP2);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.DUP2();
  }

  @Override
  public List<Object> apply(final HxComputingContext context) {
    Object value1 = context.getStack()
                           .peek();
    Object value2 = context.getStack()
                           .peek(1);

    return context.getStackPush()
                  .prepare()
                  .push(value2, value1, value2, value1)
                  .get();
  }
}
