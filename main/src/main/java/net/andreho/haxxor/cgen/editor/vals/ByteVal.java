package net.andreho.haxxor.cgen.editor.vals;

import net.andreho.haxxor.cgen.editor.Editor;
import net.andreho.haxxor.cgen.editor.vars.ByteVar;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 01.04.2017 at 18:18.
 */
public class ByteVal
    extends PrimitiveVal<Byte, ByteVar, ByteVal> {

  public ByteVal(final Editor editor, final HxType type, final int index) {
    super(editor, type, index);
  }
}
