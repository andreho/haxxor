package net.andreho.haxxor.api;

import java.util.List;
import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 08.06.2017 at 00:13.
 */
public interface HxGenericType extends HxProvider {
  /**
   * @return
   */
  HxType getDeclaringType();

  /**
   * @param name
   * @return
   */
  Optional<HxTypeVariable> getVariable(String name);

  /**
   * @return
   */
  List<HxTypeVariable> getTypeVariables();

  /**
   * @return
   */
  HxGenericElement<?> getSuperType();

  /**
   * @return
   */
  List<HxGenericElement<?>> getInterfaces();
}
