package net.andreho.haxxor.cgen;

import net.andreho.haxxor.cgen.impl.DefaultMapBasedLabelRemapper;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

/**
 * <br/>Created by a.hofmann on 13.12.2017 at 14:46.
 */
public interface HxLabelRemapper {
  static HxLabelRemapper createLabelRemapper() {
    return new DefaultMapBasedLabelRemapper();
  }

  /**
   * @param label
   * @return
   */
  LABEL remap(LABEL label);

  /**
   * @param labels
   * @return
   */
  LABEL[] remap(LABEL ... labels);

  /**
   */
  void reset();
}
