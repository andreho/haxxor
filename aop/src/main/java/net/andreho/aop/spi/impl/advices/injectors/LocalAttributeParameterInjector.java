package net.andreho.aop.spi.impl.advices.injectors;

import net.andreho.aop.api.injectable.Attribute;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectAttribute;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 01:48.
 */
public class LocalAttributeParameterInjector
  extends AbstractAnnotatedParameterInjector {

  public static final LocalAttributeParameterInjector INSTANCE = new LocalAttributeParameterInjector();
  private static final String ATTRIBUTE_ANNOTATION_TYPE_NAME = Attribute.class.getName();

  public LocalAttributeParameterInjector() {
    super(Attribute.class.getName());
  }

  @Override
  protected boolean checkedParameterInjection(final AspectAdvice<?> aspectAdvice,
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

    if (!context.hasLocalAttribute(attributeName)) {
      return false;
    }

    final AspectAttribute attribute = context.getLocalAttribute(attributeName);
    HxCgenUtils.genericLoadSlot(attribute.getType(), attribute.getIndex(), instruction.asStream());

    return true;
  }
}
