package net.andreho.haxxor.cgen.impl;

import net.andreho.haxxor.cgen.HxLabelRemapper;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * <br/>Created by a.hofmann on 13.12.2017 at 14:48.
 */
public class DefaultMapBasedLabelRemapper implements HxLabelRemapper {
  private static final Function<LABEL, LABEL>
    DEFAULT_REMAPPING_LAMBDA = (key) -> key.clone();
  private final Map<LABEL, LABEL> map;

  public DefaultMapBasedLabelRemapper() {
    this(new IdentityHashMap<>(32));
  }
  public DefaultMapBasedLabelRemapper(final Map<LABEL, LABEL> map) {
    this.map = map;
  }

  @Override
  public LABEL remap(final LABEL label) {
    return map.computeIfAbsent(label, DEFAULT_REMAPPING_LAMBDA);
  }

  @Override
  public LABEL[] remap(final LABEL... labels) {
    final LABEL[] remapped = new LABEL[labels.length];
    for(int i = 0; i < labels.length; i++) {
      remapped[i] = remap(labels[i]);
    }
    return remapped;
  }

  @Override
  public void reset() {
    map.clear();
  }
}
