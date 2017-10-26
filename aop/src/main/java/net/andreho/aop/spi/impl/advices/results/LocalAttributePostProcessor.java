package net.andreho.aop.spi.impl.advices.results;

import net.andreho.aop.api.injectable.Attribute;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectMethodContext;
import net.andreho.aop.spi.ResultPostProcessor;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxInstruction;

import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 01:42.
 */
public class LocalAttributePostProcessor
  implements ResultPostProcessor {
  private static final String ATTRIBUTE_ANNOTATION_TYPE_NAME = Attribute.class.getName();
  public static final LocalAttributePostProcessor INSTANCE = new LocalAttributePostProcessor();

  @Override
  public boolean handle(final AspectAdvice<?> aspectAdvice,
                        final AspectContext context,
                        final HxMethod interceptor,
                        final HxMethod original,
                        final HxMethod shadow,
                        final HxInstruction anchor) {
    final Optional<HxAnnotation> optional = interceptor.getAnnotation(ATTRIBUTE_ANNOTATION_TYPE_NAME);
    if(!optional.isPresent()) {
      return false;
    }

    final HxAnnotation attributeAnnotation = optional.get();
    final String attributeName = attributeAnnotation.getAttribute("value", "");

    if(attributeName.isEmpty()) {
      return false;
    }

    final AspectMethodContext methodContext = context.getAspectMethodContext();

    if(methodContext.hasLocalAttribute(attributeName)) {
      throw new IllegalStateException("Local attribute with given name was already declared: "+attributeName);
    }

    final int slotOffset = original.getParametersSlotSize() + (original.isStatic() ? 0 : 1);
    final HxType returnType = interceptor.getReturnType();
    final int variableSize = returnType.getSlotSize();

    HxCgenUtils.shiftAccessToLocalVariable(anchor, slotOffset, variableSize);
    methodContext.createLocalAttribute(returnType, attributeName, slotOffset);
    anchor.asAnchoredStream().GENERIC_STORE(returnType, slotOffset);

    return true;
  }
}
