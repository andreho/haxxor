package net.andreho.aop.spi.impl.steps.injectors;

import net.andreho.aop.api.injectable.Attribute;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectStep;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 01:48.
 */
public class FieldAttributeInjector
  extends AbstractAnnotatedInjector {

  public static final FieldAttributeInjector INSTANCE = new FieldAttributeInjector();
  private static final String ATTRIBUTE_ANNOTATION_TYPE_NAME = Attribute.class.getName();

  public FieldAttributeInjector() {
    super(Attribute.class.getName());
  }

  @Override
  protected boolean checkedParameterInjection(final AspectStep<?> aspectStep,
                                              final AspectContext context,
                                              final HxMethod interceptor,
                                              final HxMethod original,
                                              final HxMethod shadow,
                                              final HxParameter parameter,
                                              final HxInstruction instruction) {

    final HxAnnotation attributeAnnotation = parameter.getAnnotation(ATTRIBUTE_ANNOTATION_TYPE_NAME).get();
    final String attributeName = attributeAnnotation.getAttribute("value", "");

    if (attributeName.isEmpty()) {
      throw new IllegalStateException("Attribute's name is invalid.");
    }

    final HxType type = original.getDeclaringMember();
    final Collection<HxField> fields = type.fields(
      (field) ->
        field.getAnnotation(ATTRIBUTE_ANNOTATION_TYPE_NAME)
             .filter(hxAnnotation ->
                       attributeAnnotation
                         .equals(hxAnnotation.getAttribute("value", ""))
             )
             .isPresent()
    );

    if(fields.isEmpty()) {
      return false;
    }

    final HxField field = fields.iterator().next();

    if(field.isStatic()) {

    }

    return false;
  }
}
