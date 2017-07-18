package net.andreho.args;

/**
 * <br/>Created by a.hofmann on 18.07.2017 at 10:38.
 */
public final class EmptyArguments extends AbstractArguments {
  static final Arguments INSTANCE = new EmptyArguments();

  @Override
  protected int info(final int idx) {
    throw new IllegalStateException("Empty arguments.");
  }

  @Override
  protected ArgumentsType toSort(final int info) {
    throw new IllegalStateException("Empty arguments.");
  }

  @Override
  protected int toFieldsIndex(final int index) {
    throw new IllegalStateException("Empty arguments.");
  }

  @Override
  public int length() {
    return 0;
  }
}
