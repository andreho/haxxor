package net.andreho.haxxor.cgen.editor.vars;

import net.andreho.haxxor.cgen.editor.Block;
import net.andreho.haxxor.cgen.editor.vals.ShortVal;

/**
 * <br/>Created by a.hofmann on 01.04.2017 at 17:17.
 */
public class ShortVar
    extends PrimitiveVar<Short, ShortVar, ShortVal> {

  public ShortVar(final Block block, final String name, final int index) {
    super(block, block.type("S"), name, index);
  }

  public ShortVar as(short value) {
    return this;
  }
}
