package net.andreho.aop.spi.impl.advices.injectors;

import net.andreho.aop.api.injectable.Attribute;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 01:48.
 */
public class FieldAttributeParameterInjector
  extends AbstractAnnotatedParameterInjector {

  private static final String ATTRIBUTE_ANNOTATION_TYPE_NAME = Attribute.class.getName();
  public static final FieldAttributeParameterInjector INSTANCE = new FieldAttributeParameterInjector();

  public FieldAttributeParameterInjector() {
    super(ATTRIBUTE_ANNOTATION_TYPE_NAME);
  }

  @Override
  protected boolean checkedParameterInjection(final AspectAdvice<?> aspectAdvice,
                                              final AspectContext context,
                                              final HxMethod interceptor,
                                              final HxMethod original,
                                              final HxMethod shadow,
                                              final HxParameter parameter,
                                              final HxInstruction anchor) {

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
                       attributeName
                         .equals(hxAnnotation.getAttribute("value", ""))
             )
             .isPresent(), true
    );

    if (fields.isEmpty()) {
      return false;
    }

    final HxField field = fields.iterator().next();

    if (!field.isAccessibleFrom(original)) {
      return false;
    }

    final HxExtendedCodeStream stream = anchor.asAnchoredStream();

    if (field.isStatic()) {
      stream.GETSTATIC(field);
    } else {
      stream.THIS().GETFIELD(field);
    }

    return true;
  }
}
