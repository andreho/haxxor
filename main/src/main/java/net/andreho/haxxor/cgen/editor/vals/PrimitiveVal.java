package net.andreho.haxxor.cgen.editor.vals;

import net.andreho.haxxor.cgen.editor.Editor;
import net.andreho.haxxor.cgen.editor.Val;
import net.andreho.haxxor.cgen.editor.vars.PrimitiveVar;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 01.04.2017 at 18:06.
 */
public abstract class PrimitiveVal<TYPE, VAR extends PrimitiveVar<TYPE, VAR, VAL>, VAL extends PrimitiveVal<TYPE,
    VAR, VAL>>
    extends Val<TYPE, VAR, VAL> {

  protected PrimitiveVal(final Editor editor, final HxType type, final int index) {
    super(editor, type, index);
  }

  public TypedVal<TYPE> boxed() {
    return null;
  }
}
