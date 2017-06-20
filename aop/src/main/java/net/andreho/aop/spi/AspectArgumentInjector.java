package net.andreho.aop.spi;

import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 23:37.
 */
public interface AspectArgumentInjector {

  /**
   * Injects value into the parameter under the given index
   * @param context to use
   * @param interceptor to invoke
   * @param method that should be modified
   * @param instruction for injection
   * @param index is parameter's index
   * @return <b>true</b> if injection was successful or
   * <b>false</b> to signal that the injection value isn't available, which leads to default injection.
   */
  boolean injectParameter(
    AspectApplicationContext context,
    HxMethod interceptor,
    HxMethod method,
    HxInstruction instruction,
    int index);
}
