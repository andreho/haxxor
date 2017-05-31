package net.andreho.haxxor.spi;

/**
 * <br/>Created by a.hofmann on 30.05.2017 at 12:50.
 */
@FunctionalInterface
public interface HxTypeNamingStrategy {

  /**
   * Transforms given typename if needed to an internal classname form
   *
   * @param typeName to transform
   * @return corresponding typename in internal form
   */
  String toTypeName(String typeName);
}
