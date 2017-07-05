package org.sandbox.equals;

import org.sandbox.aspects.equals.EqualityAttribute;
import org.sandbox.aspects.equals.EqualsAndHashCode;

/**
 * <br/>Created by a.hofmann on 05.07.2017 at 19:11.
 */
@EqualsAndHashCode
public final class Color {

  @EqualityAttribute
  private final int color;

  private static int maskByte(final int red) {
    return 0xFF & red;
  }

  public Color(final int red,
               final int green,
               final int blue) {
    this(red, green, blue, 0);
  }

  public Color(final int red,
               final int green,
               final int blue,
               final int alpha) {
    this(alpha << 24 | maskByte(red) << 16 | maskByte(green) << 8 | maskByte(blue));
  }

  public Color(final int color) {
    this.color = color;
  }

  public int getColor() {
    return color;
  }

  public int getAlpha() {
    return maskByte(color >>> 24);
  }

  public int getRed() {
    return maskByte(color >>> 16);
  }

  public int getGreen() {
    return maskByte(color >>> 8);
  }

  public int getBlue() {
    return maskByte(color);
  }
}
