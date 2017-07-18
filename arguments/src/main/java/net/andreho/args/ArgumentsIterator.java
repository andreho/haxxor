package net.andreho.args;

import java.util.Iterator;

/**
 * <br/>Created by a.hofmann on 15.07.2017 at 06:09.
 */
public interface ArgumentsIterator extends Iterator<Object> {
  ArgumentsType type();
  boolean nextBoolean();
  byte nextByte();
  short nextShort();
  char nextChar();
  int nextInt();
  float nextFloat();
  long nextLong();
  double nextDouble();
}
