package net.andreho.args;

import java.util.NoSuchElementException;

/**
 * <br/>Created by a.hofmann on 22.06.2017 at 00:19.
 */
final class ArgumentsIteratorImpl implements ArgumentsIterator {
  private final AbstractArguments arguments;
  private final int length;
  private int index;

  ArgumentsIteratorImpl(final AbstractArguments arguments) {
    this.length = arguments.length();
    this.arguments = arguments;
    this.index = 0;
  }

  @Override
  public ArgumentsType type() {
    return arguments.getType(checkIteratorsPosition());
  }

  private int checkIteratorsPosition() {
    int index = this.index;
    if(index >= length) {
      throw new NoSuchElementException();
    }
    return index;
  }

  private int moveForward(int index) {
    this.index = index + 1;
    return index;
  }

  @Override
  public boolean hasNext() {
    return index < length;
  }

  @Override
  public boolean nextBoolean() {
    return arguments.getBoolean(moveForward(checkIteratorsPosition()));
  }

  @Override
  public byte nextByte() {
    return arguments.getByte(moveForward(checkIteratorsPosition()));
  }

  @Override
  public short nextShort() {
    return arguments.getShort(moveForward(checkIteratorsPosition()));
  }

  @Override
  public char nextChar() {
    return arguments.getChar(moveForward(checkIteratorsPosition()));
  }

  @Override
  public int nextInt() {
    return arguments.getInt(moveForward(checkIteratorsPosition()));
  }

  @Override
  public float nextFloat() {
    return arguments.getFloat(moveForward(checkIteratorsPosition()));
  }

  @Override
  public long nextLong() {
    return arguments.getLong(moveForward(checkIteratorsPosition()));
  }

  @Override
  public double nextDouble() {
    return arguments.getDouble(moveForward(checkIteratorsPosition()));
  }

  @Override
  public Object next() {
    return arguments.getObject(moveForward(checkIteratorsPosition()));
  }

  @Override
  public String toString() {
    return "Current: " + (hasNext()? arguments.getObject(index) : "<invalid>");
  }
}
