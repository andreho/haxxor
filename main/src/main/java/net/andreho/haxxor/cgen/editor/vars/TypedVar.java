package net.andreho.haxxor.cgen.editor.vars;

import net.andreho.haxxor.cgen.editor.Block;
import net.andreho.haxxor.cgen.editor.Val;
import net.andreho.haxxor.cgen.editor.Var;
import net.andreho.haxxor.cgen.editor.vals.TypedVal;
import net.andreho.haxxor.spec.api.HxType;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 01.04.2017 at 17:17.
 */
public class TypedVar<T>
    extends Var<T, TypedVar<T>, TypedVal<T>> {

  public TypedVar(final Block block, final HxType type, final String name, final int index) {
    super(block, type, name, index);
    if (type.isPrimitive()) {
      throw new IllegalArgumentException("Primitive types aren't acceptable here.");
    }
  }

  public <V extends TypedVal<V>> V invokeTyped(final String method, final Val... args) {
    return null;
  }

  public <V extends TypedVal<V>> V invokeTyped(final String method, final List<? extends Val> args) {
    return null;
  }
}
