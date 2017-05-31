package net.andreho.haxxor.cgen.editor.vars;

import net.andreho.haxxor.cgen.editor.Block;
import net.andreho.haxxor.cgen.editor.vals.FloatVal;

/**
 * <br/>Created by a.hofmann on 01.04.2017 at 17:17.
 */
public class FloatVar
    extends PrimitiveVar<Float, FloatVar, FloatVal> {

  public FloatVar(final Block block, final String name, final int index) {
    super(block, block.type("F"), name, index);
  }

  public FloatVar as(float value) {
    return this;
  }
}
