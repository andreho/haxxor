package net.andreho.haxxor.cgen.instr.stack;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * Duplicate the top operand stack value and insert two or three values down.<br/>
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class DUP_X2
    extends AbstractZeroOperandInstruction {

  public DUP_X2() {
    super(Opcodes.DUP_X2);
  }

  @Override
  public void dumpTo(HxComputingContext context, HxCodeStream codeStream) {
    codeStream.DUP_X2();
  }

  @Override
  public List<Object> apply(final HxComputingContext context) {
    Object value1 = context.getStack()
                           .peek();
    Object value2 = context.getStack()
                           .peek(1);
    Object value3 = context.getStack()
                           .peek(2);

    return context.getStackPush()
                  .prepare()
                  .push(value1, value3, value2, value1)
                  .get();
  }
}
