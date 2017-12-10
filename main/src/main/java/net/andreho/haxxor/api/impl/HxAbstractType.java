package net.andreho.haxxor.api.impl;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxConstants;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxMethodBody;
import net.andreho.haxxor.api.HxSort;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.utils.NamingUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static net.andreho.haxxor.api.impl.HxImplTools.checkDescriptorsParameters;

/**
 * <br/>Created by andreho on 3/26/16 at 7:21 PM.<br/>
 */
public abstract class HxAbstractType
  extends HxAnnotatedImpl<HxType> implements HxType {

  private final Hx haxxor;
  private final String name;

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
    return this.haxxor;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public HxSort getSort() {
    return HxSort.OBJECT;
  }

  @Override
  public HxType setSuperType(String superType) {
    if (superType == null) {
      if (hasName("java.lang.Object")) {
        setSuperType((HxType) null);
        return this;
      }
      throw new IllegalArgumentException("Supertype can't be null.");
    }
    return setSuperType(getHaxxor().reference(superType));
  }

  @Override
  public Optional<HxField> findField(String name) {
    return Optional.ofNullable(findFieldInternally(name));
  }

  @Override
  public Optional<HxField> findField(final String name,
                                     final HxType type) {
    return Optional.ofNullable(findFieldInternally(name, type.getName()));
  }

  @Override
  public Optional<HxField> findField(final String name,
                                     final Class<?> type) {
    return Optional.ofNullable(findFieldInternally(name, type.getName()));
  }

  @Override
  public Optional<HxField> findField(final String name,
                                     final String type) {
    return Optional.ofNullable(findFieldInternally(name, type));
  }

  protected HxField findFieldInternally(final String name) {
    return findFieldInternally(name, null);
  }

  protected HxField findFieldInternally(final String name, final String type) {
    for(HxField field : getFields()) {
      if(field.hasName(name) &&
         (type == null || field.hasType(type))) {
        return field;
      }
    }
    return null;
  }

  @Override
  public Collection<HxMethod> findForwardingConstructors() {
    if (!isInterface() &&
        !isArray() &&
        !isPrimitive() &&
        getSuperType().isPresent()) {

      final Collection<HxMethod> constructors = getConstructors();
      final Collection<HxMethod> forwardingConstructors = new ArrayList<>(2);

      loop:
      for (HxMethod constructor : constructors) {
        if(constructor.isForwardingConstructor()) {
          forwardingConstructors.add(constructor);
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
    addMethod(defaultConstructor);
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

    HxMethodBody body = classInitializer.getBody();
    HxExtendedCodeStream stream = body.asStream();
    stream.RETURN();
    addMethod(classInitializer);
    return Optional.of(classInitializer);
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
    return HxImplTools.estimateDistance(otherType, this, 0, arrayCost, extendsCost, interfaceCost);
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

  @Override
  public String getSimpleName() {
    /* Code was copied from original java.lang.Class */
    if (isArray()) {
      return getComponentType().get().getSimpleName() + "[]";
    }

    String simpleName = NamingUtils.toSimpleBinaryName(getName());
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
  public HxType toReference() {
    if (isReference()) {
      return this;
    }
    return getHaxxor().createReference(this);
  }

  @Override
  public byte[] toByteCode() {
    throw new UnsupportedOperationException("This class '"+getName()+"' can't be transformed to bytecode.");
  }

  @Override
  public Class<?> toClass(final ClassLoader classLoader)
  throws ClassNotFoundException {
    if (isArray()) {
      String name = toDescriptor()
        .replace(HxConstants.INTERNAL_PACKAGE_SEPARATOR_CHAR,
                 HxConstants.JAVA_PACKAGE_SEPARATOR_CHAR);
      return Class.forName(name, true, classLoader);
    }
    return getHaxxor().resolveClass(classLoader, null, getName(), toByteCode());
  }

  @Override
  public boolean hasDescriptor(final String descriptor) {
    return checkDescriptorsParameters(this, descriptor) > -1;
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
    final HxType that = (HxType) obj;
    return isArray() == that.isArray() &&
           isPrimitive() == that.isPrimitive() &&
           Objects.equals(getName(), that.getName());
  }

  @Override
  public String toString() {
    return getName();
  }
}
