package net.andreho.haxxor.cgen.editor.vars;

import net.andreho.haxxor.cgen.editor.Block;
import net.andreho.haxxor.cgen.editor.Var;
import net.andreho.haxxor.cgen.editor.vals.PrimitiveVal;
import net.andreho.haxxor.spec.HxType;

/**
 * <br/>Created by a.hofmann on 28.03.2017 at 22:38.
 */
public abstract class PrimitiveVar<TYPE, VAR extends PrimitiveVar<TYPE, VAR, VAL>, VAL extends PrimitiveVal<TYPE, VAR, VAL>>
   extends Var<TYPE, VAR, VAL> {
   protected PrimitiveVar(final Block block, final HxType type, final String name, final int index) {
      super(block, type, name, index);
   }


}
