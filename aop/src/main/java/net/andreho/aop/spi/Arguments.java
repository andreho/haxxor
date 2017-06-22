package net.andreho.aop.spi;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:21.
 */
public interface Arguments extends Iterable<Object> {
  enum Sort {
    BOOLEAN,
    BYTE,
    SHORT,
    CHAR,
    INT,
    FLOAT,
    LONG,
    DOUBLE,
    OBJECT;

    public boolean isPrimitive() {
      return this != OBJECT;
    }

    public static Sort fromCode(int code) {
      switch (code) {
        case 0: return BOOLEAN;
        case 1: return BYTE;
        case 2: return SHORT;
        case 3: return CHAR;
        case 4: return INT;
        case 5: return FLOAT;
        case 6: return LONG;
        case 7: return DOUBLE;
          default: return OBJECT;
      }
    }
  }

  /**
   * @param idx
   * @return
   */
  Sort getSort(int idx);

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
  <T> T getObject(int idx);

  /**
   *
   * @return
   */
  int length();

  /**
   * @return
   */
  Object[] toArray();

  /**
   * @return
   */
  @Override
  Iterator iterator();

  /**
   *
   */
  interface Iterator
    extends java.util.Iterator<Object> {
    Sort sort();
    boolean nextBoolean();
    byte nextByte();
    short nextShort();
    char nextChar();
    int nextInt();
    float nextFloat();
    long nextLong();
    double nextDouble();
  }
}
