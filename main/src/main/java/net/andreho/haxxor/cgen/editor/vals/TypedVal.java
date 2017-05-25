package net.andreho.haxxor.cgen.editor.vals;

import net.andreho.haxxor.cgen.editor.Editor;
import net.andreho.haxxor.cgen.editor.Val;
import net.andreho.haxxor.cgen.editor.vars.TypedVar;
import net.andreho.haxxor.spec.HxType;

/**
 * <br/>Created by a.hofmann on 01.04.2017 at 19:47.
 */
public class TypedVal<T> extends Val<T, TypedVar<T>, TypedVal<T>> {
   protected TypedVal(final Editor editor, final HxType type,
                      final int index) {
      super(editor, type, index);
   }

   public <V extends TypedVar> V toVar(final V var) {
      editor.getStream().ASTORE(var.getIndex());
      return var;
   }

   public void invokeVoid(String method, Val ... args) {
      return;
   }

   public <V extends TypedVal> V invokeTyped(String method, Val ... args) {
      return null;
   }
}
