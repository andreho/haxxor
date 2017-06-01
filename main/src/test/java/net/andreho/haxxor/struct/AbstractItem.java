package net.andreho.haxxor.struct;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 31.05.2017 at 17:38.
 */
public abstract class AbstractItem
    implements Comparable<AbstractItem> {

  private final String name;
  private double price;

  public AbstractItem(final String name) {
    this.name = Objects.requireNonNull(name);
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(final double price) {
    this.price = price;
  }

  @Override
  public int compareTo(final AbstractItem o) {
    return name.compareTo(o.name);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final AbstractItem that = (AbstractItem) o;

    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
