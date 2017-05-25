package net.andreho.haxxor.cgen.editor.vals;

import net.andreho.haxxor.cgen.editor.Editor;
import net.andreho.haxxor.cgen.editor.vars.LongVar;
import net.andreho.haxxor.spec.HxType;

/**
 * <br/>Created by a.hofmann on 01.04.2017 at 18:18.
 */
public class LongVal extends PrimitiveVal<Long, LongVar, LongVal> {
   public LongVal(final Editor editor, final HxType type, final int index) {
      super(editor, type, index);
   }
}
