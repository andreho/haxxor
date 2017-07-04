package org.sandbox.elements;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * <br/>Created by a.hofmann on 22.06.2017 at 09:34.
 */
public class Line
  extends Figure
  implements Comparable<Line> {

  private Point start;
  private Point end;

  public Line() {
    this(Point.ZERO, Point.ZERO);
  }

  public Line(final Point start,
              final Point end) {
    setStart(start);
    setEnd(end);
  }

  public Point getStart() {
    return start;
  }

  public void setStart(final Point start) {
    this.start = requireNonNull(start);
  }

  public Point getEnd() {
    return end;
  }

  public void setEnd(final Point end) {
    this.end = requireNonNull(end);
  }

  @Override
  public int compareTo(final Line o) {
    int result = start.compareTo(o.start);
    return result == 0? end.compareTo(o.end) : result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final Line other = (Line) o;

    if (!Objects.equals(start, other.start)) {
      return false;
    }
    if (!Objects.equals(end, other.end)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return start.hashCode() * 31 + end.hashCode();
  }

  @Override
  public String toString() {
    return "(" + start + " <-> " + end + ")";
  }
}
