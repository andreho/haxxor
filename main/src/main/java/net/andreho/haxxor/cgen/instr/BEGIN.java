package net.andreho.haxxor.cgen.instr;

import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.Instruction;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

import java.util.Collections;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public class BEGIN extends AbstractInstruction {
   public BEGIN() {
      super(-1);
   }

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public void dumpTo(final Context context, final CodeStream codeStream) {
      codeStream.BEGIN();
   }

   @Override
   public List<Object> apply(final Context context) {
      return Collections.emptyList();
   }

   @Override
   public int getStackPopCount() {
      return 0;
   }

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public Instruction prepend(final Instruction inst) {
      throw new IllegalStateException("BEGIN must be the first instruction.");
   }

   @Override
   public void setPrevious(final Instruction previous) {
      throw new IllegalStateException("BEGIN must be the first instruction.");
   }

   @Override
   public Instruction remove() {
      throw new IllegalStateException("BEGIN instruction can't be removed.");
   }
}
