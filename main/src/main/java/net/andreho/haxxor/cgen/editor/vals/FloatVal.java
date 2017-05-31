package net.andreho.haxxor.cgen.editor.vals;

import net.andreho.haxxor.cgen.editor.Editor;
import net.andreho.haxxor.cgen.editor.vars.FloatVar;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 01.04.2017 at 18:18.
 */
public class FloatVal
    extends PrimitiveVal<Float, FloatVar, FloatVal> {

  public FloatVal(final Editor editor, final HxType type, final int index) {
    super(editor, type, index);
  }
}
