package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.api.HxAnnotated;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMember;
import net.andreho.haxxor.api.HxNamed;
import net.andreho.haxxor.api.HxType;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 02:42.
 */
public class FieldsMatcher
    extends AbstractMatcher<HxField> {

  private final ElementMatcher<HxMember> modifiers;
  private final ElementMatcher<HxField> declaredBy;
  private final ElementMatcher<HxType> typed;
  private final ElementMatcher<HxNamed> named;
  private final ElementMatcher<HxAnnotated> annotated;

  public FieldsMatcher(final ElementMatcher<HxMember> modifiers,
                       final ElementMatcher<HxField> declaredBy,
                       final ElementMatcher<HxType> typed,
                       final ElementMatcher<HxNamed> named,
                       final ElementMatcher<HxAnnotated> annotated) {
    this.declaredBy = declaredBy;
    this.typed = typed;
    this.named = named;
    this.modifiers = modifiers;
    this.annotated = annotated;
  }

  @Override
  public boolean matches(final HxField field) {
    return modifiers.matches(field) &&
           declaredBy.matches(field) &&
           typed.matches(field.getType()) &&
           named.matches(field) &&
           annotated.matches(field);
  }
}
