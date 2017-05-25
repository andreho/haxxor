package net.andreho.haxxor.cgen.instr.invokes;

import net.andreho.asm.org.objectweb.asm.Handle;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

import java.util.Arrays;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class INVOKEDYNAMIC extends AbstractInstruction {
   private final String name;
   private final String desc;
   private final Handle bootstrapMethod;
   private final Object[] bootstrapMethodArguments;

   public INVOKEDYNAMIC(String name, String desc, Handle bsm, Object... bsmArgs) {
      super(Opcodes.INVOKEDYNAMIC);
      Utils.checkMethodName(getOpcode(), name);
      this.name = name;
      this.desc = desc;
      this.bootstrapMethod = bsm;
      this.bootstrapMethodArguments = bsmArgs;
   }

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public void dumpTo(Context context, CodeStream codeStream) {
      codeStream.INVOKEDYNAMIC(this.name, this.desc, this.bootstrapMethod, this.bootstrapMethodArguments);
   }

   @Override
   public List<Object> apply(final Context context) {
      return Utils.retrieveType(context, this.desc);
   }

   @Override
   public int getStackPopCount() {
      int argumentSizes = (Type.getArgumentsAndReturnSizes(this.desc) >> 2) - 1;
      return argumentSizes;
   }

   @Override
   public String toString() {
      return super.toString() + " " + this.name + " " + this.desc + " - > " + this.bootstrapMethod + Arrays.toString(
            bootstrapMethodArguments);
   }
}
