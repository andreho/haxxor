package net.andreho.aop.spi.impl.advices.injector;

import net.andreho.aop.api.injectable.Attribute;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectLocalAttribute;
import net.andreho.aop.spi.AspectMethodContext;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.cgen.HxInstruction;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class LocalAttributeParameterInjector
  extends AbstractParameterInjector {

  private static final Class<Attribute> ATTRIBUTE_ANNOTATION_TYPE = Attribute.class;
  public static final LocalAttributeParameterInjector INSTANCE = new LocalAttributeParameterInjector();

  public LocalAttributeParameterInjector() {
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

    final AspectMethodContext methodContext = context.getAspectMethodContext();
    final AspectLocalAttribute localAttribute = methodContext.getLocalAttribute(attributeName);

    return scoreTypes(localAttribute.getType(), parameter.getType());
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
    final AspectMethodContext methodContext = context.getAspectMethodContext();

    final AspectLocalAttribute attribute = methodContext.getLocalAttribute(attributeName);
    anchor.asAnchoredStream()
          .GENERIC_LOAD(attribute.getType(), attribute.getIndex())
          .CONVERT(attribute.getType(), parameter.getType());
  }
}
