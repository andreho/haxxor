package net.andreho.aop.spi.impl.arguments;

import net.andreho.aop.spi.Arguments;

import java.util.NoSuchElementException;

/**
 * <br/>Created by a.hofmann on 22.06.2017 at 00:19.
 */
public final class ArgumentsIteratorImpl implements Arguments.Iterator {

  private final AbstractArguments arguments;
  private final int length;
  private int index;

  public ArgumentsIteratorImpl(final AbstractArguments arguments) {
    this.length = arguments.length();
    this.arguments = arguments;
  }

  @Override
  public Arguments.Sort sort() {
    return arguments.getSort(checkIteratorPosition());
  }

  private int checkIteratorPosition() {
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
    return arguments.getBoolean(moveForward(checkIteratorPosition()));
  }

  @Override
  public byte nextByte() {
    return arguments.getByte(moveForward(checkIteratorPosition()));
  }

  @Override
  public short nextShort() {
    return arguments.getShort(moveForward(checkIteratorPosition()));
  }

  @Override
  public char nextChar() {
    return arguments.getChar(moveForward(checkIteratorPosition()));
  }

  @Override
  public int nextInt() {
    return arguments.getInt(moveForward(checkIteratorPosition()));
  }

  @Override
  public float nextFloat() {
    return arguments.getFloat(moveForward(checkIteratorPosition()));
  }

  @Override
  public long nextLong() {
    return arguments.getLong(moveForward(checkIteratorPosition()));
  }

  @Override
  public double nextDouble() {
    return arguments.getDouble(moveForward(checkIteratorPosition()));
  }

  @Override
  public Object next() {
    return arguments.getObject(moveForward(checkIteratorPosition()));
  }

  @Override
  public String toString() {
    return hasNext()? arguments.getObject(index) : "<invalid>";
  }
}
