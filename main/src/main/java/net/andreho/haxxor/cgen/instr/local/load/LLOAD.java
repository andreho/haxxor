package net.andreho.haxxor.cgen.instr.local.load;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractLocalAccessInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class LLOAD extends AbstractLocalAccessInstruction {
   public LLOAD(int var) {
      super(Opcodes.LLOAD, var);
   }

   @Override
   public void dumpTo(Context context, CodeStream codeStream) {
      codeStream.LLOAD(getLocalIndex());
   }

   @Override
   public List<Object> apply(final Context context) {
      Object type = context.getLocals().get(getLocalIndex());
      if (Opcodes.LONG != type) {
         throw new IllegalArgumentException(
               "A long operand is expected at slot index [" + getLocalIndex() + "], but got: " + type);
      }
      return context.getPush().prepare().push(type, Opcodes.TOP).get();
   }

   @Override
   public int getStackPopCount() {
      return 0;
   }
}
