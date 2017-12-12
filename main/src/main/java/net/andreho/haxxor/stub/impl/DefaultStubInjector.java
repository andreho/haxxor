package net.andreho.haxxor.stub.impl;

import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxMethodBody;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionProperties;
import net.andreho.haxxor.cgen.HxInstructionSort;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.FieldInstruction;
import net.andreho.haxxor.cgen.instr.abstr.InvokeInstruction;
import net.andreho.haxxor.cgen.instr.abstr.LocalAccessInstruction;
import net.andreho.haxxor.cgen.instr.constants.LDC;
import net.andreho.haxxor.cgen.instr.constants.ldc.TypeLDC;
import net.andreho.haxxor.cgen.instr.conversion.CHECKCAST;
import net.andreho.haxxor.cgen.instr.conversion.INSTANCEOF;
import net.andreho.haxxor.cgen.instr.invokes.INVOKESPECIAL;
import net.andreho.haxxor.spi.HxStubInjector;
import net.andreho.haxxor.stub.Stub;
import net.andreho.haxxor.stub.errors.HxStubException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static net.andreho.haxxor.stub.impl.CheckingStubInjector.getPrefixedName;

/**
 * <br/>Created by a.hofmann on 10.12.2017 at 14:20.
 */
public class DefaultStubInjector
  implements HxStubInjector,
             HxInstructionProperties {

  @Override
  public boolean injectStub(final HxType target,
                            final HxType stub)
  throws HxStubException {
    injectMissingInterfaces(target, stub);
    injectMissingFields(target, stub);
    injectMissingMethods(target, stub);
    return true;
  }

  private void injectMissingInterfaces(final HxType target,
                                       final HxType stub) {
    for (HxType itf : stub.getInterfaces()) {
      if (!itf.isAssignableFrom(target)) {
        target.addInterface(itf);
      }
    }
  }

  private void injectMissingFields(final HxType target,
                                   final HxType stub) {
    for (HxField field : stub.getFields()) {
      if (field.isAnnotationPresent(Stub.Ignore.class) ||
          field.isAnnotationPresent(Stub.Field.class)) {
        continue;
      }
      if (!target.hasField(field.getName(), field.getType())) {
        target.addField(field.clone());
      }
    }
  }

  private void injectMissingMethods(final HxType target,
                                    final HxType stub)
  throws HxStubException {
    final List<HxMethod> overriddenList = new ArrayList<>();

    for (HxMethod method : stub.getMethods()) {
      if (method.isConstructor() ||
          method.isClassInitializer() ||
          method.isAnnotationPresent(Stub.Class.class) ||
          method.isAnnotationPresent(Stub.Ignore.class)) {
        continue;
      }
      if (!method.isConstructor() &&
          method.isAnnotationPresent(Stub.Method.class)) {
        HxMethod overridden = overrideMethodIfNeeded(target, stub, method);
        if (overridden != null) {
          overriddenList.add(overridden);
        }
      }
    }

    for (HxMethod method : stub.getMethods()) {
      if (method.isAnnotationPresent(Stub.Ignore.class) ||
          method.isAnnotationPresent(Stub.Method.class) ||
          method.isAnnotationPresent(Stub.Class.class) ||
          method.isAnnotationPresent(Stub.Override.class)) {
        continue;
      }
      if (method.isClassInitializer()) {
        mergeClassInitializers(target, stub, method);
      } else if (!method.isConstructor()) {
        if (target.hasMethod(method)) {
          throw new HxStubException("Given method overrides a method in the target '" + target +
                                    "' and must be annotated with @Stub.Override: " + method);
        }

        HxMethod methodClone = method.clone();
        target.addMethod(methodClone);
        adaptMethodCodeToTarget(target, stub, methodClone);
      }
    }

    for (HxMethod overridden : overriddenList) {
      adaptMethodCodeToTarget(target, stub, overridden);
    }

    for (HxMethod constructor : target.getConstructors()) {
      mergeClassConstructors(target, stub, constructor);
    }
  }

  private void mergeClassConstructors(final HxType target,
                                      final HxType stub,
                                      final HxMethod constructor)
  throws HxStubException {

    final HxMethod best = findBestSuitableStubConstructor(stub, constructor);
    if (best != null) {
      final Optional<HxInstruction> injectableCodeOpt =
        best.getBody().getFirst().findFirst((inst ->
          inst.hasType(HxInstructionTypes.Invocation.INVOKESPECIAL) &&
          inst.hasPropertyEqual(Invocation.NAME, "<init>") &&
          target.hasNameViaExtends(inst.property(Invocation.OWNER))));

      if (!injectableCodeOpt.isPresent()) {
        throw new HxStubException("Template constructor has invalid code: " + best);
      }

      final Optional<HxInstruction> afterSuperConstructorOpt =
        constructor.getBody().getFirst().findFirst((inst ->
          inst.hasType(HxInstructionTypes.Invocation.INVOKESPECIAL) &&
          inst.hasPropertyEqual(Invocation.NAME, "<init>") &&
          target.hasNameViaExtends(inst.property(Invocation.OWNER))));

      final HxInstruction injectableStart = injectableCodeOpt.get().getNext();
      final int stubSlots = best.getParametersSlotSize();
      final int targetSlots = constructor.getParametersSlotSize();
      final int localsShift = targetSlots - stubSlots;

      if (afterSuperConstructorOpt.isPresent()) {
        final HxInstruction point = afterSuperConstructorOpt.get();
        final HxExtendedCodeStream stream = point.asStream();

        for (HxInstruction inst : injectableStart) {
          if (inst.isPseudoInstruction() ||
              inst.hasType(HxInstructionTypes.Special.LINE_NUMBER)) {
            continue;
          } else if (inst.hasType(HxInstructionTypes.Exit.RETURN) &&
                     inst.isFollowedTillEndBy(
                       i -> i.isPseudoInstruction() ||
                            i.hasType(HxInstructionTypes.Special.LABEL))) {
            break;
          }
          HxInstruction replacement = inst;
          if (inst.hasSort(HxInstructionSort.Store) ||
              inst.hasSort(HxInstructionSort.Load)) {
            final LocalAccessInstruction accessInstruction = (LocalAccessInstruction) inst;
            final int slotIndex = accessInstruction.getLocalIndex();
            if (slotIndex > 0) {
              if (slotIndex < stubSlots) {
                Optional<HxParameter> parameterOpt = best.getParameterWithSlot(slotIndex);
                if (!parameterOpt.isPresent()) {
                  throw new HxStubException("Template constructor has invalid code: " + best);
                }
                HxParameter parameterToLink = parameterOpt.get();
                HxParameter givenParameter = findParameter(constructor, parameterToLink);
                if (givenParameter == null) {
                  throw new HxStubException(
                    "Unable to link the parameter under the index " + parameterToLink.getIndex() + " of: " + best);
                }
//                HxInstruction adjusted =
                replacement = accessInstruction.clone(givenParameter.getSlotIndex());
//                HxExtendedCodeStream convertStream = inst.hasSort(HxInstructionSort.Store)?
//                                                      adjusted.asAnchoredStream() :
//                                                     adjusted.asStream();
//                convertStream.CONVERT(parameterToLink.getType(), givenParameter.getType());
              } else {
                replacement = accessInstruction.clone(slotIndex + localsShift);
              }
            }
          } else {
            replacement = calculateReplacement(target, stub, best, inst);
          }
          replacement.visit(stream);
        }
      }
    }
  }

  private HxParameter findParameter(final HxMethod method,
                                    final HxParameter given) {
    final List<HxParameter> parameters = method.findParametersWith(
      parameter -> Objects.equals(getParameterName(parameter), getParameterName(given)) &&
                   given.getType().distanceTo(parameter.getType()) > -1);
    if (parameters.isEmpty()) {
      return null;
    } else if (parameters.size() > 1) {
      throw new IllegalStateException(
        "Given constructor of the target class defines ambiguous parameter-names: " + method);
    }
    return parameters.get(0);
  }

  private HxMethod findBestSuitableStubConstructor(final HxType stub,
                                                   final HxMethod constructor)
  throws HxStubException {
    HxMethod best = null;
    int bestMatch = -1;
    loop:
    for (HxMethod stubConstructor : stub.getConstructors()) {
      if (!stubConstructor.isAnnotationPresent(Stub.Ignore.class) &&
          !stubConstructor.hasAllParametersWithAnnotation(Stub.Named.class)) {
        continue;
      }

      int found = 0;
      for (HxParameter initParam : stubConstructor.getParameters()) {
        if (!initParam.isAnnotationPresent(Stub.Named.class)) {
          throw new HxStubException(
            "Each declared parameter of the template-constructor must be annotated with @Stub.Named: " +
            stubConstructor);
        }
        final String parameterName = getParameterName(initParam);
        List<HxParameter> list = constructor.findParametersWith(
          param -> Objects.equals(parameterName, getParameterName(param)));
        if (list.isEmpty()) {
          continue loop;
        } else if (list.size() > 1) {
          throw new HxStubException(
            "Given constructor of the target class defines ambiguous parameter-names: " + constructor);
        }
        HxParameter matchingParameter = list.get(0);
        if (initParam.getType().isAssignableFrom(matchingParameter.getType())) {
          found++;
        }
      }

      if (found == stubConstructor.getParametersCount()) {
        if (bestMatch == -1 || bestMatch < found) {
          bestMatch = found;
          best = stubConstructor;
        }
      }
    }
    return best;
  }

  private String getParameterName(final HxParameter parameter) {
    return parameter
      .getAnnotation(Stub.Named.class)
      .map(anno ->
             anno.getAttribute("value", parameter.isNamePresent() ? parameter.getName() : null))
      .orElse(null);
  }

  private void mergeClassInitializers(final HxType target,
                                      final HxType stub,
                                      final HxMethod method)
  throws HxStubException {
    Optional<HxMethod> clinitOpt = target.findOrCreateClassInitializer();
    if (clinitOpt.isPresent()) {
      HxMethod clinit = clinitOpt.get();
      HxExtendedCodeStream stream = clinit.getBody().getFirst().asStream();
      for (HxInstruction inst : method.getBody().getFirst()) {
        if (inst.isPseudoInstruction() ||
            inst.hasType(HxInstructionTypes.Special.LINE_NUMBER)) {
          continue;
        }
        if (inst.hasType(HxInstructionTypes.Exit.RETURN) &&
            inst.isFollowedTillEndBy(i -> i.isPseudoInstruction() ||
                                          i.hasType(HxInstructionTypes.Special.LABEL))) {
          break;
        }
        calculateReplacement(target, stub, method, inst).visit(stream);
      }
    }
  }

  private HxMethod overrideMethodIfNeeded(final HxType target,
                                          final HxType stub,
                                          final HxMethod ref)
  throws HxStubException {
    String name = getPrefixedName(ref.getName(), Stub.Method.class, ref);
    if (name.equals(ref.getName())) {
      return null;
    }
    Optional<HxMethod> overriddenOpt = stub.findMethod(ref.getReturnType(), name, ref.getParameterTypes());
    if (!overriddenOpt.isPresent()) {
      return null;
    }

    final HxMethod overridden = overriddenOpt.get();
    if (!overridden.isAnnotationPresent(Stub.Override.class)) {
      throw new HxStubException("Missing @Stub.Override on: " + overridden);
    }
    if (!overridden.hasBody()) {
      throw new HxStubException("Method with @Stub.Override must defined a method-body: " + overridden);
    }
    Optional<HxMethod> originalOpt = target.findMethod(overridden);
    if (!originalOpt.isPresent()) {
      throw new HxStubException("Method to override wasn't found: " + overriddenOpt + " in current target: " + target);
    }
    HxMethod original = originalOpt.get();
    if (original.isStatic() != ref.isStatic() &&
        ref.isStatic() != overridden.isStatic()) {
      if (ref.isStatic() != overridden.isStatic()) {
        throw new HxStubException("Modifier mismatch between: " + ref + " and its counterpart: " + overridden);
      }
      throw new HxStubException(
        "Modifier mismatch between: " + overriddenOpt + " and its counterpart: " + original);
    }
    if (!target.hasMethod(ref)) {
      HxMethod originalClone = original.clone(ref.getName());
      target.removeMethod(original)
            .addMethod(originalClone.makePrivate());
    }
    HxMethod overriddenClone = overridden.clone();
    target.addMethod(overriddenClone);
    return overriddenClone;
  }

  private void adaptMethodCodeToTarget(final HxType target,
                                       final HxType stub,
                                       final HxMethod method)
  throws HxStubException {
    final HxMethodBody body = method.getBody();
    for (HxInstruction inst : body) {
      if (inst.isPseudoInstruction() ||
          inst.hasType(HxInstructionTypes.Special.LINE_NUMBER)) {
        continue;
      }
      adjustInstruction(target, stub, method, inst);
    }
  }

  private void adjustInstruction(final HxType target,
                                 final HxType stub,
                                 final HxMethod method,
                                 final HxInstruction instruction)
  throws HxStubException {
    HxInstruction replacement = calculateReplacement(target, stub, method, instruction);
    if (replacement != instruction) {
      instruction.replaceWith(replacement);
    }
  }

  private HxInstruction calculateReplacement(final HxType target,
                                             final HxType stub,
                                             final HxMethod method,
                                             final HxInstruction instruction)
  throws HxStubException {
    if (instruction.hasSort(HxInstructionSort.Fields)) {
      return adaptFieldInstruction(target, stub, method, instruction);
    } else if (instruction.hasSort(HxInstructionSort.Invocation)) {
      return adaptInvocationInstruction(target, stub, method, instruction);
    } else if (instruction.hasSort(HxInstructionSort.Conversion)) {
      if (instruction.hasType(HxInstructionTypes.Conversion.INSTANCEOF)) {
        return adaptInstanceOfInstruction(target, stub, method, instruction);
      } else if (instruction.hasType(HxInstructionTypes.Conversion.CHECKCAST)) {
        return adaptCastInstruction(target, stub, method, instruction);
      }
    } else if (instruction.hasType(HxInstructionTypes.Constants.LDC)) {
      return adaptClassConstantInstruction(target, stub, method, instruction);
    }
    return instruction;
  }

  private HxInstruction adaptClassConstantInstruction(final HxType target,
                                                      final HxType stub,
                                                      final HxMethod method,
                                                      final HxInstruction inst) {
    LDC<?> ldc = (LDC<?>) inst;
    if (ldc.getType() == LDC.ConstantType.TYPE) {
      TypeLDC typeLDC = (TypeLDC) ldc;
      if (stub.hasName(typeLDC.getValue())) {
        return LDC.ofType(target.toDescriptor());
      }
    }
    return inst;
  }

  private HxInstruction adaptFieldInstruction(final HxType target,
                                              final HxType stub,
                                              final HxMethod method,
                                              final HxInstruction inst)
  throws HxStubException {
    final FieldInstruction fieldInstruction = (FieldInstruction) inst;
    if (!stub.hasName(fieldInstruction.getOwner())) {
      return inst;
    }

    final Optional<HxField> stubFieldOptional =
      stub.findField(fieldInstruction.getName(), fieldInstruction.getDescriptor());
    if (!stubFieldOptional.isPresent()) {
      throw new HxStubException(
        "Stub field declared in the code wasn't found in the stub itself: [" + fieldInstruction + "]");
    }

    final HxField stubField = stubFieldOptional.get();
    if (stubField.isAnnotationPresent(Stub.Ignore.class)) {
      throw new HxStubException(
        "Ignored field '" + stubField + "' is used in the method that is going to be " +
        "injected into the target class: " + method);

    } else if (stubField.isAnnotationPresent(Stub.Field.class)) {
      final Optional<HxField> fieldReferenceOptional =
        target.findFieldRecursively(stubField.getName(),
                                    stubField.getType().getName());

      if (!fieldReferenceOptional.isPresent()) {
        throw new HxStubException(
          "Missing field reference '" + stubField + "' is used in the method that is going to be " +
          "injected into the target class: " + method);
      }

      final HxField fieldReference = fieldReferenceOptional.get();
      if (!fieldReference.isAccessibleFrom(target)) {
        throw new HxStubException(
          "Resolved field reference '" + fieldReference + "' can't be accessed from current method: " + method);
      }

      return fieldInstruction.clone(
        fieldReference.getDeclaringType().toInternalName(),
        fieldReference.getName(),
        fieldReference.getType().toDescriptor());
    } else if (stubField.isDeclaredBy(stub)) {
      return fieldInstruction.clone(
        target.toInternalName(),
        stubField.getName(),
        stubField.getType().toDescriptor());
    } else {
      //skip
    }
    return inst;
  }

  private HxInstruction adaptInvocationInstruction(final HxType target,
                                                   final HxType stub,
                                                   final HxMethod method,
                                                   final HxInstruction inst)
  throws HxStubException {
    final InvokeInstruction invokeInstruction = (InvokeInstruction) inst;
    if (!stub.hasName(invokeInstruction.getOwner())) {
      return inst;
    }

    final Optional<HxMethod> stubMethodOptional =
      stub.findMethodDirectly(invokeInstruction.getName(), invokeInstruction.getDescriptor());

    if (!stubMethodOptional.isPresent()) {
      throw new HxStubException(
        "Stub method declared in the code wasn't found in the stub itself: [" + invokeInstruction + "]");
    }

    final HxMethod stubMethod = stubMethodOptional.get();
    if (stubMethod.isAnnotationPresent(Stub.Ignore.class)) {
      throw new HxStubException(
        "Ignored method '" + stubMethod + "' is used in the method that is going to be " +
        "injected into the target class: " + method);
    } else if (stubMethod.isAnnotationPresent(Stub.Method.class)) {
      boolean overriddenCall = false;
      Optional<HxMethod> targetMethodOptional = Optional.empty();

      if (!stubMethod.hasName(getPrefixedName(stubMethod.getName(), Stub.Method.class, stubMethod))) {
        Optional<HxMethod> overriddenOpt =
          target.findMethodRecursively(stubMethod.getReturnType(), stubMethod.getName(),
                                       stubMethod.getParameterTypes());
        if (overriddenOpt.isPresent()) {
          targetMethodOptional = overriddenOpt;
          overriddenCall = true;
        }
      } else {
        targetMethodOptional =
          target.findMethodRecursively(stubMethod.getReturnType(), stubMethod.getName(),
                                       stubMethod.getParameterTypes());
      }

      if (!targetMethodOptional.isPresent()) {
        throw new HxStubException(
          "Missing method reference '" + stubMethod + "' is used in the method that is going to be " +
          "injected into the target class: " + method);
      }

      final HxMethod targetMethod = targetMethodOptional.get();
      if (!targetMethod.isAccessibleFrom(target)) {
        throw new HxStubException(
          "Resolved method reference '" + targetMethod + "' can't be accessed from current method: " + method);
      }

      if (targetMethod.isStatic() != stubMethod.isStatic() ||
          (!overriddenCall && !stubMethod.isStatic() && (targetMethod.isPrivate() != stubMethod.isPrivate()))) {
        throw new HxStubException(
          "Stub method '" + stubMethod + "' must have the same modifiers {private | static} " +
          "as the resolved method reference: " + targetMethod);
      }

      boolean declaredByInterface = targetMethod.getDeclaringType().isInterface();
      String owner = targetMethod.getDeclaringType().toInternalName();
      String methodName = targetMethod.getName();
      String methodDescriptor = targetMethod.toDescriptor();

      if (overriddenCall ||
          targetMethod.isPrivate() ||
          (!targetMethod.isDeclaredBy(target) && !targetMethod.getDeclaringType().isInterface())) {
        //Needs INVOKESPECIAL invocation
        return new INVOKESPECIAL(owner, methodName, methodDescriptor);
      } else {
        return invokeInstruction.clone(
          owner,
          methodName,
          methodDescriptor,
          declaredByInterface);
      }
    } else if (stubMethod.isAnnotationPresent(Stub.Override.class)) {
      //TODO
    } else if (stubMethod.isAnnotationPresent(Stub.Class.class)) {
      return LDC.ofType(target.toDescriptor());
    } else if (stubMethod.isDeclaredBy(stub)) {
      return invokeInstruction.clone(
        target.toInternalName(),
        stubMethod.getName(),
        stubMethod.toDescriptor(),
        stubMethod.getDeclaringType().isInterface());
    } else {
      //skip
    }
    return inst;
  }

  private HxInstruction adaptInstanceOfInstruction(final HxType target,
                                                   final HxType stub,
                                                   final HxMethod method,
                                                   final HxInstruction inst) {
    final INSTANCEOF instanceOf = (INSTANCEOF) inst;
    if (stub.hasName(instanceOf.getOperand())) {
      return new INSTANCEOF(target.toInternalName());
    }
    return inst;
  }

  private HxInstruction adaptCastInstruction(final HxType target,
                                             final HxType stub,
                                             final HxMethod method,
                                             final HxInstruction inst) {
    final CHECKCAST checkcast = (CHECKCAST) inst;
    if (stub.hasName(checkcast.getOperand())) {
      return new CHECKCAST(target.toInternalName());
    }
    return inst;
  }
}
