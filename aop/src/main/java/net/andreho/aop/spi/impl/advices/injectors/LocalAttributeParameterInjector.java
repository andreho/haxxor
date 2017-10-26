package net.andreho.aop.spi.impl.advices.injectors;

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
 * <br/>Created by a.hofmann on 21.06.2017 at 01:48.
 */
public class LocalAttributeParameterInjector
  extends AbstractAnnotatedParameterInjector {

  private static final String ATTRIBUTE_ANNOTATION_TYPE_NAME = Attribute.class.getName();
  public static final LocalAttributeParameterInjector INSTANCE = new LocalAttributeParameterInjector();

  public LocalAttributeParameterInjector() {
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
    final AspectMethodContext methodContext = context.getAspectMethodContext();

    if (attributeName.isEmpty()) {
      throw new IllegalStateException("Attribute's name is invalid.");
    }

    if (!methodContext.hasLocalAttribute(attributeName)) {
      return false;
    }

    final AspectLocalAttribute attribute = methodContext.getLocalAttribute(attributeName);
    anchor.asAnchoredStream()
          .GENERIC_LOAD(attribute.getType(), attribute.getIndex());

    return true;
  }
}
