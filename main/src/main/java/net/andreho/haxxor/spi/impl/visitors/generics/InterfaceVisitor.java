package net.andreho.haxxor.spi.impl.visitors.generics;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxGenericElement;
import net.andreho.haxxor.api.HxTypeVariable;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <br/>Created by a.hofmann on 08.06.2017 at 19:41.
 */
public class InterfaceVisitor
    extends ClassTypeVisitor {

  /**
   * @param haxxor
   * @param consumer
   * @param variableResolver
   */
  public InterfaceVisitor(final Hx haxxor,
                          final Consumer<HxGenericElement<?>> consumer,
                          final Function<String, HxTypeVariable> variableResolver) {
    super(haxxor, consumer, variableResolver);
  }
}
