package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.spi.HxDeduplicationCache;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * <br/>Created by a.hofmann on 27.10.2017 at 17:28.
 */
public final class TreeBasedDeduplicationCache
  implements HxDeduplicationCache {

  private final TreeMap<CharSequence, String> map;
  private static int compare(final CharSequence a, final CharSequence b) {
    final int diff = a.length() - b.length();
    final int len = Math.min(a.length(), b.length());

    for(int i = 0; i < len; i++) {
      final char ca = a.charAt(i);
      final char cb = b.charAt(i);

      if(ca < cb) {
        return -1;
      } else if(ca != cb) {
        return 1;
      }
    }
    return diff;
  }

  public TreeBasedDeduplicationCache() {
    this.map = new TreeMap<>(TreeBasedDeduplicationCache::compare);
  }

  @Override
  public boolean has(final char[] chars,
                     final int length) {
    return this.map.containsKey(new Key(chars, length));
  }

  @Override
  public String deduplicate(final char[] chars,
                            final int length) {
    final Key key = new Key(chars, length);
    String string = map.get(key);
    if(string != null) {
      return string;
    }
    string = new String(chars, 0, length);
    map.put(string, string);
    return string;
  }

  @Override
  public int size() {
    return this.map.size();
  }

  @Override
  public int capacity() {
    return Integer.MAX_VALUE;
  }

  @Override
  public Iterator<String> iterator() {
    return map.values().iterator();
  }
}

final class Key implements CharSequence {
  final char[] chars;
  final int length;

  public Key(final char[] chars,
             final int length) {
    this.chars = chars;
    this.length = length;
  }

  @Override
  public int length() {
    return length;
  }

  @Override
  public char charAt(final int index) {
    return chars[index];
  }

  @Override
  public CharSequence subSequence(final int start,
                                  final int end) {
    throw new UnsupportedOperationException();
  }
}
