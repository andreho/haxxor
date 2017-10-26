package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.api.HxAnnotated;
import net.andreho.haxxor.api.HxNamed;
import net.andreho.haxxor.api.HxOrdered;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxTyped;

/**
 * <br/>Created by a.hofmann on 18.06.2017 at 08:12.
 */
public class ParametersMatcher
  extends AbstractMatcher<HxParameter> {

  private final ElementMatcher<HxOrdered> positioned;
  private final ElementMatcher<HxTyped> typed;
  private final ElementMatcher<HxAnnotated> annotated;
  private final ElementMatcher<HxNamed> named;

  public ParametersMatcher(final ElementMatcher<HxOrdered> positioned,
                           final ElementMatcher<HxTyped> typed,
                           final ElementMatcher<HxAnnotated> annotated,
                           final ElementMatcher<HxNamed> named) {
    this.positioned = positioned.minimize();
    this.typed = typed.minimize();
    this.annotated = annotated.minimize();
    this.named = named.minimize();
  }


  @Override
  public boolean isAny() {
    return positioned.isAny() &&
           typed.isAny() &&
           annotated.isAny() &&
           named.isAny();
  }

  @Override
  public boolean matches(final HxParameter parameter) {
    return positioned.matches(parameter) &&
           typed.matches(parameter) &&
           annotated.matches(parameter) &&
           named.matches(parameter);
  }
}
