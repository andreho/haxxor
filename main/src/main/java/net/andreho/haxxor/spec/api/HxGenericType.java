package net.andreho.haxxor.spec.api;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 08.06.2017 at 00:13.
 */
public interface HxGenericType {

  /**
   * @param signature
   */
  void interpret(String signature);

  /**
   * @return
   */
  List<HxTypeVariable> getTypeVariables();

  /**
   * @return
   */
  HxGeneric<?> getSuperType();

  /**
   * @return
   */
  List<HxGeneric<?>> getInterfaces();
}
