package net.andreho.args;

import static net.andreho.args.ArgumentsType.fromCharCode;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 23:52.
 */
public abstract class AbstractArguments256
  extends AbstractArguments
  implements Arguments {

  private static final int TYPE_COUNT = 9;
  public static final int USED_BITS_MASK = 0xFFFF;
  public static final int INDEX_MASK = 0xFF;
  public static final int TYPE_SHIFT = 8;

  private final short[] info;

  private static short toShort(ArgumentsType type,
                               int index) {
    return (short) ((type.ordinal() << TYPE_SHIFT) | (INDEX_MASK & index));
  }

  private static void layoutArguments(final String signature,
                                      final String layout,
                                      final short[] info) {
    final byte[] start = new byte[TYPE_COUNT];
//    int fragment = 64;
//    long visited = 0;

    for (int idx = 0; idx < info.length; idx++) {
      final char signatureCode = signature.charAt(idx);
      final ArgumentsType argumentsType = fromCharCode(signatureCode);
      final int typeId = argumentsType.ordinal();
      final int fromIndex = INDEX_MASK & start[typeId];
      final int layoutPosition = layout.indexOf(signatureCode, fromIndex);

      if (layoutPosition < 0) {
        throw new IllegalStateException(
          "Given signature '" + signature + "' isn't compatible with the current layout: '" + layout + "'"
        );
      }

      info[idx] = toShort(argumentsType, layoutPosition);
      start[typeId] = (byte) (layoutPosition + 1);
    }
  }

  private static void checkLengthCompatibility(final String signature,
                                               final String layout,
                                               final int length) {
    if (length != layout.length() ||
        length != signature.length()) {
      throw new IllegalArgumentException(
        "Signature '" + signature + "' has different length as the current layout: '" + layout + "'");

    }
  }

  protected AbstractArguments256(final String signature,
                                 final String layout,
                                 final int length) {
    super();
    this.info = new short[length];
    checkLengthCompatibility(signature, layout, length);
    layoutArguments(signature, layout, info);
  }

  @Override
  protected final int info(int idx) {
    return USED_BITS_MASK & info[idx];
  }

  @Override
  public final int length() {
    return info.length;
  }

  @Override
  protected final ArgumentsType toSort(int value) {
    return ArgumentsType.fromCode(value >>> TYPE_SHIFT);
  }

  @Override
  protected final int toFieldsIndex(final int index) {
    return INDEX_MASK & info(index);
  }
}
