package net.andreho.func;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:46.
 */
public interface FX<R> extends F {

  /**
   * @param args
   * @return
   */
  R call(Object... args);
}
