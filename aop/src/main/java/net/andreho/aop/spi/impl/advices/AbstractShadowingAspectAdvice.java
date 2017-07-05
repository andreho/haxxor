package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.api.Placeholder;
import net.andreho.aop.spi.AspectAdviceParameterInjector;
import net.andreho.aop.spi.AspectAdvicePostProcessor;
import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectLocalAttribute;
import net.andreho.aop.spi.AspectMethodContext;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionsType;
import net.andreho.haxxor.cgen.HxLocalVariable;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.NAMED_LABEL;
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

  protected static final String PLACEHOLDER_CLASSNAME = Placeholder.class.getName();
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

    if (constructor) {
      shadowName = "<init>";
      parameterTypes = new ArrayList<>(parameterTypes.size() + 1);
      parameterTypes.addAll(original.getParameterTypes());
      parameterTypes.add(haxxor.reference(PLACEHOLDER_CLASSNAME));
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

    final boolean isStatic = original.isStatic();
    final HxMethodBody body = original.getBody();
    final HxInstruction first = body.getFirst();

    final LABEL startLabel = new NAMED_LABEL("START");
    final LABEL delegationStart = new NAMED_LABEL("D_START");

    first.append(startLabel);

    final HxExtendedCodeStream stream =
      appendLineNumberIfAvailable(shadowMethod, startLabel)
        .append(delegationStart)
        .asStream();

    if (isStatic) {
      shadowMethod.addModifiers(HxMethod.Modifiers.STATIC);
    } else {
      stream.THIS();
    }

    stream.GENERIC_LOAD(shadowMethod.getParameterTypes(), isStatic ? 0 : 1);

    if (constructor) {
      HxCgenUtils.shiftAccessToLocalVariable(
        shadowMethod.getBody().getFirst(),
        original.getParametersSlotSize() + 1,
        1);

      shadowMethod.addParameter(
        haxxor.createParameter(haxxor.reference(PLACEHOLDER_CLASSNAME))
              .setModifiers(HxParameter.Modifiers.SYNTHETIC));

    }

    type.addMethod(shadowMethod);

    if (isStatic) {
      stream.INVOKESTATIC(shadowMethod);
    } else {
      if (constructor) {
        stream.ACONST_NULL(); //for the void parameter
      }
      stream.INVOKESPECIAL(shadowMethod);
    }


    final LABEL delegationEnd = new NAMED_LABEL("D_END");
    final LABEL endLabel = new NAMED_LABEL("END");

    stream
      .LABEL(delegationEnd)
      .GENERIC_RETURN(shadowMethod.getReturnType().getSort())
      .LABEL(endLabel);

    copyLocalVariables(shadowMethod, body, startLabel, endLabel);

    initializeMethodContext(context,
                            original,
                            shadowMethod,
                            startLabel,
                            delegationStart,
                            delegationEnd,
                            endLabel);

    return shadowMethod;
  }

  private void initializeMethodContext(final AspectContext context,
                                       final HxMethod original,
                                       final HxMethod shadowMethod,
                                       final LABEL startLabel,
                                       final LABEL delegationStart,
                                       final LABEL delegationEnd,
                                       final LABEL endLabel) {
    final AspectMethodContext methodContext = context.getAspectMethodContext();

    methodContext.setOriginalMethod(original);
    methodContext.setShadowMethod(shadowMethod);

    methodContext.setStart(startLabel);
    methodContext.setDelegationStart(delegationStart);
    methodContext.setDelegationEnd(delegationEnd);
    methodContext.setEnd(endLabel);
  }

  private void copyLocalVariables(final HxMethod shadowMethod,
                                  final HxMethodBody body,
                                  final LABEL startLabel,
                                  final LABEL endLabel) {
    for (HxLocalVariable originalVariable : shadowMethod.getBody().getLocalVariables()) {
      HxLocalVariable newVariable = HxLocalVariable.createLocalVariable();

      newVariable
        .setIndex(originalVariable.getIndex())
        .setName(originalVariable.getName())
        .setDescriptor(originalVariable.getDescriptor())
        .setSignature(originalVariable.getSignature())
        .setStart(startLabel)
        .setEnd(endLabel);

      body.addLocalVariable(newVariable);
    }
  }

  private HxInstruction appendLineNumberIfAvailable(final HxMethod targetMethod,
                                                    final LABEL newBeginLabel) {
    Optional<HxInstruction> inst =
      targetMethod.getBody().getFirst().findFirst(
        ins -> ins.hasType(HxInstructionsType.Special.LINE_NUMBER)
      );

    if (inst.isPresent()) {
      LINE_NUMBER originalLineNumber = (LINE_NUMBER) inst.get();
      return newBeginLabel.append(new LINE_NUMBER(originalLineNumber.getLine(), newBeginLabel));
    }
    return newBeginLabel;
  }

  protected HxMethodBody addNewLocalAttributes(final HxMethod original,
                                               final AspectMethodContext methodContext) {
    final HxMethodBody body = original.getBody();
    for(AspectLocalAttribute localAttribute : methodContext.getLocalAttributes().values()) {
      if(!localAttribute.wasHandled()) {
        if(!body.hasLocalVariable(localAttribute.getName())) {
          body.addLocalVariable(localAttribute.getLocalVariable());
        }
        localAttribute.markAsHandled();
      }
    }
    return body;
  }

  protected void postProcessResult(final AspectContext context,
                                   final HxMethod interceptor,
                                   final HxMethod original,
                                   final HxMethod shadow,
                                   final HxInstruction anchor) {

    getResultHandler().process(this, context, interceptor, shadow, shadow, anchor);
  }

  protected void injectParameters(final AspectContext ctx,
                                  final HxMethod interceptor,
                                  final HxMethod original,
                                  final HxMethod shadow,
                                  final HxInstruction anchor) {

    final AspectAdviceParameterInjector parameterInjector = getParameterInjector();

    for (HxParameter parameter : interceptor.getParameters()) {
      if (!parameterInjector.injectParameter(
        this,
        ctx,
        interceptor,
        original,
        shadow,
        parameter,
        anchor)) {

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
