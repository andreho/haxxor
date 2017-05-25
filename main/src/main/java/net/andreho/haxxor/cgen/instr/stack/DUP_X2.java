package net.andreho.haxxor.cgen.instr.stack;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * Duplicate the top operand stack value and insert two or three values down.<br/>
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class DUP_X2 extends AbstractZeroOperandInstruction {
   public DUP_X2() {
      super(Opcodes.DUP_X2);
   }

   @Override
   public void dumpTo(Context context, CodeStream codeStream) {
      codeStream.DUP_X2();
   }

   @Override
   public List<Object> apply(final Context context) {
      Object value1 = context.getStack().peek();
      Object value2 = context.getStack().peek(1);
      Object value3 = context.getStack().peek(2);

      return context.getPush().prepare()
                    .push(value1, value3, value2, value1).get();
   }

   @Override
   public int getStackPopCount() {
      return 3;
   }
}
