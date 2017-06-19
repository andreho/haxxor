package net.andreho.aop.spi;

import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.spec.api.HxExecutable;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 04:51.
 */
public interface InjectableResolver<Executable extends HxExecutable<Executable>> {

  /**
   * @param aspect
   * @param intercepted
   * @param codeStream
   */
  void inject(final AspectDefinition aspect,
              final Executable intercepted,
              final HxExtendedCodeStream codeStream);
}
