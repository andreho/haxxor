package net.andreho.haxxor.cgen.editor.vars;

import net.andreho.haxxor.cgen.editor.Block;
import net.andreho.haxxor.cgen.editor.vals.LongVal;

/**
 * <br/>Created by a.hofmann on 01.04.2017 at 17:17.
 */
public class LongVar
    extends PrimitiveVar<Long, LongVar, LongVal> {

  public LongVar(final Block block, final String name, final int index) {
    super(block, block.type("J"), name, index);
  }

  public LongVar as(long value) {
    return this;
  }
}
