package net.andreho.haxxor.spec.visitors.generics.x;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxGeneric;
import net.andreho.haxxor.spec.api.HxTypeVariable;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <br/>Created by a.hofmann on 08.06.2017 at 19:41.
 */
public class SuperclassVisitor extends ClassTypeVisitor {

  /**
   * @param haxxor
   * @param consumer
   * @param variableResolver
   */
  public SuperclassVisitor(final Haxxor haxxor,
                           final Consumer<HxGeneric<?>> consumer,
                           final Function<String, HxTypeVariable> variableResolver) {
    super(haxxor, consumer, variableResolver);
  }
}
