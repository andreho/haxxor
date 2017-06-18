package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.AspectMatcher;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMember;
import net.andreho.haxxor.spec.api.HxNamed;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 02:42.
 */
public class FieldsMatcher
    extends AbstractMatcher<HxField> {

  private final AspectMatcher<HxMember> modifiers;
  private final AspectMatcher<HxField> declaredBy;
  private final AspectMatcher<HxType> typed;
  private final AspectMatcher<HxNamed> named;
  private final AspectMatcher<HxAnnotated> annotated;

  public FieldsMatcher(final AspectMatcher<HxMember> modifiers,
                       final AspectMatcher<HxField> declaredBy,
                       final AspectMatcher<HxType> typed,
                       final AspectMatcher<HxNamed> named,
                       final AspectMatcher<HxAnnotated> annotated) {
    this.declaredBy = declaredBy;
    this.typed = typed;
    this.named = named;
    this.modifiers = modifiers;
    this.annotated = annotated;
  }

  @Override
  public boolean match(final HxField field) {
    return modifiers.match(field) &&
           declaredBy.match(field) &&
           typed.match(field.getType()) &&
           named.match(field) &&
           annotated.match(field);
  }
}
