package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;

/**
 * <br/>Created by a.hofmann on 18.06.2017 at 08:12.
 */
public class MethodParametersMatcher
  extends AbstractMatcher<HxMethod> {

  private final ElementMatcher<HxParameter> parametersMatcher;

  public MethodParametersMatcher(final ElementMatcher<HxParameter> parametersMatcher) {
    this.parametersMatcher = parametersMatcher;
  }

  @Override
  public boolean isAny() {
    return parametersMatcher.isAny();
  }

  @Override
  public boolean matches(final HxMethod element) {
    if(element.getParametersCount() == 0) {
      return false;
    }

    final ElementMatcher<HxParameter> parameterMatcher = this.parametersMatcher;

    for(HxParameter parameter : element.getParameters()) {
      if(parameterMatcher.matches(parameter)) {
        return true;
      }
    }
    return false;
  }
}
