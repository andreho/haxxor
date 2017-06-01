package net.andreho.haxxor.spec.api;

/**
 * <br/>Created by a.hofmann on 31.05.2017 at 18:53.
 */
public interface HxTypeReference extends HxType {
  /**
   * @return the resolved type
   */
  HxType toType();
}
