package net.andreho.haxxor.cgen.instr.create;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractStringOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class MULTIANEWARRAY extends AbstractStringOperandInstruction {
   private final int dimension;

   //----------------------------------------------------------------------------------------------------------------

   public MULTIANEWARRAY(String className, int dims) {
      super(Opcodes.MULTIANEWARRAY, className);
      this.dimension = dims;
   }

   //----------------------------------------------------------------------------------------------------------------

   private static String multiply(String str, int times) {
      StringBuilder builder = new StringBuilder();
      while (times-- > 0) {
         builder.append(str);
      }
      return builder.toString();
   }

   @Override
   public void dumpTo(Context context, CodeStream codeStream) {
      codeStream.MULTIANEWARRAY(getOperand(), this.dimension);
   }

   @Override
   public List<Object> apply(final Context context) {
      return context.getPush()
                    .prepare()
                    .push(multiply(getOperand(), this.dimension))
                    .get();
   }

   @Override
   public int getStackPopCount() {
      return this.dimension;
   }

   //-----------------------------------------------------------------------------------------------------------------

   @Override
   public String toString() {
      return super.toString() + " " + multiply("[", this.dimension) + getOperand();
   }
}
