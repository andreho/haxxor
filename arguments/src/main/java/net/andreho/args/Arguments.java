package net.andreho.args;

import java.net.URLClassLoader;

import static net.andreho.args.ArgumentsClassFactory.createArgumentsClassFor;
import static net.andreho.args.DynamicClassPathInstaller.installDynamicClassPathForArguments;

/**
 * <br/>Created by a.hofmann on 15.07.2017 at 05:23.
 */
public interface Arguments extends Iterable<Object> {
  /**
   * @return
   */
  static Arguments empty() {
    return EmptyArguments.INSTANCE;
  }

  /**
   * Installs a dynamic class path for generated arguments classes and
   * uses system class-loader as installation point.
   */
  static void install() {
    installDynamicClassPathForArguments();
  }

  /**
   * Installs a dynamic class path for generated classes and
   * uses given class-loader as installation point (may vary depending on class-loader hierarchy).
   * @param classLoader to use
   */
  static void install(URLClassLoader classLoader) {
    installDynamicClassPathForArguments(classLoader);
  }

  /**
   * @param signature of the requested class
   * @return classname of the generated or already existing class
   */
  static String generateFor(final Class<?> ... signature) {
    return createArgumentsClassFor(signature);
  }

  /**
   * @param signature of the requested class
   * @return classname of the generated or already existing class
   */
  static String generateFor(final String signature) {
    return createArgumentsClassFor(signature);
  }

  /**
   * @param idx
   * @return
   */
  ArgumentsType getType(int idx);

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
   * @param idx
   * @param value
   * @return
   */
  Arguments setBoolean(int idx, boolean value);

  /**
   *
   * @param idx
   * @param value
   * @return
   */
  Arguments setByte(int idx, byte value);

  /**
   *
   * @param idx
   * @param value
   * @return
   */
  Arguments setShort(int idx, short value);

  /**
   *
   * @param idx
   * @param value
   * @return
   */
  Arguments setChar(int idx, char value);

  /**
   *
   * @param idx
   * @param value
   * @return
   */
  Arguments setInt(int idx, int value);

  /**
   *
   * @param idx
   * @param value
   * @return
   */
  Arguments setFloat(int idx,  float value);

  /**
   *
   * @param idx
   * @param value
   * @return
   */
  Arguments setLong(int idx, long value);

  /**
   *
   * @param idx
   * @param value
   * @return
   */
  Arguments setDouble(int idx, double value);

  /**
   * @param idx
   * @param value
   * @return
   */
  Arguments setObject(int idx, Object value);

  /**
   * @return <b>true</b> if {@link #length()} is equal to zero or <b>false</b> if not.
   */
  boolean isEmpty();

  /**
   * @return length of the argument's list
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
  ArgumentsIterator iterator();
}
