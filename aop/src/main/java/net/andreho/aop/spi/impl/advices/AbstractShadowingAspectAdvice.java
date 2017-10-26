package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.api.Placeholder;
import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectLocalAttribute;
import net.andreho.aop.spi.AspectMethodContext;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.ParameterInjector;
import net.andreho.aop.spi.ParameterInjectorSelector;
import net.andreho.aop.spi.ResultPostProcessor;
import net.andreho.aop.spi.impl.Constants;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxMethodBody;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxSort;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.HxLocalVariable;
import net.andreho.haxxor.cgen.instr.misc.LABEL;
import net.andreho.haxxor.cgen.instr.misc.LINE_NUMBER;
import net.andreho.haxxor.cgen.instr.misc.NAMED_LABEL;

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
  private final List<HxMethod> interceptors;
  private final ResultPostProcessor resultHandler;
  private final ParameterInjectorSelector parameterInjectorSelector;

  public AbstractShadowingAspectAdvice(final AspectAdviceType type,
                                       final ElementMatcher<T> elementMatcher,
                                       final String profileName,
                                       final List<HxMethod> interceptors,
                                       final ParameterInjectorSelector parameterInjectorSelector,
                                       final ResultPostProcessor resultHandler) {
    super(type,
          elementMatcher,
          profileName);

    this.interceptors = requireNonNull(interceptors);
    this.parameterInjectorSelector = requireNonNull(parameterInjectorSelector);
    this.resultHandler = requireNonNull(resultHandler);
  }

  @Override
  public List<HxMethod> getInterceptors() {
    return interceptors;
  }

  public ParameterInjectorSelector getParameterInjectorSelector() {
    return parameterInjectorSelector;
  }

  public ResultPostProcessor getResultHandler() {
    return resultHandler;
  }

  protected HxMethod findOrCreateShadowMethod(AspectContext context,
                                              HxMethod original,
                                              String shadowName) {

    final Hx haxxor = original.getHaxxor();
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
      //For more examples: InvokerBytecodeGenerator.generateCustomizedCodeBytes()
      //IS IT A HACK OR NOT?
      .addAnnotation(haxxor.createAnnotation("java.lang.invoke.LambdaForm$Compiled", true))
      .addAnnotation(haxxor.createAnnotation("java.lang.invoke.LambdaForm$Hidden", true))
      .addAnnotation(haxxor.createAnnotation("java.lang.invoke.ForceInline", true));

    shadowMethod.getParameters().forEach(parameter -> parameter.addModifier(HxParameter.Modifiers.SYNTHETIC));
    original.getBody().moveTo(shadowMethod);

    final boolean isStatic = original.isStatic();
    final HxMethodBody body = original.getBody();
    final HxInstruction first = body.getFirst();

    final LABEL startLabel = new NAMED_LABEL("START");
    final LABEL delegationStart = new NAMED_LABEL("D_START");
    final LABEL delegationEnd = new NAMED_LABEL("D_END");
    final LABEL returnLabel = new NAMED_LABEL("RET");
    final LABEL endLabel = new NAMED_LABEL("END");

    first.append(startLabel);

    final HxExtendedCodeStream stream =
      appendLineNumberIfAvailable(shadowMethod, startLabel)
        .append(delegationStart)
        .asStream();

    final int resultSlot = createSlotForResult(context, original, delegationStart);

    if (isStatic) {
      shadowMethod.addModifier(HxMethod.Modifiers.STATIC);
      stream
        .GENERIC_LOAD(shadowMethod.getParameterTypes(), 0);
    } else {
      stream
        .THIS()
        .GENERIC_LOAD(shadowMethod.getParameterTypes(), 1);
    }

    if (constructor) {
      HxCgenUtils.shiftAccessToLocalVariable(
        shadowMethod.getBody().getFirst(),
        original.getParametersSlotSize() + 1,
        1);

      shadowMethod.addParameter(
        haxxor.createParameter(haxxor.reference(PLACEHOLDER_CLASSNAME))
              .setModifiers(HxParameter.Modifiers.SYNTHETIC));
    }

    //DON'T MOVE THIS METHOD CALL ANYWHERE ELSE
    type.addMethod(shadowMethod);

    if (isStatic) {
      stream.INVOKESTATIC(shadowMethod);
    } else {
      if (constructor) {
        stream.ACONST_NULL(); //for the last "net.andreho.aop.api.Placeholder" parameter
      }
      stream.INVOKESPECIAL(shadowMethod);
    }

    stream
      .GENERIC_STORE(shadowMethod.getReturnType(), resultSlot)
      .LABEL(delegationEnd)
      .GOTO(returnLabel)
      .LABEL(returnLabel)
      .GENERIC_LOAD(shadowMethod.getReturnType(), resultSlot)
      .GENERIC_RETURN(shadowMethod.getReturnType())
      .LABEL(endLabel);

    copyLocalVariables(shadowMethod, body, startLabel, endLabel);

    initializeMethodContext(context,
                            original,
                            shadowMethod,
                            startLabel,
                            delegationStart,
                            delegationEnd,
                            returnLabel,
                            endLabel);

    return shadowMethod;
  }

  private int createSlotForResult(final AspectContext context,
                                  final HxMethod original,
                                  final HxInstruction anchor) {

    if (!original.hasReturnType(HxSort.VOID)) {
      AspectLocalAttribute resultAttribute;
      final AspectMethodContext methodContext = context.getAspectMethodContext();

      if (!methodContext.hasLocalAttribute(Constants.RESULT_ATTRIBUTES_NAME)) {
        final int slotOffset = methodContext.getNextSlotIndex();
        final HxType returnType = original.getReturnType();

        resultAttribute =
          methodContext.createLocalAttribute(returnType, Constants.RESULT_ATTRIBUTES_NAME, slotOffset);
        final int variableSize = returnType.getSlotSize();

        HxCgenUtils.shiftAccessToLocalVariable(original.getBody().getFirst(), slotOffset, variableSize);
      } else {
        resultAttribute = methodContext.getLocalAttribute(Constants.RESULT_ATTRIBUTES_NAME);
      }

      anchor.asAnchoredStream()
            .GENERIC_DEFAULT_VALUE(resultAttribute.getType())
            .GENERIC_STORE(resultAttribute.getType(), resultAttribute.getIndex());

      return resultAttribute.getIndex();
    }
    return -1;
  }

  private void initializeMethodContext(final AspectContext context,
                                       final HxMethod original,
                                       final HxMethod shadowMethod,
                                       final LABEL startLabel,
                                       final LABEL delegationStart,
                                       final LABEL delegationEnd,
                                       final LABEL catchInjection,
                                       final LABEL endLabel) {
    final AspectMethodContext methodContext = context.getAspectMethodContext();

    methodContext.setOriginalMethod(original);
    methodContext.setShadowMethod(shadowMethod);

    methodContext.setBegin(startLabel);
    methodContext.setDelegationBegin(delegationStart);
    methodContext.setDelegationEnd(delegationEnd);
    methodContext.setCatchInjection(catchInjection);
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
        ins -> ins.hasType(HxInstructionTypes.Special.LINE_NUMBER)
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
    for (AspectLocalAttribute localAttribute : methodContext.getLocalAttributes().values()) {
      if (!localAttribute.wasHandled()) {
        if (!body.hasLocalVariable(localAttribute.getName())) {
          body.addLocalVariable(localAttribute.getHxLocalVariable());
        }
        localAttribute.markAsHandled();
      }
    }
    return body;
  }

  protected boolean postProcessResult(final AspectContext context,
                                      final HxMethod interceptor,
                                      final HxMethod original,
                                      final HxMethod shadow,
                                      final HxInstruction anchor) {

    return getResultHandler().handle(this, context, interceptor, shadow, shadow, anchor);
  }

  protected void injectParameters(final AspectContext ctx,
                                  final HxMethod interceptor,
                                  final HxMethod original,
                                  final HxMethod shadow,
                                  final HxInstruction anchor,
                                  final ParameterInjectorSelector.Result suitableInjectors) {
    final List<ParameterInjector> injectors = suitableInjectors.injectors;

    for (int i = 0; i < interceptor.getParameters().size(); i++) {
      final HxParameter parameter = interceptor.getParameters().get(i);
      final ParameterInjector injector = injectors.get(i);

      injector.inject(this, ctx, original, shadow, interceptor, parameter, anchor);
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
    return interceptors.equals(that.interceptors);
  }

  @Override
  public int hashCode() {
    return super.hashCode() * 31 +
           interceptors.hashCode();
  }
}
