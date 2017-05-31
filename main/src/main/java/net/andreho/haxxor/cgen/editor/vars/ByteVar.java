package net.andreho.haxxor.cgen.editor.vars;

import net.andreho.haxxor.cgen.editor.Block;
import net.andreho.haxxor.cgen.editor.vals.ByteVal;

/**
 * <br/>Created by a.hofmann on 01.04.2017 at 17:17.
 */
public class ByteVar
    extends PrimitiveVar<Byte, ByteVar, ByteVal> {

  public ByteVar(final Block block, final String name, final int index) {
    super(block, block.type("B"), name, index);
  }

  public ByteVar as(byte value) {
    return this;
  }
}
