package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.spi.AspectAdviceParameterInjector;
import net.andreho.aop.spi.AspectAdvicePostProcessor;
import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectMethodContext;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionsType;
import net.andreho.haxxor.cgen.HxLocalVariable;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.misc.LINE_NUMBER;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxMethodBody;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:34.
 */
public abstract class AbstractShadowingAspectAdvice<T>
  extends AbstractAspectAdvice<T> {

  private final HxMethod interceptor;
  private final AspectAdviceParameterInjector parameterInjector;
  private final AspectAdvicePostProcessor resultHandler;

  public AbstractShadowingAspectAdvice(final int index,
                                       final AspectAdviceType type,
                                       final ElementMatcher<T> elementMatcher,
                                       final String profileName,
                                       final HxMethod interceptor,
                                       final AspectAdviceParameterInjector parameterInjector,
                                       final AspectAdvicePostProcessor resultHandler) {
    super(index,
          type,
          elementMatcher,
          profileName);

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

  public AspectAdvicePostProcessor getResultHandler() {
    return resultHandler;
  }

  protected HxMethod findOrCreateShadowMethod(AspectContext context,
                                              HxMethod original,
                                              String shadowName) {

    final Haxxor haxxor = original.getHaxxor();
    final HxType type = original.getDeclaringMember();
    final boolean constructor = original.isConstructor();
    List<HxType> parameterTypes = original.getParameterTypes();

    if(constructor) {
      shadowName = "<init>";
      parameterTypes = new ArrayList<>(parameterTypes.size() + 1);
      parameterTypes.addAll(original.getParameterTypes());
      parameterTypes.add(haxxor.reference("java.lang.Void"));
    }

    final Optional<HxMethod> methodOptional =
      type.findMethod(original.getReturnType(), shadowName, parameterTypes);

    if (methodOptional.isPresent()) {
      return methodOptional.get();
    }

    final HxMethod shadowMethod = original
      .clone(shadowName, 0)
      .setModifiers(HxMethod.Modifiers.PRIVATE, HxMethod.Modifiers.SYNTHETIC) // HxMethod.Modifiers.BRIDGE
      //IS IT A HACK OR NOT?
      .addAnnotation(haxxor.createAnnotation("java.lang.invoke.ForceInline", true));

    original.getBody().moveTo(shadowMethod);

    if(constructor) {
      HxCgenUtils.shiftAccessToLocalVariable (
        shadowMethod.getBody().getFirst(),
        original.getParametersSlotSize() + 1,
        1);

      shadowMethod.addParameter (
        haxxor.createParameter(haxxor.reference("java.lang.Void"))
              .setModifiers(HxParameter.Modifiers.SYNTHETIC));
    }

    type.addMethod(shadowMethod);

    final boolean isStatic = original.isStatic();
    final HxMethodBody body = original.getBody();
    final HxInstruction first = body.getFirst();

    final LABEL startLabel = new LABEL();
    final LABEL delegationLabel = new LABEL();

    first.append(startLabel);

    final HxExtendedCodeStream stream =
      appendLineNumberIfAvailable(shadowMethod, startLabel).asStream().LABEL(delegationLabel);

    if (isStatic) {
      shadowMethod.addModifiers(HxMethod.Modifiers.STATIC);
    } else {
      stream.THIS();
    }

    stream.GENERIC_LOAD(shadowMethod.getParameterTypes(), isStatic ? 0 : 1);

    if (isStatic) {
      stream.INVOKESTATIC(shadowMethod);
    } else {
      if(constructor) {
        stream.ACONST_NULL(); //for the void parameter
      }
      stream.INVOKESPECIAL(shadowMethod);
    }

    final LABEL endLabel = new LABEL();
    stream.GENERIC_RETURN(shadowMethod.getReturnType().getSort())
          .LABEL(endLabel);

    copyLocalVariables(shadowMethod, body, startLabel, endLabel);
    initializeMethodContext(context, original, shadowMethod, startLabel, delegationLabel, endLabel);

    return shadowMethod;
  }

  private void initializeMethodContext(final AspectContext context,
                                       final HxMethod original,
                                       final HxMethod shadowMethod,
                                       final LABEL startLabel,
                                       final LABEL delegationLabel,
                                       final LABEL endLabel) {
    final AspectMethodContext methodContext = context.getAspectMethodContext();

    methodContext.setOriginalMethod(original);
    methodContext.setShadowMethod(shadowMethod);

    methodContext.setStart(startLabel);
    methodContext.setDelegation(delegationLabel);
    methodContext.setEnd(endLabel);
  }

  private void copyLocalVariables(final HxMethod shadowMethod,
                                  final HxMethodBody body,
                                  final LABEL startLabel,
                                  final LABEL endLabel) {
    for (HxLocalVariable originalVariable : shadowMethod.getBody().getLocalVariables()) {
      HxLocalVariable newVariable = HxLocalVariable.createLocalVariable();

      newVariable.setStart(startLabel);
      newVariable.setEnd(endLabel);
      newVariable.setName(originalVariable.getName());
      newVariable.setIndex(originalVariable.getIndex());
      newVariable.setDescriptor(originalVariable.getDescriptor());
      newVariable.setSignature(originalVariable.getSignature());

      body.addLocalVariable(newVariable);
    }
  }

  private HxInstruction appendLineNumberIfAvailable(final HxMethod targetMethod,
                                           final LABEL labelToAppendTo) {
    Optional<HxInstruction> lineNumber =
      targetMethod.getBody().getFirst().findFirst(
        ins -> ins.hasType(HxInstructionsType.Special.LINE_NUMBER)
      );

    if(lineNumber.isPresent()) {
      LINE_NUMBER instruction = (LINE_NUMBER) lineNumber.get();
      return instruction.append(new LINE_NUMBER(instruction.getLine(), labelToAppendTo));
    }
    return labelToAppendTo;
  }

  protected void postProcessResult(final AspectContext context,
                                   final HxMethod interceptor,
                                   final HxMethod original,
                                   final HxMethod shadow,
                                   final HxInstruction instruction) {

    getResultHandler().process(this, context, interceptor, shadow, shadow, instruction);
  }

  protected void injectParameters(final AspectContext ctx,
                                  final HxMethod interceptor,
                                  final HxMethod original,
                                  final HxMethod shadow,
                                  final HxInstruction instruction) {

    final AspectAdviceParameterInjector parameterInjector = getParameterInjector();
    final HxInstruction anchor = instruction.getNext();

    for (HxParameter parameter : interceptor.getParameters()) {
      if (!parameterInjector.injectParameter(this, ctx, interceptor, original, shadow, parameter, anchor.getPrevious())) {
        throw new IllegalStateException(
          "Unable to inject parameter of " + interceptor + " at position: " + parameter.getIndex());
      }
    }
  }


  @Override
  public boolean equals(final Object o) {
    if (!super.equals(o)) {
      return false;
    }
    if (!(o instanceof AbstractShadowingAspectAdvice)) {
      return false;
    }

    final AbstractShadowingAspectAdvice<?> that = (AbstractShadowingAspectAdvice<?>) o;
    return interceptor.equals(that.interceptor);
  }

  @Override
  public int hashCode() {
    return super.hashCode() * 31 +
           interceptor.hashCode();
  }
}
