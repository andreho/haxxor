package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.spi.AspectAdviceParameterInjector;
import net.andreho.aop.spi.AspectAdviceResultHandler;
import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionsType;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.misc.LINE_NUMBER;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxMethodBody;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:34.
 */
public abstract class AbstractCallingAspectAdvice<T>
  extends AbstractAspectAdvice<T> {

  private final HxMethod interceptor;
  private final AspectAdviceParameterInjector parameterInjector;
  private final AspectAdviceResultHandler resultHandler;

  public AbstractCallingAspectAdvice(final int index,
                                     final AspectAdviceType type,
                                     final ElementMatcher<T> elementMatcher,
                                     final String profileName,
                                     final HxMethod interceptor,
                                     final AspectAdviceParameterInjector parameterInjector,
                                     final AspectAdviceResultHandler resultHandler) {
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

  public AspectAdviceParameterInjector getParameterInjector() {
    return parameterInjector;
  }

  public AspectAdviceResultHandler getResultHandler() {
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
      .setModifiers(HxMethod.Modifiers.PRIVATE, HxMethod.Modifiers.SYNTHETIC) // HxMethod.Modifiers.BRIDGE
      //IS IT A HACK OR NOT?
      .addAnnotation(original.getHaxxor().createAnnotation("java.lang.invoke.ForceInline", true));

    original.getBody().moveTo(targetMethod);

    type.addMethod(targetMethod);

    final boolean isStatic = original.isStatic();
    final HxMethodBody body = original.getBody();
    final HxInstruction first = body.getFirst();
    final LABEL label = new LABEL();

    appendInitialLabelAndLineNumberIfAvailable(targetMethod, first, label);

    final HxExtendedCodeStream stream = label.asStream();

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

  private void appendInitialLabelAndLineNumberIfAvailable(final HxMethod targetMethod,
                                                          final HxInstruction first,
                                                          final LABEL label) {
    first.append(label);
    targetMethod.getBody().getFirst().findFirst(ins -> ins.hasType(HxInstructionsType.Special.LINE_NUMBER))
                .ifPresent(ins -> label.append(new LINE_NUMBER(((LINE_NUMBER)ins).getLine(), label)));
  }

  private static Optional<String> asOptional(final String profileName) {
    return profileName == null || profileName.isEmpty() ? Optional.empty() : Optional.of(profileName);
  }
}
