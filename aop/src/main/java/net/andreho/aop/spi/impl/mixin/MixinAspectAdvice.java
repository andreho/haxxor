package net.andreho.aop.spi.impl.mixin;

import net.andreho.aop.api.mixin.Mixin;
import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.impl.advices.AbstractAspectAdvice;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionSort;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.FieldInstruction;
import net.andreho.haxxor.cgen.instr.abstr.InvokeInstruction;
import net.andreho.haxxor.cgen.instr.constants.LDC;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * <br/>Created by a.hofmann on 05.07.2017 at 18:06.
 */
public class MixinAspectAdvice
  extends AbstractAspectAdvice<HxType> {

  private final HxType[] mixinTypes;

  private static boolean selectMixinMethods(HxMethod hxMethod) {
    return
      !hxMethod.getDeclaringType().hasName("java.lang.Object") &&
      !hxMethod.isAnnotationPresent(Mixin.Ignore.class) &&
      !hxMethod.isClassInitializer() &&
      !hxMethod.isConstructor() &&
      !hxMethod.isNative() &&
      hxMethod.hasBody();
  }

  public MixinAspectAdvice(final int index,
                           final String profileName,
                           final AspectAdviceType type,
                           final ElementMatcher<HxType> elementMatcher,
                           final HxType[] mixinTypes) {
    super(type, elementMatcher, profileName); //index,
    this.mixinTypes = mixinTypes;
  }

  public HxType[] getMixinTypes() {
    return mixinTypes.clone();
  }

  @Override
  public List<HxMethod> getInterceptors() {
    return Collections.emptyList();
  }

  @Override
  public boolean apply(final AspectContext context,
                       final HxType type) {
    if (!matches(type) && !type.isInterface()) {
      return false;
    }

    boolean result = false;
    for (HxType mixin : this.mixinTypes) {
      final HxType resolvedMixin = type.getHaxxor().resolve(mixin.getName());
      if (apply(context, type, resolvedMixin)) {
        result = true;
      }
    }
    return result;
  }

  private boolean apply(final AspectContext context,
                        final HxType type,
                        final HxType mixin) {

    if (!mixin.isAnnotationPresent(Mixin.class)) {
      return false;
    }

    copyMixinFields(type, mixin);
    copyMixinMethods(type, mixin);
    mergeMixinConstructors(type, mixin);
    mergeClassInitializers(type, mixin);
    addMixinInterface(type, mixin);

    return true;
  }

  private void addMixinInterface(final HxType type,
                                 final HxType mixin) {
    for (HxType mixinInterface : mixin.getInterfaces()) {
      if (!mixinInterface.isAssignableFrom(type)) {
        type.addInterface(mixinInterface);
      }
    }
  }

  private void mergeClassInitializers(final HxType type,
                                      final HxType mixin) {

    final Hx haxxor = type.getHaxxor();
    final Optional<HxMethod> mixinClinitOptional = mixin.findClassInitializer();

    if (mixinClinitOptional.isPresent()) {
      final HxMethod mixinClinit = mixinClinitOptional.get();
      final HxMethod clonedMixinClinit = mixinClinit.clone(HxMethod.CloneableParts.ALL_PARTS);

      adaptMethodBodyForCurrentType(type, mixin, clonedMixinClinit);
      adaptLocalVariables(type, mixin, clonedMixinClinit);
      adaptMethodSignature(type, mixin, clonedMixinClinit);

      Optional<HxMethod> typeClinit = type.findClassInitializer();
      HxMethod clinit;

      if (!typeClinit.isPresent()) {
        clinit = haxxor.createMethod("void", "<clinit>");
        type.addMethod(clinit);
      } else {
        clinit = typeClinit.get();
      }

      Optional<HxInstruction> returnInstructionOptional =
        clinit.getBody().getLast().findFirstWithType(HxInstructionTypes.Exit.RETURN);

      if (returnInstructionOptional.isPresent()) {
        final HxInstruction instruction = returnInstructionOptional.get();
        final HxExtendedCodeStream stream = instruction.asAnchoredStream();

        for (HxInstruction inst : clonedMixinClinit.getBody()) {
          inst.visit(stream);
        }
      } else {
        clonedMixinClinit.getBody().moveTo(clinit);
      }
    }
  }

  private void mergeMixinConstructors(final HxType type,
                                      final HxType mixin) {
    final Collection<HxMethod> typeForwardingConstructors = type.findForwardingConstructors();

    for (HxMethod constructor : typeForwardingConstructors) {
      final Set<String> binds = new LinkedHashSet<>();
      for (HxParameter parameter : constructor.getParameters()) {
        Optional<HxAnnotation> annotationOptional = parameter.getAnnotation(Mixin.Parameter.class);
        if (annotationOptional.isPresent()) {
          binds.add(annotationOptional.get().getAttribute("value"));
        }
      }

      if (binds.isEmpty()) {
        Optional<HxMethod> defaultMixinConstructor = mixin.findDefaultConstructor();
        if (defaultMixinConstructor.isPresent()) {
          mergeMixinConstructors(constructor, defaultMixinConstructor.get());
        } else {
          complainAboutMissingDefaultConstructor(type, mixin);
        }
      } else {

      }

      for (HxMethod mixinConstructor : mixin.findForwardingConstructors()) {

      }
    }
    throw new UnsupportedOperationException("TODO: implement it");
  }

  private void mergeMixinConstructors(final HxMethod typeConstructor, final HxMethod mixinConstructor) {
    HxInstruction typeSuperCall = findSuperConstructorCall(typeConstructor).get();
    HxInstruction mixinSuperCall = findSuperConstructorCall(mixinConstructor).get();
  }

  private void complainAboutMissingDefaultConstructor(final HxType type,
                                                      final HxType mixin) {
    throw new IllegalStateException("Mixin doesn't have a default constructor. " +
                                    "Please provide a default constructor or " +
                                    "use @Mixin.Parameter annotations on both sides " +
                                    "to link corresponding parameters of: " + mixin.getName() +
                                    " -> to: " + type.getName());
  }

  private Optional<HxInstruction> findSuperConstructorCall(final HxMethod constructor) {
    return constructor.getBody().getFirst().findFirst(instr -> {
      if (instr.hasType(HxInstructionTypes.Invocation.INVOKESPECIAL)) {
        InvokeInstruction invokeInstruction = (InvokeInstruction) instr;
        if ("<init>".equals(invokeInstruction.getName()) &&
            constructor.getDeclaringType().hasSupertype(invokeInstruction.getOwner())) {
          return true;
        }
      }
      return false;
    });
  }

  private void copyMixinMethods(final HxType type,
                                final HxType mixin) {
    for (HxMethod method : mixin.methods(MixinAspectAdvice::selectMixinMethods, true)) {

      if (!method.getDeclaringType().isAssignableFrom(type)) {
        if (!type.hasMethod(method)) {
          final HxMethod clonedMethod = method.clone(-1);
          adaptMethodBodyForCurrentType(type, mixin, clonedMethod);
          adaptLocalVariables(type, mixin, clonedMethod);
          adaptMethodSignature(type, mixin, clonedMethod);
          type.addMethod(clonedMethod);
        } else if(method.isAnnotationPresent(Mixin.Forced.class)) {
          throw new UnsupportedOperationException();
        }
      }
    }
  }

  private void adaptMethodSignature(final HxType type,
                                    final HxType mixin,
                                    final HxMethod clonedMethod) {
  }

  private void adaptLocalVariables(final HxType type,
                                   final HxType mixin,
                                   final HxMethod clonedMethod) {
    String typeDescriptor = type.toDescriptor();
    String mixinDescriptor = mixin.toDescriptor();

    clonedMethod.getBody().getLocalVariables().forEach(var -> {
      if ("this".equals(var.getName())) {
        var.setDescriptor(type.toDescriptor());
      }
      if (var.getSignature() != null &&
          var.getSignature().contains(mixinDescriptor)) {
        var.setSignature(var.getSignature().replaceAll(mixinDescriptor, typeDescriptor));
      }
    });
  }

  private void adaptMethodBodyForCurrentType(final HxType type,
                                             final HxType mixin,
                                             final HxMethod clonedMethod) {
    clonedMethod.getBody().forEach(inst -> {
      if (inst.hasSort(HxInstructionSort.Invocation) && inst instanceof InvokeInstruction) {
        final InvokeInstruction invokeInstruction = (InvokeInstruction) inst;
        if (mixin.hasNameViaExtends(invokeInstruction.getOwner())) {
          invokeInstruction.replaceWith(
            invokeInstruction.clone(type.toInternalName(),
                                    invokeInstruction.getName(),
                                    invokeInstruction.getDescriptor(),
                                    invokeInstruction.isInterface()));
        }
      } else if (inst.hasSort(HxInstructionSort.Field)) {
        final FieldInstruction fieldInstruction = (FieldInstruction) inst;

        if (mixin.hasNameViaExtends(fieldInstruction.getOwner())) {
          fieldInstruction.replaceWith(
            fieldInstruction.clone(type.toInternalName(),
                                   fieldInstruction.getName(),
                                   fieldInstruction.getDescriptor()));
        }
      } else if (inst.hasType(HxInstructionTypes.Constants.LDC)) {
        LDC<?> ldc = (LDC<?>) inst;
        if (ldc.getType() == LDC.ConstantType.TYPE && mixin.hasName(ldc.getValue().toString())) {
          ldc.replaceWith(LDC.ofType(type.toDescriptor()));
        }
      } else if (inst.hasType(HxInstructionTypes.Special.LINE_NUMBER)) {
        inst.remove();
      }
    });
  }

  private void copyMixinFields(final HxType type,
                               final HxType mixin) {
    for (HxField field : mixin.fields(hxField -> !hxField.getDeclaringMember().isInterface() &&
                                                 !hxField.isAnnotationPresent(Mixin.Ignore.class), true)) {
      if (!type.hasField(field.getName())) {
        type.addField(field.clone());
      }
    }
  }
}
