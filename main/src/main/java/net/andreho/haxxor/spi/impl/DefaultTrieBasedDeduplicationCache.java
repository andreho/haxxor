package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.spi.HxDeduplicationCache;
import net.andreho.haxxor.spi.HxParameters;
import net.andreho.haxxor.utils.trie.TrieNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * <br/>Created by a.hofmann on 27.10.2017 at 17:28.
 */
public class DefaultTrieBasedDeduplicationCache
  implements HxDeduplicationCache {

  private static final int MIN_ENTRY_LENGTH =
    Math.max(0, Integer.parseInt(System.getProperty(HxParameters.HX_DEDUPLICATION_MIN_ENTRY_LENGTH, "0")));
  private static final int MAX_ENTRY_LENGTH =
    Math.max(0, Integer.parseInt(System.getProperty(HxParameters.HX_DEDUPLICATION_MAX_ENTRY_LENGTH, "128")));

  private static final int DEFAULT_CAPACITY = 4;

  private char[] table;
  private TrieNode[] roots;

  private int length;
  private int size;

  private final int minLength;
  private final int maxLength;


  public DefaultTrieBasedDeduplicationCache() {
    this(MIN_ENTRY_LENGTH, MAX_ENTRY_LENGTH);
  }

  public DefaultTrieBasedDeduplicationCache(final int minLength,
                                            final int maxLength) {
    this.minLength = minLength;
    this.maxLength = maxLength;

    this.table = new char[DEFAULT_CAPACITY];
    this.roots = new TrieNode[DEFAULT_CAPACITY];

    if(minLength < 0 || minLength > maxLength) {
      throw new IllegalArgumentException("Invalid boundary for entries: ["+minLength + ", " + maxLength + "]");
    }
  }

  private int indexOf(char c) {
    return Arrays.binarySearch(this.table, 0, this.length, c);
  }

  private TrieNode findRootFor(final char[] chars, final int length) {
    int idx = indexOf(chars[0]);
    if(idx < 0) {
      idx = (-idx) - 1;
      return insertNewRootAt(idx, chars, length);
    }
    return roots[idx];
  }

  private TrieNode insertNewRootAt(final int idx,
                                   final char[] chars,
                                   final int length) {
    final TrieNode root = new TrieNode(chars, 0, length);
    final char c = chars[0];

    if(this.table.length <= this.length) {
      this.table = Arrays.copyOf(table, Math.max(DEFAULT_CAPACITY, table.length * 2));
      this.roots = Arrays.copyOf(roots, Math.max(DEFAULT_CAPACITY, roots.length * 2));
    }

    System.arraycopy(this.table, idx, this.table, idx + 1, this.length - idx);
    System.arraycopy(this.roots, idx, this.roots, idx + 1, this.length - idx);

    this.table[idx] = c;
    this.roots[idx] = root;
    this.length++;
    return root;
  }

  @Override
  public String deduplicate(final char[] chars,
                            final int length) {
    if(length <= 0) {
      return "";
    }
    if(!isSupported(length)) {
      return createString(chars, length);
    }

    TrieNode root = findRootFor(chars, length);
    TrieNode leaf = root.insert(chars, length);

    if(!leaf.hasValue()) {
      leaf.setValue(createString(chars, length));
      this.size++;
    }
    return leaf.getValue();
  }

  private boolean isSupported(final int length) {
    return length >= minLength && length <= maxLength;
  }

  protected String createString(final char[] chars, final int length) {
    return new String(chars, 0, length);
  }

  @Override
  public int size() {
    return this.size;
  }

  @Override
  public int capacity() {
    return Integer.MAX_VALUE;
  }

  public void print(final Appendable output) {
    for(int i = 0; i<this.length; i++) {
      final TrieNode root = this.roots[i];

      forEachLeaf(root, (node) -> {
          try {
            output.append(node.toString()).append('\n');
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
      });
    }
  }

  @Override
  public Iterator<String> iterator() {
    final List<String> strings = new ArrayList<>(size());

    for(int i = 0; i<this.length; i++) {
      final TrieNode root = this.roots[i];
      forEachLeaf(root, (node) -> strings.add(node.getValue()));
    }

    return strings.iterator();
  }

  private static void forEachLeaf(final TrieNode node, final Consumer<TrieNode> consumer) {
    if(node.hasValue()) {
      consumer.accept(node);
    }
    for(TrieNode child : node) {
      forEachLeaf(child, consumer);
    }
  }
}
