package net.andreho.haxxor.cgen.editor.vars;

import net.andreho.haxxor.cgen.editor.Block;
import net.andreho.haxxor.cgen.editor.vals.CharVal;

/**
 * <br/>Created by a.hofmann on 01.04.2017 at 17:17.
 */
public class CharVar
    extends PrimitiveVar<Character, CharVar, CharVal> {

  public CharVar(final Block block, final String name, final int index) {
    super(block, block.type("C"), name, index);
  }

  public CharVar as(char value) {
    return this;
  }
}
