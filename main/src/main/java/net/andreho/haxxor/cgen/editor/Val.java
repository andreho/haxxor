package net.andreho.haxxor.cgen.editor;

import net.andreho.haxxor.spec.HxType;

/**
 * <br/>Created by a.hofmann on 28.03.2017 at 22:44.
 */
public abstract class Val<TYPE, VAR extends Var<TYPE, VAR, VAL>, VAL extends Val<TYPE, VAR, VAL>> {
   protected final Editor editor;
   protected final HxType type;
   protected final int index;

   protected Val(final Editor editor, final HxType type, final int index) {
      this.editor = editor;
      this.index = index;
      this.type = type;
   }
}
