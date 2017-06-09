package net.andreho.haxxor.spec.visitors.generics.x;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxTypeVariable;

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
  public VariableControlledVisitor(final Haxxor haxxor,
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
