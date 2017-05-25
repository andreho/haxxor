package net.andreho.haxxor.cgen.editor.vals;

import net.andreho.haxxor.cgen.editor.Editor;
import net.andreho.haxxor.cgen.editor.vars.TypedVar;

/**
 * <br/>Created by a.hofmann on 01.04.2017 at 19:47.
 */
public class StringVal extends TypedVal<String> {
   public StringVal(final Editor editor,
                       final int index) {
      super(editor, editor.getHaxxor().reference(String.class.getName()), index);
   }

   @Override
   public <V extends TypedVar> V toVar(final V var) {
      return super.toVar(var);
   }
}
