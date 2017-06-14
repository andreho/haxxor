package net.andreho.haxxor.cgen;

import net.andreho.asm.org.objectweb.asm.Handle;
import net.andreho.asm.org.objectweb.asm.Label;
import net.andreho.haxxor.cgen.instr.LABEL;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 18:48.
 */
public abstract class HxAsmUtils {

  private HxAsmUtils() {
  }

  public static Label toAsmLabel(LABEL label) {
    return label.getAsmLabel();
  }

  public static Label[] toAsmLabels(LABEL... labels) {
    final Label[] result = new Label[labels.length];
    for (int i = 0; i < labels.length; i++) {
      result[i] = toAsmLabel(labels[i]);
    }
    return result;
  }

  public static Handle toAsmHandle(final HxMethodHandle handle) {
    return new Handle(handle.getTag()
                            .getCode(), handle.getOwner(), handle.getName(), handle.getDescriptor(),
                      handle.isInterface());
  }
}
