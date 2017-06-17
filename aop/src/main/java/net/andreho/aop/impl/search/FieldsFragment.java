package net.andreho.aop.impl.search;

import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMember;
import net.andreho.haxxor.spec.api.HxNamed;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 02:42.
 */
public class FieldsFragment
    extends AbstractFragment<HxField> {

  private final DisjunctionFragment<HxMember> modifiers;
  private final DisjunctionFragment<HxType> declaredBy;
  private final DisjunctionFragment<HxType> typed;
  private final DisjunctionFragment<HxAnnotated> annotated;
  private final DisjunctionFragment<HxNamed> named;

  public FieldsFragment(final DisjunctionFragment<HxMember> modifiers,
                        final DisjunctionFragment<HxType> declaredBy,
                        final DisjunctionFragment<HxType> typed,
                        final DisjunctionFragment<HxAnnotated> annotated,
                        final DisjunctionFragment<HxNamed> named) {
    this.declaredBy = declaredBy;
    this.typed = typed;
    this.named = named;
    this.modifiers = modifiers;
    this.annotated = annotated;
  }

  @Override
  public boolean test(final HxField field) {
    return modifiers.test(field) &&
           declaredBy.test(field.getDeclaringMember()) &&
           typed.test(field.getType()) &&
           annotated.test(field) &&
           named.test(field);
  }
}
