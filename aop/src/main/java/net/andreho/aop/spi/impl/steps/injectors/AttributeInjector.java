package net.andreho.aop.spi.impl.steps.injectors;

import net.andreho.aop.api.injectable.Attribute;
import net.andreho.aop.spi.AspectAttribute;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectStep;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class AttributeInjector
  extends AbstractAnnotatedInjector {

  public static final AttributeInjector INSTANCE = new AttributeInjector();
  private static final String ATTRIBUTE_ANNOTATION_TYPE_NAME = Attribute.class.getName();

  public AttributeInjector() {
    super(Attribute.class.getName());
  }

  @Override
  protected void checkedParameterInjection(final AspectStep<?> aspectStep,
                                           final AspectContext context,
                                           final HxMethod interceptor,
                                           final HxMethod method,
                                           final HxParameter parameter,
                                           final HxInstruction instruction) {

    final HxAnnotation attributeAnnotation = parameter.getAnnotation(ATTRIBUTE_ANNOTATION_TYPE_NAME).get();
    final String attributeName = attributeAnnotation.getAttribute("value", "");

    if (attributeName.isEmpty()) {
      throw new IllegalStateException("Attribute's name is invalid.");
    }

    if (!context.hasLocalAttribute(attributeName)) {
      throw new IllegalStateException("There isn't any declared local attribute with name: " + attributeName);
    }

    final AspectAttribute attribute = context.getLocalAttribute(attributeName);
    HxCgenUtils.genericLoadSlot(attribute.getType(), attribute.getIndex(), instruction.asStream());
  }
}
