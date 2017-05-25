package net.andreho.haxxor.cgen.editor.vals;

import net.andreho.haxxor.cgen.editor.Editor;
import net.andreho.haxxor.cgen.editor.vars.DoubleVar;
import net.andreho.haxxor.spec.HxType;

/**
 * <br/>Created by a.hofmann on 01.04.2017 at 18:18.
 */
public class DoubleVal extends PrimitiveVal<Double, DoubleVar, DoubleVal> {
   public DoubleVal(final Editor editor, final HxType type, final int index) {
      super(editor, type, index);
   }
}
