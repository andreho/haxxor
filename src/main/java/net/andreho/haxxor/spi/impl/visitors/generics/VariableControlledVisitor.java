package net.andreho.haxxor.spi.impl.visitors.generics;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxTypeVariable;

import java.util.function.Function;

/**
 * <br/>Created by a.hofmann on 08.06.2017 at 18:39.
 */
public class VariableControlledVisitor
    extends ControlledVisitor {

  private final Function<String, HxTypeVariable> variableResolver;

  /**
   * @param haxxor
   * @param variableResolver
   */
  public VariableControlledVisitor(final Hx haxxor,
                                   final Function<String, HxTypeVariable> variableResolver) {
    super(haxxor);
    this.variableResolver = variableResolver;
  }

  protected HxTypeVariable typeVariable(String name) {
    return variableResolver.apply(name);
  }

  protected Function<String, HxTypeVariable> getVariableResolver() {
    return variableResolver;
  }
}
