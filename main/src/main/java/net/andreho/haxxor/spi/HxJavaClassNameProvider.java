package net.andreho.haxxor.spi;

/**
 * <br/>Created by a.hofmann on 30.05.2017 at 12:50.
 */
@FunctionalInterface
public interface HxJavaClassNameProvider {
  /**
   * Transforms given typename if needed to a fully-qualified Java classname form
   * (like: int, byte[], java.lang.String etc.)
   *
   * @param typeName to transform
   * @return corresponding typename in internal form
   */
  String toJavaClassName(String typeName);
}
