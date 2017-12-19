package net.andreho.haxxor.api;

import net.andreho.haxxor.utils.NamingUtils;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static net.andreho.haxxor.api.HxConstants.ARRAY_DIMENSION;
import static net.andreho.haxxor.api.HxConstants.DESC_ARRAY_PREFIX_STR;
import static net.andreho.haxxor.api.HxConstants.JAVA_PACKAGE_SEPARATOR_CHAR;
import static net.andreho.haxxor.utils.NamingUtils.equalClassnames;
import static net.andreho.haxxor.utils.NamingUtils.equalPackageNames;
import static net.andreho.haxxor.utils.NamingUtils.hasSimpleBinaryName;
import static net.andreho.haxxor.utils.NamingUtils.isDescriptor;
import static net.andreho.haxxor.utils.NamingUtils.toSimpleName;

/**
 * <br/>Created by a.hofmann on 01.12.2017 at 18:10.
 */
public interface HxTypeAnalysis<O extends HxInheritanceManager<O> & HxOwned<O> & HxMember<O>>
  extends HxInheritanceManager<O>,
          HxMember<O>,
          HxOwned<O>,
          HxNamed {
  /**
   * @return simple name of this type
   * @see Class#getSimpleName()
   */
  default String getSimpleName() {
    return toSimpleName(getName());
  }

  /**
   * @return package-name of this type
   */
  default String getPackageName() {
    int index = getName().lastIndexOf(JAVA_PACKAGE_SEPARATOR_CHAR);
    return index < 0 ? "" : getName().substring(0, index);
  }

  /**
   * @param packageName
   * @return
   */
  default boolean hasPackageName(String packageName) {
    final String name = getName();
    return name.startsWith(packageName) &&
           name.charAt(name.length()) == JAVA_PACKAGE_SEPARATOR_CHAR;
  }

  /**
   * @param classname of other type
   * @return
   */
  default boolean hasSamePackageName(String classname) {
    return equalPackageNames(getName(), classname);
  }

  /**
   * @param other type whose name is going to be checked
   * @return
   */
  default boolean hasSamePackageName(HxType other) {
    return equalPackageNames(getName(), other.getName());
  }

  /**
   * @param other type whose name is going to be checked
   * @return
   */
  default boolean hasSamePackageName(Class<?> other) {
    return equalPackageNames(getName(), other.getName());
  }

  /**
   * @param cls whose name to check
   * @return
   */
  default boolean hasName(Class<?> cls) {
    return hasName(cls.getName());
  }

  /**
   * Shortcut for: <code>getName().equals(haxxor.toNormalizedClassname(classname))</code>
   *
   * @param classname to check against
   * @return <b>true</b> if name of this type is equal to the given one, <b>false</b> otherwise.
   */
  default boolean hasName(String classname) {
    if(isArray() != NamingUtils.isArray(classname)) {
      return false;
    }
    if(isDescriptor(classname)) {
      return hasDescriptor(classname);
    }
    return equalClassnames(getName(), classname);
  }

  /**
   * @param className to check against current classname and all names of parent types
   * @return <b>true</b> if name of this type is equal to the given one or if anyone of super types has the given name,
   * <b>false</b> otherwise.
   */
  default boolean hasNameViaExtends(String className) {
    if (!hasName(className)) {
      final Optional<HxType> superType = getSupertype();
      return superType.isPresent() &&
             superType.get().hasNameViaExtends(className);
    }
    return true;
  }

  /**
   * @return sort of this type
   */
  default HxSort getSort() {
    return HxSort.fromName(getName());
  }

  /**
   * @return count of slots that are needed to store a value of this type on stack or as local variable
   * @implSpec <code>long</code> and <code>double</code> take two slots and all other only one
   */
  default int getSlotSize() {
    final String name = getName();
    return isVoid() ? 0 :
           ("long".equals(name) || "double".equals(name)) ? 2 : 1;
  }

  /**
   * Shortcut for: <code>otherType.isAssignableFrom(this)</code>
   *
   * @param otherType to check against
   * @return
   */
  default boolean isTypeOf(HxType otherType) {
    return otherType.isAssignableFrom((HxType) this);
  }

  /**
   * @param otherType
   * @return
   * @see Class#isAssignableFrom(Class)
   */
  default boolean isAssignableFrom(String otherType) {
    return isAssignableFrom(getHaxxor().reference(otherType));
  }

  /**
   * @param otherType
   * @return
   * @see Class#isAssignableFrom(Class)
   */
  default boolean isAssignableFrom(Class<?> otherType) {
    return isAssignableFrom(getHaxxor().reference(otherType.getName()));
  }

  /**
   * @param otherType
   * @return
   * @see Class#isAssignableFrom(Class)
   */
  default boolean isAssignableFrom(HxType otherType) {
    return equals(otherType);
  }

  /**
   * This method calculates a number of hops from this type to other subtype or interface-type
   *
   * @param otherType as destination
   * @return <b>-1</b> if there isn't any way to reach given type, otherwise a calculated distance
   * @implNote by default this method uses array-cost equal to 3, extends-cost equal to 90 and interface-cost equal
   * to 9000
   */
  default int distanceTo(HxType otherType) {
    return distanceTo(otherType, 3, 90, 9000);
  }

  /**
   * This method calculates a number of hops from this type to other super- or interface-type
   *
   * @param otherType     as destination
   * @param arrayCost     for arrays destinations
   * @param extendsCost   for super-type visiting
   * @param interfaceCost for interface-type visiting
   * @return <b>-1</b> if there isn't any way to reach given type, otherwise a calculated distance
   */
  default int distanceTo(HxType otherType,
                         int arrayCost,
                         int extendsCost,
                         int interfaceCost) {
    return hasName(otherType.getName()) ? 0 : -1;
  }

  /**
   * @return component type of this array type or <b>empty()</b> if this isn't an array type
   */
  default Optional<HxType> getComponentType() {
    return Optional.empty();
  }

  /**
   * @return whether this type represents an array type or not
   */
  default boolean isArray() {
    String name = getName();
    return name.endsWith(ARRAY_DIMENSION) ||
           name.startsWith(DESC_ARRAY_PREFIX_STR);
  }

  /**
   * @return a number of dimensions which this array type has or zero if this isn't an array type
   */
  default int getDimension() {
    if(!isArray()) {
      return 0;
    }
    int dim = 0;
    Optional<HxType> component = getComponentType();
    while (component.isPresent()) {
      component = component.get().getComponentType();
      dim++;
    }
    return dim;
  }

  /**
   * @return internal classname of this type
   */
  default String toInternalName() {
    if (isPrimitive() || isArray()) {
      return toDescriptor();
    }
    return getName().replace(
      JAVA_PACKAGE_SEPARATOR_CHAR,
      HxConstants.INTERNAL_PACKAGE_SEPARATOR_CHAR
    );
  }

  /**
   * @param descriptor of a type to compare with
   * @return <b>true</b> if this type has the given descriptor
   */
  default boolean hasDescriptor(String descriptor) {
    return descriptor.equals(toDescriptor());
  }

  /**
   * @return parameter descriptor of this type
   */
  default String toDescriptor() {
    if(isPrimitive()) {
      return getSort().getDescriptor();
    }
    return toDescriptor(new StringBuilder(getName().length() + 2)).toString();
  }

  /**
   * @param builder to use
   * @return parameter descriptor of this type printed to the given builder instance
   */
  default Appendable toDescriptor(Appendable builder) {
    try {
      if (isArray()) {
        builder.append('[');
        return getComponentType().get().toDescriptor(builder);
      }
      if (isPrimitive()) {
        return builder.append(getSort().getDescriptor());
      }
      builder.append('L');
      final String name = getName();
      for (int i = 0, len = name.length(); i < len; i++) {
        char c = name.charAt(i);
        if (c == JAVA_PACKAGE_SEPARATOR_CHAR) {
          c = HxConstants.INTERNAL_PACKAGE_SEPARATOR_CHAR;
        }
        builder.append(c);
      }
      builder.append(';');
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    return builder;
  }

  /**
   * @param predicate
   * @return
   */
  default Optional<HxType> visitHierarchy(Predicate<HxType> predicate) {
    return visitHierarchy(predicate, true, true);
  }

  /**
   * @param predicate
   * @param visitClasses
   * @param visitInterfaces
   * @return
   */
  default Optional<HxType> visitHierarchy(Predicate<HxType> predicate, boolean visitClasses, boolean visitInterfaces) {
    final HxType start = (HxType) this;
    HxType current = start;
    if(visitClasses) {
      while (true) {
        if(predicate.test(current)) {
          return Optional.of(current);
        }
        if (!current.hasSupertype()) {
          break;
        }
        current = current.getSupertype().orElseThrow(IllegalStateException::new);
      }
    }
    if (visitInterfaces) {
      current = start;
      while (current.hasSupertype()) {
        for (HxType itf : current.getInterfaces()) {
          if(predicate.test(itf)) {
            return Optional.of(itf);
          }
        }
        for (HxType itf : current.getInterfaces()) {
          itf.visitHierarchy(predicate, false, visitInterfaces);
        }
        current = current.getSupertype().orElseThrow(IllegalStateException::new);
      }
    }
    return Optional.empty();
  }

  /**
   * @param consumer
   * @return
   */
  default HxType visitHierarchy(Consumer<HxType> consumer) {
    return visitHierarchy(consumer, true, true);
  }

  /**
   * @param consumer
   * @param visitClasses
   * @param visitInterfaces
   * @return
   */
  default HxType visitHierarchy(Consumer<HxType> consumer, boolean visitClasses, boolean visitInterfaces) {
    visitHierarchy((type) -> {
      consumer.accept(type);
      return false;
    }, visitClasses, visitInterfaces);
    return (HxType) this;
  }

  /**
   * @return <b>true</b> if this is a primitive type, <b>false</b> otherwise.
   */
  default boolean isPrimitive() {
    return getSort().isPrimitive();
  }

  /**
   * @return <b>true</b> if this is a local type, <b>false</b> otherwise.
   */
  default boolean isLocalType() {
    return !isAnonymous() &&
           isLocalOrAnonymousClass();
  }

  /**
   * Returns {@code true} if this is a local class or an anonymous
   * class.  Returns {@code false} otherwise.
   */
  default boolean isLocalOrAnonymousClass() {
    if (!hasModifiers(HxType.Modifiers.STATIC)) {
      return NamingUtils.isLocalOrAnonymousClass(getName());
//      return getDeclaringMember() instanceof HxMethod;
    }
    return false;
  }

  /**
   * @return <b>true</b> if this is a member type, <b>false</b> otherwise.
   */
  default boolean isMemberType() {
    return hasSimpleBinaryName(getName()) &&
           !isLocalOrAnonymousClass();
  }

  /**
   * @return <b>true</b> if this is a deprecated type, <b>false</b> otherwise.
   */
  default boolean isDeprecated() {
    return hasModifiers(HxType.Modifiers.DEPRECATED);
  }

  /**
   * @return <b>true</b> if this is a final type, <b>false</b> otherwise.
   */
  default boolean isFinal() {
    return hasModifiers(HxType.Modifiers.FINAL);
  }

  /**
   * @return <b>true</b> if this is a static type, <b>false</b> otherwise.
   */
  default boolean isStatic() {
    return hasModifiers(HxType.Modifiers.STATIC);
  }

  /**
   * @return <b>true</b> if this is a public type, <b>false</b> otherwise.
   */
  default boolean isPublic() {
    return hasModifiers(HxType.Modifiers.PUBLIC);
  }

  /**
   * @return <b>true</b> if this is a protected type, <b>false</b> otherwise.
   */
  default boolean isProtected() {
    return hasModifiers(HxType.Modifiers.PROTECTED);
  }

  /**
   * @return <b>true</b> if this is a private type, <b>false</b> otherwise.
   */
  default boolean isPrivate() {
    return hasModifiers(HxType.Modifiers.PRIVATE);
  }

  /**
   * @return <b>true</b> if this is an internal (package-private) type, <b>false</b> otherwise.
   */
  default boolean isInternal() {
    return !isPublic() && !isProtected() && !isPrivate();
  }

  /**
   * @return <b>true</b> if this is a class (not a primitive, not an array and not an interface),
   * <b>false</b> otherwise.
   */
  default boolean isClass() {
    return !isPrimitive() && !isArray() && !isInterface();
  }

  /**
   * @return <b>true</b> if this is an abstract type, <b>false</b> otherwise.
   */
  default boolean isAbstract() {
    return hasModifiers(HxType.Modifiers.ABSTRACT);
  }

  /**
   * @return <b>true</b> if this is an interface type, <b>false</b> otherwise.
   */
  default boolean isInterface() {
    return hasModifiers(HxType.Modifiers.INTERFACE);
  }

  /**
   * @return <b>true</b> if this is an annotation type, <b>false</b> otherwise.
   */
  default boolean isAnnotation() {
    return hasModifiers(HxType.Modifiers.ANNOTATION);
  }

  /**
   * @return <b>true</b> if this is an enum type, <b>false</b> otherwise.
   */
  default boolean isEnum() {
    return hasModifiers(HxType.Modifiers.ENUM);
  }

  /**
   * @return <b>true</b> if this is an anonymous type, <b>false</b> otherwise.
   */
  default boolean isAnonymous() {
    return getSimpleName().isEmpty();
  }

  /**
   * @return <b>true</b> if this type is the <b>void</b> primitive type, <b>false</b> otherwise.
   */
  default boolean isVoid() {
    return hasName("void");
  }

  /**
   * @return <b>true</b> if this type can be instantiated, <b>false</b> otherwise.
   */
  default boolean isInstantiable() {
    return !isPrimitive() &&
           !isAbstract();
  }

  /**
   * @return <b>true</b> if this type can be serialized to corresponding byte-code, <b>false</b> otherwise.
   */
  default boolean isSerializable() {
    return !isPrimitive() &&
           !isArray();
  }

  /**
   * Defines this type as <b>static</b> and so declares it as a static inner class
   * @return owner instance
   */
  default O makeStatic() {
    //removeModifiers(HxType.Modifiers.ABSTRACT, HxType.Modifiers.INTERFACE, HxType.Modifiers.ANNOTATION)
    return addModifiers(HxType.Modifiers.STATIC);
  }

  /**
   * Defines this type as <b>final</b>
   * @return owner instance
   */
  default O makeFinal() {
    return removeModifiers(HxType.Modifiers.ABSTRACT, HxType.Modifiers.INTERFACE, HxType.Modifiers.ANNOTATION)
      .addModifiers(HxType.Modifiers.FINAL, HxType.Modifiers.SUPER);
  }

  /**
   * Defines this type as <b>public</b>
   * @return owner instance
   */
  default O makePublic() {
    return makeInternal().addModifier(HxType.Modifiers.PUBLIC);
  }

  /**
   * Defines this type as <b>package-private</b>
   * @return owner instance
   */
  default O makeInternal() {
    return removeModifiers(HxType.Modifiers.PUBLIC, HxType.Modifiers.PROTECTED, HxType.Modifiers.PRIVATE);
  }

  /**
   * Defines this type as <b>abstract</b>
   * @return owner instance
   */
  default O makeAbstract() {
    return removeModifier(HxType.Modifiers.FINAL).addModifier(HxType.Modifiers.ABSTRACT);
  }

  /**
   * Defines this type as <b>interface</b>
   * @return owner instance
   */
  default O makeInterface() {
    return makeAbstract() //removes final and sets abstract flag
          .removeModifiers(HxType.Modifiers.SUPER, HxType.Modifiers.ENUM)
          .addModifier(HxType.Modifiers.INTERFACE);
  }

  /**
   * Defines this type as <b>enum</b>
   * @return owner instance
   */
  default O makeEnum() {
    return makeFinal().addModifier(HxType.Modifiers.ENUM);
  }

  /**
   * Defines this type as <b>@interface</b>
   * @return owner instance
   */
  default O makeAnnotation() {
    return makeInterface()
      .addModifier(HxType.Modifiers.ANNOTATION);
  }
}
