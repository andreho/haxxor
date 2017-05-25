package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.haxxor.cgen.Context;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class AbstractFieldInstruction extends AbstractInstruction {
   protected final String owner;

   //----------------------------------------------------------------------------------------------------------------
   protected final String name;
   protected final String desc;

   public AbstractFieldInstruction(int opcode, String owner, String name, String desc) {
      super(opcode);
      this.owner = owner;
      this.name = name;
      this.desc = desc;
   }

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public List<Object> apply(final Context context) {
      return Utils.retrieveType(context, this.desc);
   }

   @Override
   public String toString() {
      return super.toString() + " " + this.owner + "." + this.name + " " + this.desc;
   }
}
