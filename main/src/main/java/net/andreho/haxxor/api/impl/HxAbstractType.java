package net.andreho.haxxor.api.impl;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxConstants;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxGenericType;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.api.HxTypeReference;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionSort;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInvokeInstruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * <br/>Created by andreho on 3/26/16 at 7:21 PM.<br/>
 */
public abstract class HxAbstractType
  extends HxAnnotatedImpl<HxType> implements HxType {

  private final Hx haxxor;
  private final String name;

  private static int estimateDistance(final HxType lhs,
                                      final HxType rhs,
                                      int distance,
                                      final int arrayCost,
                                      final int extendsCost,
                                      final int interfaceCost) {
    if (lhs == null || rhs == null) {
      return -1;
    } else if (lhs.equals(rhs)) {
      return distance;
    } else if (lhs.isPrimitive()) {
      return -1;
    } else if (lhs.isArray()) {
      if (lhs.getDimension() != rhs.getDimension()) {
        return -1;
      }

      return estimateDistance(
        lhs.getComponentType().get(),
        rhs.getComponentType().get(),
        distance + arrayCost,
        arrayCost,
        extendsCost,
        interfaceCost
      );
    }

    if (rhs.isInterface()) {
      for (HxType itf : lhs.getInterfaces()) {
        int dist = estimateDistance(
          itf,
          rhs,
          distance += interfaceCost,
          arrayCost,
          extendsCost,
          interfaceCost
        );

        if (dist > -1) {
          return dist;
        }
      }
    }
    if (lhs.hasSuperType()) {
      return estimateDistance(
        lhs.getSuperType().get(),
        rhs,
        distance + extendsCost,
        arrayCost,
        extendsCost,
        interfaceCost
      );
    }
    return -1;
  }

  public HxAbstractType(Hx haxxor,
                        String name) {
    this.haxxor = Objects.requireNonNull(haxxor, "Associated haxxor instance can't be null.");
    this.name = Objects.requireNonNull(name, "Given internal classname can't be null.");

    if (name.isEmpty()) {
      throw new IllegalArgumentException("Given internal classname can't be empty.");
    }
  }

  @Override
  public Hx getHaxxor() {
    return haxxor;
  }

  @Override
  public Version getVersion() {
    return Version.V1_8;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public HxType setVersion(Version version) {
    //NO OP
    return this;
  }

  @Override
  public Optional<HxType> getSuperType() {
    return Optional.empty();
  }

  @Override
  public HxType setSuperType(HxType superType) {
    //NO OP
    return this;
  }

  @Override
  public HxType setSuperType(String superType) {
    if (superType == null) {
      if ("java.lang.Object".equals(getName())) {
        setSuperType((HxType) null);
        return this;
      }
      throw new IllegalArgumentException("Super type can't be null.");
    }
    return setSuperType(getHaxxor().reference(superType));
  }

  @Override
  public List<HxType> getInterfaces() {
    return Collections.emptyList();
  }

  @Override
  public HxType initialize(Part... parts) {
    for (Part part : parts) {
      initialize(part);
    }
    return this;
  }

  @Override
  public HxType initialize(Part part) {
    return this;
  }

  @Override
  public HxType setInterfaces(List<HxType> interfaces) {
    //NO OP
    return this;
  }

  @Override
  public List<HxType> getInnerTypes() {
    return Collections.emptyList();
  }

  @Override
  public HxType setInnerTypes(List<HxType> declaredTypes) {
    //NO OP
    return this;
  }

  @Override
  public HxType addFieldAt(int index,
                           HxField field) {
    //NO OP
    return this;
  }

  @Override
  public HxType removeField(HxField field) {
    //NO OP
    return this;
  }

  @Override
  public Optional<HxField> findField(String name) {
    return Optional.empty();
  }

  @Override
  public Optional<HxField> findFieldDirectly(final String name,
                                             final String descriptor) {
    return Optional.empty();
  }

  @Override
  public List<HxField> getFields() {
    return Collections.emptyList();
  }

  @Override
  public HxType setFields(List<HxField> fields) {
    //NO OP
    return this;
  }

  @Override
  public List<HxMethod> getMethods() {
    return Collections.emptyList();
  }

  @Override
  public HxType setMethods(List<HxMethod> methods) {
    //NO OP
    return this;
  }

  @Override
  public HxType removeMethod(final HxMethod method) {
    //NO OP
    return this;
  }

  @Override
  public HxType addMethodAt(int index,
                            HxMethod method) {
    //NO OP
    return this;
  }

  @Override
  public Optional<HxMethod> findMethod(String name) {
    //NO OP
    return Optional.empty();
  }

  @Override
  public Collection<HxMethod> getMethods(String name) {
    return Collections.emptySet();
  }

  @Override
  public Optional<HxMethod> findMethodDirectly(String name,
                                               String descriptor) {
    for (HxMethod method : getMethods(name)) {
      if (method.hasDescriptor(descriptor)) {
        return Optional.of(method);
      }
    }
    return Optional.empty();
  }

  @Override
  public Optional<HxMethod> findMethod(final Optional<HxType> returnType,
                                       final String name,
                                       final List<HxType> parameters) {
    final Iterable<HxMethod> methods = getMethods(name);

    loop:
    for (HxMethod method : methods) {
      if (parameters.size() != method.getParametersCount()) {
        continue;
      }

      for (int i = 0, arity = method.getParametersCount(); i < arity; i++) {
        HxType type = method.getParameterTypeAt(i);

        if (!type.equals(parameters.get(i))) {
          continue loop;
        }
      }

      if (returnType.isPresent() &&
          Objects.equals(returnType.get(), method.getReturnType())) {
        return Optional.of(method);
      }
    }
    return Optional.empty();
  }

  @Override
  public Optional<HxMethod> findConstructorDirectly(final String descriptor) {
    for (HxMethod constructor : getConstructors()) {
      if (constructor.hasDescriptor(descriptor)) {
        return Optional.of(constructor);
      }
    }
    return Optional.empty();
  }

  @Override
  public Collection<HxMethod> findForwardingConstructors() {
    if (!isInterface() && !isArray() && !isPrimitive() &&
        !hasName("java.lang.Object") &&
        getSuperType().isPresent()) {

      final HxType superType = getSuperType().get();
      final Collection<HxMethod> constructors = getConstructors();
      final Collection<HxMethod> forwardingConstructors = new ArrayList<>(2);

      loop:
      for (HxMethod constructor : constructors) {
        if (constructor.hasBody()) {
          for (HxInstruction inst : constructor.getBody()) {
            if (inst.hasSort(HxInstructionSort.Invocation) && inst instanceof AbstractInvokeInstruction) {
              final AbstractInvokeInstruction invokeInstruction = (AbstractInvokeInstruction) inst;

              if (invokeInstruction.hasType(HxInstructionTypes.Invocation.INVOKESPECIAL) &&
                  HxConstants.CONSTRUCTOR_METHOD_NAME.equals(invokeInstruction.getName())) {

                if (superType.hasName(invokeInstruction.getOwner())) {
                  forwardingConstructors.add(constructor);
                  continue loop;
                } else if (hasName(invokeInstruction.getOwner())) {
                  continue loop;
                }
              }
            }
          }
        }
      }
      return forwardingConstructors;
    }

    return Collections.emptySet();
  }

  @Override
  public Optional<HxMethod> findOrCreateDefaultConstructor() {
    Optional<HxMethod> defaultConstructorOptional = findDefaultConstructor();
    if (defaultConstructorOptional.isPresent()) {
      return defaultConstructorOptional;
    }

    Optional<HxMethod> defaultSuperConstructor = getSuperType().get().findDefaultConstructor();
    if (!defaultSuperConstructor.isPresent() || !defaultSuperConstructor.get().isAccessibleFrom(this)) {
      return Optional.empty();
    }

    HxMethod defaultConstructor = getHaxxor().createConstructor(new String[0]);
    HxExtendedCodeStream stream = defaultConstructor.getBody().getFirst().asStream();
    stream
      .THIS() //Uninitialized
      .INVOKESPECIAL(defaultSuperConstructor.get())
      .RETURN();

    return Optional.of(defaultConstructor);
  }

  @Override
  public Optional<HxMethod> findOrCreateClassInitializer() {
    Optional<HxMethod> classInitializerOptional = findClassInitializer();
    if (classInitializerOptional.isPresent()) {
      return classInitializerOptional;
    }

    HxMethod classInitializer = getHaxxor()
      .createMethod("void", HxConstants.CLASS_INITIALIZER_METHOD_NAME)
      .setModifiers(HxMethod.Modifiers.STATIC);

    HxExtendedCodeStream stream = classInitializer.getBody().getFirst().asStream();
    stream.RETURN();

    return Optional.of(classInitializer);
  }

  @Override
  public Optional<HxMethod> findConstructor(final List<HxType> signature) {
    loop:
    for (HxMethod constructor : getConstructors()) {
      if (signature.size() != constructor.getParametersCount()) {
        continue;
      }

      for (int i = 0, arity = constructor.getParametersCount(); i < arity; i++) {
        HxType type = constructor.getParameterTypeAt(i);

        if (!type.equals(signature.get(i))) {
          continue loop;
        }
      }
      return Optional.of(constructor);
    }
    return Optional.empty();
  }

  @Override
  public Optional<HxMethod> findConstructor(HxType... signature) {
    return findConstructor(Arrays.asList(signature));
  }

  @Override
  public Optional<HxMethod> findConstructor(String... signature) {
    return findConstructor(getHaxxor().referencesAsArray(signature));
  }

  @Override
  public Optional<HxGenericType> getGenericType() {
    return Optional.empty();
  }

  @Override
  public boolean isAssignableFrom(final HxType otherType) {
    if (otherType == null) {
      return false;
    } else if (equals(otherType)) {
      return true;
    } else if (isPrimitive()) {
      return false;
    } else if (isArray()) {
      if (!otherType.isArray()) {
        return false;
      }
      return this.getDimension() == otherType.getDimension() &&
             this.getComponentType().get()
                 .isAssignableFrom(otherType.getComponentType().get());
    }

    HxType current = otherType; //current is not NULL at this moment

    if (isInterface()) {
      do {
        //search only for interfaces of current type (independent whether interface or not)
        for (HxType itf : current.getInterfaces()) {
          if (isAssignableFrom(itf)) {
            return true;
          }
        }
        if (!current.hasSuperType()) {
          break;
        }
        current = current.getSuperType().get();
      } while (true);
    } else {
      do {
        if (equals(current)) {
          return true;
        }
        if (!current.hasSuperType()) {
          break;
        }
        current = current.getSuperType().get();
      } while (true);
    }
    return false;
  }

  @Override
  public int distanceTo(final HxType otherType,
                        final int arrayCost,
                        final int extendsCost,
                        final int interfaceCost) {
    return estimateDistance(otherType, this, 0, arrayCost, extendsCost, interfaceCost);
  }

  @Override
  public Collection<HxField> fields(Predicate<HxField> predicate,
                                    boolean recursive) {
    return Collections.emptySet();
  }

  @Override
  public Collection<HxMethod> methods(Predicate<HxMethod> predicate,
                                      boolean recursive) {
    return Collections.emptySet();
  }

  @Override
  public Collection<HxMethod> constructors(Predicate<HxMethod> predicate,
                                           boolean recursive) {
    return Collections.emptySet();
  }

  @Override
  public Collection<HxType> types(Predicate<HxType> predicate,
                                  boolean recursive) {
    return Collections.emptySet();
  }

  @Override
  public Collection<HxType> interfaces(Predicate<HxType> predicate,
                                       boolean recursive) {
    return Collections.emptySet();
  }

  @Override
  public HxType getDeclaringType() {
    return getEnclosingType().orElse(this);
  }

  @Override
  public Optional<HxType> getEnclosingType() {
    if (getEnclosingMethod().isPresent()) {
      return Optional.of(getEnclosingMethod().get().getDeclaringMember());
    }
    if (getDeclaringMember() != null) {
      return Optional.of((HxType) getDeclaringMember());
    }
    return Optional.empty();
  }

  @Override
  public Optional<HxMethod> getEnclosingMethod() {
    if (getDeclaringMember() instanceof HxMethod) {
      return Optional.of((HxMethod) getDeclaringMember());
    }
    return Optional.empty();
  }

  protected final String getSimpleBinaryName() {
    /* HxLinkedCode was copied from original java.lang.Class */
    final String name = getName();
    int dollarIndex = name.lastIndexOf('$');
    if (dollarIndex < 0) {
      return null;
    }
    return name.substring(dollarIndex);
  }

  @Override
  public String getSimpleName() {
    /* HxLinkedCode was copied from original java.lang.Class */
    if (isArray()) {
      return getComponentType().get().getSimpleName() + "[]";
    }

    String simpleName = getSimpleBinaryName();
    if (simpleName == null) {
      // top level class
      simpleName = getName();
      int dotIndex = simpleName.lastIndexOf(".");
      return dotIndex < 0 ?
             simpleName :
             simpleName.substring(dotIndex + 1); // strip the package name
    }

    // Remove leading "\$[0-9]*" from the name
    int length = simpleName.length();
    if (length < 1 || simpleName.charAt(0) != '$') {
      throw new InternalError("Malformed class name");
    }
    int index = 1;
    while (index < length &&
           simpleName.charAt(index) >= '0' &&
           simpleName.charAt(index) <= '9') {
      index++;
    }
    // Eventually, this is the empty string if this is an anonymous class
    return simpleName.substring(index);
  }

  @Override
  public Optional<HxType> getComponentType() {
    if (!isArray()) {
      return Optional.empty();
    }

    final String name = getName();
    final String componentType = name.substring(0, name.length() - 2);
    final HxTypeReference reference = getHaxxor().reference(componentType);

    return Optional.of(reference);
  }

  @Override
  public int getDimension() {
    final String name = getName();
    final int len = name.length();

    int dim = 0;
    int pos = len;

    while (pos >= 2 &&
           name.charAt(pos - 2) == '[' &&
           name.charAt(pos - 1) == ']') {
      dim++;
      pos -= 2;
    }
    return dim;
  }

  @Override
  public boolean isPrimitive() {
    return false;
  }

  @Override
  public boolean isLocalType() {
    return isLocalOrAnonymousClass() &&
           !isAnonymous();
  }

  /**
   * Returns {@code true} if this is a local class or an anonymous
   * class.  Returns {@code false} otherwise.
   */
  protected boolean isLocalOrAnonymousClass() {
    if (!hasModifiers(Modifiers.STATIC)) {
      return getDeclaringMember() instanceof HxMethod;
    }
    return false;
  }

  @Override
  public boolean isMemberType() {
    return getSimpleBinaryName() != null &&
           !isLocalOrAnonymousClass();
  }

  @Override
  public HxTypeReference toReference() {
    if (isReference()) {
      return (HxTypeReference) this;
    }
    return getHaxxor().createReference(this);
  }

  @Override
  public byte[] toByteCode() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Class<?> loadClass(final ClassLoader classLoader)
  throws ClassNotFoundException {
    String name = getName();
    if (isArray()) {
      name = toDescriptor()
        .replace(HxConstants.INTERNAL_PACKAGE_SEPARATOR_CHAR,
                 HxConstants.JAVA_PACKAGE_SEPARATOR_CHAR);
    }
    return Class.forName(name, true, classLoader);
  }

  @Override
  public boolean hasDescriptor(final String descriptor) {
    return HxMethodImpl.checkDescriptorsParameters(this, descriptor) > -1;
  }

  @Override
  public int hashCode() {
    return this.name.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof HxType)) {
      return false;
    }
    return Objects.equals(getName(), ((HxType) obj).getName());
  }

  @Override
  public String toString() {
    return getName();
  }
}
