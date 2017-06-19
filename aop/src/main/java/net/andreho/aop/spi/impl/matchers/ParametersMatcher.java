package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxNamed;
import net.andreho.haxxor.spec.api.HxOrdered;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxTyped;

/**
 * <br/>Created by a.hofmann on 18.06.2017 at 08:12.
 */
public class ParametersMatcher
  extends AbstractMatcher<HxMethod> {

  private final ElementMatcher<HxOrdered> positioned;
  private final ElementMatcher<HxTyped> typed;
  private final ElementMatcher<HxAnnotated> annotated;
  private final ElementMatcher<HxNamed> named;

  public ParametersMatcher(final ElementMatcher<HxOrdered> positioned,
                           final ElementMatcher<HxTyped> typed,
                           final ElementMatcher<HxAnnotated> annotated,
                           final ElementMatcher<HxNamed> named) {
    this.positioned = positioned;
    this.typed = typed;
    this.annotated = annotated;
    this.named = named;
  }


  @Override
  public boolean isAny() {
    return positioned.isAny() &&
           typed.isAny() &&
           annotated.isAny() &&
           named.isAny();
  }

  @Override
  public boolean match(final HxMethod element) {
    if(element.getParametersCount() == 0) {
      return false;
    }

    for(HxParameter parameter : element.getParameters()) {
      if(!matchParameter(parameter)) {
        return false;
      }
    }
    return true;
  }

  private boolean matchParameter(final HxParameter parameter) {
    return positioned.match(parameter) &&
           typed.match(parameter) &&
           annotated.match(parameter) &&
           named.match(parameter);
  }
}
