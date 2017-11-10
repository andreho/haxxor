package net.andreho.haxxor.utils.trie;

/**
 * <br/>Created by a.hofmann on 27.10.2017 at 14:00.
 */
public abstract class BasicTrieNode<N extends BasicTrieNode<N>> {
  protected BasicTrieNode parent;
  protected N[] children;
  protected int length;

  protected char[] prefix;

  protected char charAt(int index) {
    return prefix[index];
  }

  @Override
  public String toString() {
    return toString(new StringBuilder()).toString();
  }

  protected StringBuilder toString(StringBuilder builder) {
    if(this.parent != null) {
      this.parent.toString(builder);
      builder.append('.');
    }
    return builder.append(prefix);
  }
}
