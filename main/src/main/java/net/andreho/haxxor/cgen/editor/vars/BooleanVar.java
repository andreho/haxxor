package net.andreho.haxxor.cgen.editor.vars;

import net.andreho.haxxor.cgen.editor.Block;
import net.andreho.haxxor.cgen.editor.vals.BooleanVal;

/**
 * <br/>Created by a.hofmann on 01.04.2017 at 17:17.
 */
public class BooleanVar extends PrimitiveVar<Boolean, BooleanVar, BooleanVal> {
   public BooleanVar(final Block block, final String name, final int index) {
      super(block, block.type("Z"), name, index);
   }

   public BooleanVar as(boolean value) {
      return this;
   }
}
