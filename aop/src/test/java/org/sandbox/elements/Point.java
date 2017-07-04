package org.sandbox.elements;

/**
 * <br/>Created by a.hofmann on 22.06.2017 at 09:34.
 */
public class Point
  extends Figure
  implements Comparable<Point> {
  public static final Point ZERO = new Point();

  private int x;
  private int y;

  public Point() {
    this(0, 0);
  }
  public Point(final int x,
               final int y) {
    this.x = x;
    this.y = y;
  }

  public void setX(final int x) {
    this.x = x;
  }
  public void setY(final int y) {
    this.y = y;
  }

  public int getX() {
    return x;
  }
  public int getY() {
    return y;
  }

  @Override
  public int compareTo(final Point o) {
    int result = Integer.compare(x, o.x);
    return result == 0? Integer.compare(y, o.y) : result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final Point point = (Point) o;

    if (x != point.x ||
        y != point.y) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = x;
    result = 31 * result + y;
    return result;
  }

  @Override
  public String toString() {
    return "(" + x +"," + y +")";
  }
}
