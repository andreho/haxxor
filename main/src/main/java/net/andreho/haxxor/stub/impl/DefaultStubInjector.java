package net.andreho.haxxor.stub.impl;

import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxMethodBody;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionSort;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.FieldInstruction;
import net.andreho.haxxor.cgen.instr.abstr.InvokeInstruction;
import net.andreho.haxxor.cgen.instr.constants.LDC;
import net.andreho.haxxor.cgen.instr.constants.ldc.TypeLDC;
import net.andreho.haxxor.cgen.instr.conversion.CHECKCAST;
import net.andreho.haxxor.cgen.instr.conversion.INSTANCEOF;
import net.andreho.haxxor.spi.HxStubInjector;
import net.andreho.haxxor.stub.Stub;
import net.andreho.haxxor.stub.errors.HxStubException;

import java.util.Optional;

import static net.andreho.haxxor.stub.impl.CheckingStubInjector.getPrefixedName;

/**
 * <br/>Created by a.hofmann on 10.12.2017 at 14:20.
 */
public class DefaultStubInjector
  implements HxStubInjector {

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
    for (HxMethod method : stub.getMethods()) {
      if (method.isAbstract() ||
          method.isAnnotationPresent(Stub.Ignore.class) ||
          method.isAnnotationPresent(Stub.Method.class)) {
        continue;
      }
      if (!target.hasMethod(method.getReturnType(), method.getName(), method.getParameterTypes())) {
        if (!method.isConstructor() && !method.isClassInitializer()) {
          HxMethod methodClone = method.clone();
          target.addMethod(methodClone);
          adaptMethodCodeToTarget(target, stub, methodClone);
        }
      }
    }
  }

  private void adaptMethodCodeToTarget(final HxType target,
                                       final HxType stub,
                                       final HxMethod method)
  throws HxStubException {
    final HxMethodBody body = method.getBody();
    for (HxInstruction instruction : body) {
      adaptInstruction(target, stub, method, instruction);
    }
  }

  private void adaptInstruction(final HxType target,
                                final HxType stub,
                                final HxMethod method,
                                final HxInstruction instruction)
  throws HxStubException {
    if (instruction.hasSort(HxInstructionSort.Fields)) {
      adaptFieldInstruction(target, stub, method, instruction);
    } else if (instruction.hasSort(HxInstructionSort.Invocation)) {
      adaptInvocationInstruction(target, stub, method, instruction);
    } else if (instruction.hasSort(HxInstructionSort.Conversion)) {
      if (instruction.hasType(HxInstructionTypes.Conversion.INSTANCEOF)) {
        adaptInstanceOfInstruction(target, stub, method, instruction);
      } else if (instruction.hasType(HxInstructionTypes.Conversion.CHECKCAST)) {
        adaptCastInstruction(target, stub, method, instruction);
      }
    } else if (instruction.hasType(HxInstructionTypes.Constants.LDC)) {
      adaptClassConstantInstruction(target, stub, method, instruction);
    }
  }

  private void adaptClassConstantInstruction(final HxType target,
                                             final HxType stub,
                                             final HxMethod method,
                                             final HxInstruction inst) {
    LDC<?> ldc = (LDC<?>) inst;
    if (ldc.getType() == LDC.ConstantType.TYPE) {
      TypeLDC typeLDC = (TypeLDC) ldc;
      if (stub.hasName(typeLDC.getValue())) {
        typeLDC.replaceWith(LDC.ofType(target.toDescriptor()));
      }
    }
  }

  private void adaptFieldInstruction(final HxType target,
                                     final HxType stub,
                                     final HxMethod method,
                                     final HxInstruction inst)
  throws HxStubException {
    final FieldInstruction fieldInstruction = (FieldInstruction) inst;
    if (!stub.hasName(fieldInstruction.getOwner())) {
      return;
    }

    final Optional<HxField> stubFieldOptional =
      stub.findField(fieldInstruction.getName(), fieldInstruction.getDescriptor());
    if (!stubFieldOptional.isPresent()) {
      throw new HxStubException(
        "Stub field declared in the code wasn't found in the stub itself: ["+fieldInstruction+"]");
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

      inst.replaceWith(fieldInstruction.clone(
        fieldReference.getDeclaringType().toInternalName(),
        fieldReference.getName(),
        fieldReference.getType().toDescriptor())
      );
    } else if (stubField.isDeclaredBy(stub)) {
      inst.replaceWith(fieldInstruction.clone(
        target.toInternalName(),
        stubField.getName(),
        stubField.getType().toDescriptor())
      );
    } else {
      //skip
    }
  }

  private void adaptInvocationInstruction(final HxType target,
                                          final HxType stub,
                                          final HxMethod method,
                                          final HxInstruction inst)
  throws HxStubException {
    final InvokeInstruction invokeInstruction = (InvokeInstruction) inst;
    if (!stub.hasName(invokeInstruction.getOwner())) {
      return;
    }

    final Optional<HxMethod> stubMethodOptional =
      stub.findMethodDirectly(invokeInstruction.getName(), invokeInstruction.getDescriptor());

    if (!stubMethodOptional.isPresent()) {
      throw new HxStubException(
        "Stub method declared in the code wasn't found in the stub itself: ["+invokeInstruction+"]");
    }

    final HxMethod stubMethod = stubMethodOptional.get();
    if(stubMethod.isAnnotationPresent(Stub.Ignore.class)) {
      throw new HxStubException(
        "Ignored method '" + stubMethod + "' is used in the method that is going to be " +
        "injected into the target class: " + method);
    } else if(stubMethod.isAnnotationPresent(Stub.Method.class)) {
      String name = getPrefixedName(stubMethod.getName(), Stub.Method.class, stubMethod);
      final Optional<HxMethod> methodReferenceOptional =
        target.findMethodRecursively(stubMethod.getReturnType(), name, stubMethod.getParameterTypes());

      if (!methodReferenceOptional.isPresent()) {
        throw new HxStubException(
          "Missing method reference '" + stubMethod + "' is used in the method that is going to be " +
          "injected into the target class: " + method);
      }

      final HxMethod methodReference = methodReferenceOptional.get();
      if (!methodReference.isAccessibleFrom(target)) {
        throw new HxStubException(
          "Resolved method reference '" + methodReference + "' can't be accessed from current method: " + method);
      }

      if(methodReference.isStatic() != stubMethod.isStatic() ||
         (!stubMethod.isStatic() && (methodReference.isPrivate() == stubMethod.isPrivate()))) {
        throw new HxStubException(
          "Stub method '" + stubMethod + "' must have the same modifiers {private | static} " +
          "as the resolved method reference: " + methodReference);
      }

      inst.replaceWith(invokeInstruction.clone(
        methodReference.getDeclaringType().toInternalName(),
        methodReference.getName(),
        methodReference.toDescriptor(), methodReference.getDeclaringType().isInterface())
      );
    } else if(stubMethod.isAnnotationPresent(Stub.Override.class)) {
      //TODO
    } else if (stubMethod.isDeclaredBy(stub)) {
      inst.replaceWith(invokeInstruction.clone(
        target.toInternalName(),
        stubMethod.getName(),
        stubMethod.toDescriptor(),
        stubMethod.getDeclaringType().isInterface())
      );
    } else {
      //skip
    }
  }

  private void adaptInstanceOfInstruction(final HxType target,
                                          final HxType stub,
                                          final HxMethod method,
                                          final HxInstruction inst) {
    final INSTANCEOF instanceOf = (INSTANCEOF) inst;
    if (stub.hasName(instanceOf.getOperand())) {
      instanceOf.replaceWith(new INSTANCEOF(target.toInternalName()));
    }
  }

  private void adaptCastInstruction(final HxType target,
                                    final HxType stub,
                                    final HxMethod method,
                                    final HxInstruction inst) {
    final CHECKCAST checkcast = (CHECKCAST) inst;
    if (stub.hasName(checkcast.getOperand())) {
      checkcast.replaceWith(new CHECKCAST(target.toInternalName()));
    }
  }
}
