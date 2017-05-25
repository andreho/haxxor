package net.andreho.haxxor.cgen.editor.vars;

import net.andreho.haxxor.cgen.editor.Block;
import net.andreho.haxxor.cgen.editor.vals.DoubleVal;

/**
 * <br/>Created by a.hofmann on 01.04.2017 at 17:17.
 */
public class DoubleVar extends PrimitiveVar<Double, DoubleVar, DoubleVal> {
   public DoubleVar(final Block block, final String name, final int index) {
      super(block, block.type("D"), name, index);
   }

   public DoubleVar as(double value) {
      return this;
   }
}
