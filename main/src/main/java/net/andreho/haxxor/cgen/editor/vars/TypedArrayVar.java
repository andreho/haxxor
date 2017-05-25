package net.andreho.haxxor.cgen.editor.vars;

import net.andreho.haxxor.cgen.editor.Block;
import net.andreho.haxxor.spec.HxType;

/**
 * <br/>Created by a.hofmann on 01.04.2017 at 17:17.
 */
public class TypedArrayVar<T> extends TypedVar<T> {
   public TypedArrayVar(final Block block, final HxType type, final String name, final int index) {
      super(block, type, name, index);
   }
}
