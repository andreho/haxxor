package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.spi.HxDeduplicationCache;

import java.util.Collections;
import java.util.Iterator;

/**
 * <br/>Created by a.hofmann on 27.10.2017 at 18:17.
 */
public class NoOpDeduplicationCache
  implements HxDeduplicationCache {
  @Override
  public String deduplicate(final char[] chars,
                            final int length) {
    return new String(chars, 0, length);
  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public int capacity() {
    return 0;
  }

  @Override
  public boolean has(final char[] chars,
                     final int length) {
    return false;
  }

  @Override
  public Iterator<String> iterator() {
    return Collections.<String>emptyList().iterator();
  }
}
