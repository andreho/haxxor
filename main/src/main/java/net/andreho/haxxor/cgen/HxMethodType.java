package net.andreho.haxxor.cgen;

/**
 * <br/>Created by a.hofmann on 13.06.2017 at 03:45.
 */
public class HxMethodType {
  private final String signature;

  public HxMethodType(final String signature) {
    this.signature = signature;
  }

  public String getSignature() {
    return signature;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final HxMethodType that = (HxMethodType) o;

    return signature.equals(that.signature);
  }

  @Override
  public int hashCode() {
    return signature.hashCode();
  }

  @Override
  public String toString() {
    return signature;
  }
}
