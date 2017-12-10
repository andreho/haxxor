package net.andreho.func;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:49.
 */
@FunctionalInterface
public interface Bindable<T,X extends FX> {

  /**
   * @param target
   * @return
   */
  X bind(T target);
}
