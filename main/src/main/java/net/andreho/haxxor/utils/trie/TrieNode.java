package net.andreho.haxxor.utils.trie;

import net.andreho.haxxor.utils.CommonUtils;

import java.util.Arrays;
import java.util.Iterator;

/**
 * <br/>Created by a.hofmann on 26.10.2017 at 18:42.
 */
public final class TrieNode
  extends BasicTrieNode<TrieNode>
  implements Iterable<TrieNode> {

  private static final String NO_VALUE = new String("<empty>");
  private static final TrieNode[] EMPTY_CHILDREN = new TrieNode[0];

  protected String value = NO_VALUE;

  public TrieNode() {
    super();
    this.children = EMPTY_CHILDREN;
  }

  public TrieNode(final char[] key,
                  final int length,
                  final String value) {
    this();

    this.prefix = Arrays.copyOf(key, length);
    this.value = value;
  }

  public TrieNode(final char[] key,
                  final int tail,
                  final int length) {
    this();

    final int prefixLength = length - tail;
    this.prefix = new char[prefixLength];
    System.arraycopy(key, tail, this.prefix, 0, prefixLength);
  }

  public String getValue() {
    String value = this.value;
    return value == NO_VALUE ? null : value;
  }

  public void setValue(final String value) {
    this.value = value;
  }

  public boolean hasValue() {
    return this.value != NO_VALUE;
  }

  public boolean hasChildren() {
    return this.length > 0;
  }

  public char first() {
    return this.prefix[0];
  }

  public TrieNode insert(final char[] key,
                         final int length) {
    return insert(key, 0, length);
  }

  private TrieNode insert(final char[] key,
                          int tail,
                          final int length) {
    TrieNode current = this;

    while (true) {
      final char[] prefix = current.prefix;
      int idx = 0;

      for (int len = Math.min(length - tail, prefix.length); idx < len; idx++, tail++) {
        if (prefix[idx] != key[tail]) {
          break;
        }
      }

      if (idx == prefix.length) {
        if (tail == length) {
          return current;
        }

        TrieNode child = current.findChild(key, tail);
        if (child == null) {
          return current.addChild(key, tail, length);
        }
        current = child;
      } else if (idx < prefix.length) {
        return current.split(key, idx, tail, length);
      } else if (idx == 0) {
        throw new IllegalStateException();
      }
    }
  }

  private TrieNode findChild(final char[] key,
                             final int charIndex) {
    final char first = key[charIndex];
    final TrieNode[] children = this.children;
    final int idx = TrieUtils.binaryApproximation(children, first, this.length);

    if(idx < 0) {
      return null;
    }

    return children[idx];
  }

  private TrieNode addChild(final char[] key,
                            final int tail,
                            final int length) {
    return addChild(new TrieNode(key, tail, length));
  }

  private TrieNode addChild(TrieNode node) {
    TrieNode[] children = this.children;

    if (children.length <= this.length) {
      children = Arrays.copyOf(children, Math.max(2, children.length * 2));
      this.children = children;
    }

    int idx = TrieUtils.binaryApproximation(this.children, node.first(), this.length);

    if(idx < 0) {
      idx = TrieUtils.decodeIndex(idx);
      System.arraycopy(children, idx, children, idx + 1, this.length - idx);
      children[idx] = node;
      this.length++;
    } else {
      throw new IllegalStateException();
    }
//    children[this.length++] = node;
    node.parent = this;
    return node;
  }

  private TrieNode split(final char[] key,
                         final int split,
                         final int tail,
                         final int length) {

    TrieNode rest = new TrieNode();

    rest.value = this.value;
    rest.prefix = new char[this.prefix.length - split];
    System.arraycopy(this.prefix, split, rest.prefix, 0, rest.prefix.length);

    rest.children = this.children;
    rest.length = this.length;
    rest.setupChildren(rest);

    this.prefix = Arrays.copyOf(this.prefix, split);

    resetValue()
      .resetChildren()
      .addChild(rest);

    if (length - tail == 0) {
      return this;
    }

    return addChild(key, tail, length);
  }

  private void setupChildren(final TrieNode newParent) {
    for (int i = 0, l = this.length; i < l; i++) {
      this.children[i].parent = newParent;
    }
  }

  private TrieNode resetValue() {
    this.value = NO_VALUE;
    return this;
  }

  private TrieNode resetChildren() {
    this.children = EMPTY_CHILDREN;
    this.length = 0;
    return this;
  }

  @Override
  public Iterator<TrieNode> iterator() {
    return CommonUtils.iterator(this.children, 0, this.length);
  }

  @Override
  public String toString() {
    return super.toString() + " = " + value;
  }
}
