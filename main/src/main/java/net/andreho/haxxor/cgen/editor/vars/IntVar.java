package net.andreho.haxxor.cgen.editor.vars;

import net.andreho.haxxor.cgen.editor.Block;
import net.andreho.haxxor.cgen.editor.vals.IntVal;

/**
 * <br/>Created by a.hofmann on 01.04.2017 at 17:17.
 */
public class IntVar extends PrimitiveVar<Integer, IntVar, IntVal> {
   public IntVar(final Block block, final String name, final int index) {
      super(block, block.type("I"), name, index);
   }

   public IntVar as(int value) {
      return this;
   }
}
