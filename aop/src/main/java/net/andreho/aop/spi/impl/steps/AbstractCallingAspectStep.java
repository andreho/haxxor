package net.andreho.aop.spi.impl.steps;

import net.andreho.aop.spi.AspectStepParameterInjector;
import net.andreho.aop.spi.AspectStepResultHandler;
import net.andreho.aop.spi.AspectStepType;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:34.
 */
public abstract class AbstractCallingAspectStep<T>
  extends AbstractAspectStep<T> {

  private final HxMethod interceptor;
  private final AspectStepParameterInjector parameterInjector;
  private final AspectStepResultHandler resultHandler;

  public AbstractCallingAspectStep(final int index,
                                   final AspectStepType type,
                                   final ElementMatcher<T> elementMatcher,
                                   final String profileName,
                                   final HxMethod interceptor,
                                   final AspectStepParameterInjector parameterInjector,
                                   final AspectStepResultHandler resultHandler) {
    super(index,
          type,
          elementMatcher,
          asOptional(profileName));

    this.interceptor = requireNonNull(interceptor);
    this.parameterInjector = requireNonNull(parameterInjector);
    this.resultHandler = requireNonNull(resultHandler);
  }

  @Override
  public boolean needsAspectFactory() {
    return !getInterceptor().isStatic();
  }

  @Override
  public HxMethod getInterceptor() {
    return interceptor;
  }

  public AspectStepParameterInjector getParameterInjector() {
    return parameterInjector;
  }

  public AspectStepResultHandler getResultHandler() {
    return resultHandler;
  }

  protected HxMethod findOrCreateShadowMethod(HxMethod original,
                                              String shadowName) {
    final HxType type = original.getDeclaringMember();

    final Optional<HxMethod> methodOptional = type.findMethod(original.getReturnType(), shadowName,
                                                              original.getParameterTypes());
    if (methodOptional.isPresent()) {
      return methodOptional.get();
    }

    final HxMethod targetMethod = original
      .clone(shadowName, 0)
      .setModifiers(HxMethod.Modifiers.PRIVATE, HxMethod.Modifiers.SYNTHETIC)
      //IS IT A HACK OR NOT?
      .addAnnotation(original.getHaxxor().createAnnotation("java.lang.invoke.ForceInline", true));

    original.getBody().moveTo(targetMethod);

    type.addMethod(targetMethod);

    final boolean isStatic = original.isStatic();
    final HxExtendedCodeStream stream = original.getBody().build(true);
    stream.LABEL(new LABEL());

    if (isStatic) {
      targetMethod.addModifiers(HxMethod.Modifiers.STATIC);
    } else {
      stream.THIS();
    }

    HxCgenUtils.genericLoadTypes(targetMethod.getParameterTypes(), isStatic ? 0 : 1, stream);

    if (isStatic) {
      stream.INVOKESTATIC(targetMethod);
    } else {
      stream.INVOKESPECIAL(targetMethod);
    }

    HxCgenUtils.genericReturnValue(targetMethod.getReturnType(), stream);

    stream.LABEL(new LABEL());

    return targetMethod;
  }

  private static Optional<String> asOptional(final String profileName) {
    return profileName == null || profileName.isEmpty() ? Optional.empty() : Optional.of(profileName);
  }
}
