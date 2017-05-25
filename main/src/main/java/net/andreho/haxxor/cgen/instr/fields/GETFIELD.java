package net.andreho.haxxor.cgen.instr.fields;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractFieldInstruction;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class GETFIELD extends AbstractFieldInstruction {
   //----------------------------------------------------------------------------------------------------------------

   public GETFIELD(String owner, String name, String desc) {
      super(Opcodes.GETFIELD, owner, name, desc);
   }

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public void dumpTo(Context context, CodeStream codeStream) {
      codeStream.GETFIELD(this.owner, this.name, this.desc);
   }

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public int getStackPopCount() {
      return 1;
   }
}
