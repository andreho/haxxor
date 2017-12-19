package net.andreho.haxxor.utils.trie;

/**
 * <br/>Created by a.hofmann on 27.10.2017 at 22:57.
 */
public class TrieUtils {

  /**
   * @param key
   * @param length
   * @return
   */
  public static TrieNode binarySearch(final TrieNode[] nodes,
                                      final int key,
                                      final int length) {
    return binarySearch(nodes, key, 0, length);
  }

  /**
   * @param nodes
   * @param key
   * @param fromIndex
   * @param toIndex
   * @return
   */
  public static TrieNode binarySearch(
    final TrieNode[] nodes,
    final int key,
    final int fromIndex,
    final int toIndex) {

    int low = fromIndex;
    int high = toIndex - 1;

    while (low <= high) {
      final int mid = (low + high) >>> 1;
      final char target = nodes[mid].first();

      if (target < key) {
        low = mid + 1;
      } else if (target > key) {
        high = mid - 1;
      } else {
        return nodes[mid]; // key found
      }
    }
    return null;
  }

  /**
   * @param nodes
   * @param key
   * @param length
   * @return
   */
  public static int binaryApproximation(final TrieNode[] nodes,
                                        final int key,
                                        final int length) {
    return binaryApproximation(nodes, key, 0, length);
  }

  /**
   * @param key
   * @param fromIndex
   * @param toIndex
   * @return
   */
  public static int binaryApproximation(
    final TrieNode[] nodes,
    final int key,
    final int fromIndex,
    final int toIndex) {

    int low = fromIndex;
    int high = toIndex - 1;

    while (low <= high) {
      final int mid = (low + high) >>> 1;
      final TrieNode target = nodes[mid];
      final char first = target.first();

      if (first < key) {
        low = mid + 1;
      } else if (first > key) {
        high = mid - 1;
      } else {
        return mid; // key found
      }
    }
    return -(low + 1);
  }

  public static int decodeIndex(int idx) {
    return (-idx) - 1;
  }
}
