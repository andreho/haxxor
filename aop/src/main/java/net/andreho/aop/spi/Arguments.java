package net.andreho.aop.spi;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:21.
 */
public interface Arguments extends Iterable<Object> {

  /**
   *
   * @param idx
   * @return
   */
  boolean getBoolean(int idx);

  /**
   *
   * @param idx
   * @return
   */
  byte getByte(int idx);

  /**
   *
   * @param idx
   * @return
   */
  short getShort(int idx);

  /**
   *
   * @param idx
   * @return
   */
  char getChar(int idx);

  /**
   *
   * @param idx
   * @return
   */
  int getInt(int idx);

  /**
   *
   * @param idx
   * @return
   */
  float getFloat(int idx);

  /**
   *
   * @param idx
   * @return
   */
  long getLong(int idx);

  /**
   *
   * @param idx
   * @return
   */
  double getDouble(int idx);

  /**
   * @param idx
   * @param <T>
   * @return
   */
  <T> T getReference(int idx);

  /**
   *
   * @return
   */
  int length();

  /**
   * @return
   */
  Object[] toArray();
}
