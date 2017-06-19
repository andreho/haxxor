package net.andreho.aop.spi;

import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.spec.api.HxMethod;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 04:51.
 */
public interface InjectableResolver {

  /**
   * @param aspect
   * @param intercepted
   * @param codeStream
   */
  void inject(final AspectDefinition aspect,
              final HxMethod intercepted,
              final HxExtendedCodeStream codeStream);
}
