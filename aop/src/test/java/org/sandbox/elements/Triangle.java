package org.sandbox.elements;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * <br/>Created by a.hofmann on 22.06.2017 at 09:41.
 */
public class Triangle
  extends Figure
  implements Comparable<Triangle> {

  private Point one;
  private Point two;
  private Point three;

  public Triangle() {
    this(Point.ZERO, Point.ZERO, Point.ZERO);
  }

  public Triangle(final Point one,
                  final Point two,
                  final Point three) {
    setOne(one);
    setTwo(two);
    setThree(three);
  }

  public Point getOne() {
    return one;
  }

  public void setOne(final Point one) {
    this.one = requireNonNull(one);
  }

  public Point getTwo() {
    return two;
  }

  public void setTwo(final Point two) {
    this.two = requireNonNull(two);
  }

  public Point getThree() {
    return three;
  }

  public void setThree(final Point three) {
    this.three = requireNonNull(three);
  }

  @Override
  public int compareTo(final Triangle o) {
    int result = one.compareTo(o.one);
    if(result == 0) {
      result = two.compareTo(o.two);
      if(result == 0) {
        result = three.compareTo(o.three);
      }
    }
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final Triangle other = (Triangle) o;

    if (!Objects.equals(one, other.one)) {
      return false;
    }

    if (!Objects.equals(two, other.two)) {
      return false;
    }

    if (!Objects.equals(three, other.three)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return one.hashCode() * 31 + two.hashCode() * 17 + three.hashCode();
  }

  @Override
  public String toString() {
    return "<" + one +" <-> " + two + " <-> " + three +">";
  }
}
