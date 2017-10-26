package net.andreho.aop.spi.impl.advices.injector;

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
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class FieldAttributeParameterInjector
  extends AbstractParameterInjector {

  private static final Class<Attribute> ATTRIBUTE_ANNOTATION_TYPE = Attribute.class;
  public static final FieldAttributeParameterInjector INSTANCE = new FieldAttributeParameterInjector();

  public FieldAttributeParameterInjector() {
  }

  @Override
  public int score(final AspectContext context,
                   final HxMethod original,
                   final HxMethod interceptor,
                   final HxParameter parameter) {
    if(!parameter.isAnnotationPresent(ATTRIBUTE_ANNOTATION_TYPE)) {
      return NOT_INJECTABLE;
    }

    final HxAnnotation attributeAnnotation = parameter.getAnnotation(ATTRIBUTE_ANNOTATION_TYPE).get();
    final String attributeName = attributeAnnotation.getAttribute("value", "");

    if (attributeName.isEmpty()) {
      return NOT_INJECTABLE;
    }

    final HxType type = original.getDeclaringType();
    final Collection<HxField> fields = type.fields(
      (field) ->
        field.getAnnotation(ATTRIBUTE_ANNOTATION_TYPE)
             .filter(hxAnnotation ->
                       attributeName.equals(hxAnnotation.getAttribute("value", ""))
             )
             .isPresent(), true
    );

    if (fields.isEmpty()) {
      return NOT_INJECTABLE;
    }

    final HxField field = fields.iterator().next();

    if (!field.isAccessibleFrom(original)) {
      return NOT_INJECTABLE;
    }

    return scoreTypes(field.getType(), parameter.getType());
  }

  @Override
  public void inject(final AspectAdvice<?> aspectAdvice,
                     final AspectContext context,
                     final HxMethod original,
                     final HxMethod shadow,
                     final HxMethod interceptor,
                     final HxParameter parameter,
                     final HxInstruction anchor) {

    final HxAnnotation attributeAnnotation = parameter.getAnnotation(ATTRIBUTE_ANNOTATION_TYPE).get();
    final String attributeName = attributeAnnotation.getAttribute("value", "");

    if (attributeName.isEmpty()) {
      throw new IllegalStateException("Attribute's name is invalid.");
    }

    final HxType type = original.getDeclaringMember();
    final Collection<HxField> fields = type.fields(
      (field) ->
        field.getAnnotation(ATTRIBUTE_ANNOTATION_TYPE)
             .filter(hxAnnotation ->
                       attributeName
                         .equals(hxAnnotation.getAttribute("value", ""))
             )
             .isPresent(), true
    );

    final HxField field = fields.iterator().next();
    final HxExtendedCodeStream stream = anchor.asAnchoredStream();

    if (field.isStatic()) {
      stream.GETSTATIC(field);
    } else {
      stream.THIS().GETFIELD(field);
    }
    stream.CONVERT(field.getType(), parameter.getType());
  }
}
