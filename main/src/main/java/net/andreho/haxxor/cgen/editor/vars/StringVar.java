package net.andreho.haxxor.cgen.editor.vars;

import net.andreho.haxxor.cgen.editor.Block;

/**
 * <br/>Created by a.hofmann on 01.04.2017 at 17:17.
 */
public class StringVar extends TypedVar<String> {
   public StringVar(final Block block, final String name, final int index) {
      super(block, block.type(String.class.getName()), name, index);
      if(type.isPrimitive()) {
         throw new IllegalArgumentException("Primitive types aren't acceptable here.");
      }
   }

   public StringVar as(String value) {
      return this;
   }
}
