package net.andreho.haxxor.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * <br/>Created by a.hofmann on 06.12.2017 at 09:11.
 */
public interface HxInheritanceManager<T> extends HxInitializable<HxType>, HxProvider {
  /**
   * @return the parent-type of this type
   */
  default Optional<HxType> getSuperType() {
    return Optional.empty();
  }

  /**
   * @param superType of this type
   * @return this instance
   */
  default T setSuperType(HxType superType) {
    throw new UnsupportedOperationException("This class can't define a supertype: " + this);
  }

  /**
   * Shortcut for: <code>this.setSuperType(getHaxxor().reference(superType))</code>
   *
   * @param superType to reference as super type
   * @return this instance
   */
  default T setSuperType(String superType) {
    return setSuperType(getHaxxor().reference(superType));
  }

  /**
   * Shortcut for: <code>this.setSuperType(getHaxxor().reference(superClass.getName()))</code>
   *
   * @param superClass to reference as super type
   * @return this instance
   */
  default T setSuperType(Class<?> superClass) {
    if (superClass.isPrimitive() ||
        superClass.isArray() ||
        superClass.isInterface()) {
      throw new IllegalArgumentException("Given class can't be used as superclass: " + superClass);
    }
    return setSuperType(getHaxxor().reference(superClass.getName()));
  }

  /**
   * @param superType to look for
   * @return <b>true</b> if this type has the given supertype
   */
  default boolean hasSuperType(String superType) {
    return getSuperType().isPresent() ?
            getSuperType().get().hasName(superType) :
           superType == null;
  }

  /**
   * @param superType to look for
   * @return <b>true</b> if this type has the given supertype
   */
  default boolean hasSuperType(HxType superType) {
    return getSuperType().isPresent() ?
            Objects.equals(getSuperType().get(), superType) :
           superType == null;
  }

  /**
   * @param superClass
   * @return <b>true</b> if this type has the given supertype
   */
  default boolean hasSuperType(Class<?> superClass) {
    return hasSuperType(superClass.getName());
  }

  /**
   * @return <b>true</b> if this type has a reference to a supertype, <b>false</b> otherwise.
   */
  default boolean hasSuperType() {
    return getSuperType().isPresent();
  }

  /**
   * @param annotated
   * @return this instance
   */
  default T setAnnotatedSuperType(HxAnnotated<?> annotated) {
    return (T) this;
  }

  /**
   * @return
   */
  default Optional<HxAnnotated<?>> getAnnotatedSuperType() {
    return Optional.empty();
  }

  /**
   * @return list with all interfaces defined by this type
   * @implNote never change this collection directly - use helper methods instead
   */
  default List<HxType> getInterfaces() {
    return Collections.emptyList();
  }

  /**
   * @param interfaces of this type
   * @return this instance
   */
  default T setInterfaces(List<HxType> interfaces) {
    throw new UnsupportedOperationException("This class can't define any interfaces: " + this);
  }

  /**
   * @param interfaces of this type
   * @return this instance
   */
  default T setInterfaces(HxType... interfaces) {
    return setInterfaces(Arrays.asList(interfaces));
  }

  /**
   * @param interfaces of this type
   * @return this instance
   */
  default T setInterfaces(String... interfaces) {
    return setInterfaces(getHaxxor().referencesAsList(interfaces));
  }

  /**
   * @param interfaces of this type
   * @return this instance
   */
  default T setInterfaces(Class<?>... interfaces) {
    return setInterfaces(getHaxxor().references(interfaces));
  }

  /**
   * @param interfaceName to add
   * @return this instance
   */
  default T addInterface(String interfaceName) {
    return addInterface(getHaxxor().reference(interfaceName));
  }

  /**
   * @param itf to add
   * @return this instance
   */
  default T addInterface(Class<?> itf) {
    Objects.requireNonNull(itf, "Interface can't be null.");
    if (!itf.isInterface()) {
      throw new IllegalArgumentException("Not an interface: " + itf);
    }
    return addInterface(itf.getName());
  }

  /**
   * @param itf to add
   * @return this instance
   */
  default T addInterface(HxType itf) {
    Objects.requireNonNull(itf, "Interface can't be null.");
    if(!hasInterface(itf)) {
      initialize(HxInitializablePart.INTERFACES);
      getInterfaces().add(itf);
    }
    return (T) this;
  }

  /**
   * @param interfaceName to look for
   * @return <b>true</b> if this type <code>implements</code> the given one interface
   */
  default boolean hasInterface(final String interfaceName) {
    Objects.requireNonNull(interfaceName, "Interface name can't be null.");
    return indexOfInterface(interfaceName) > -1;
  }

  /**
   * @param hxInterface to look for
   * @return <b>true</b> if this type <code>implements</code> the given one interface
   */
  default boolean hasInterface(final HxType hxInterface) {
    Objects.requireNonNull(hxInterface, "Interface can't be null.");
    return hasInterface(hxInterface.getName());
  }

  /**
   * @param itf to look for
   * @return <b>true</b> if this type <code>implements</code> the given one interface
   */
  default boolean hasInterface(final Class<?> itf) {
    Objects.requireNonNull(itf, "Interface can't be null.");
    return itf.isInterface() && hasInterface(itf.getName());
  }

  /**
   * @param hxInterface to look for
   * @return index of interface or -1
   */
  default int indexOfInterface(HxType hxInterface) {
    Objects.requireNonNull(hxInterface, "Interface can't be null.");
    return indexOfInterface(hxInterface.getName());
  }

  /**
   * @param itf to look for
   * @return index of interface or -1
   */
  default int indexOfInterface(Class<?> itf) {
    Objects.requireNonNull(itf, "Interface can't be null.");
    return indexOfInterface(itf.getName());
  }

  /**
   * @param interfaceName to look for
   * @return index of interface or -1
   */
  default int indexOfInterface(String interfaceName) {
    List<HxType> interfaces = getInterfaces();
    for(int i = 0, len = interfaces.size(); i < len; i++) {
      HxType itf = interfaces.get(i);
      if(itf.hasName(interfaceName)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * @param index of interface to remove
   * @return this instance
   */
  default T removeInterfaceAt(final int index) {
    if(index >= 0 && index < getInterfaces().size()) {
      getInterfaces().remove(index);
    }
    return (T) this;
  }

  /**
   * @param itf
   * @return this instance
   */
  default T removeInterface(Class<?> itf) {
    return removeInterface(itf.getName());
  }

  /**
   * @param itf
   * @return this instance
   */
  default T removeInterface(HxType itf) {
    return removeInterface(itf.getName());
  }

  /**
   * @param interfaceName
   * @return this instance
   */
  default T removeInterface(String interfaceName) {
    int idx = indexOfInterface(interfaceName);
    if(idx >= 0) {
      return removeInterfaceAt(idx);
    }
    return (T) this;
  }

  /**
   * @param index of an interface in the interface's list
   * @param annotated object
   * @return this instance
   */
  default T setAnnotatedInterface(int index, HxAnnotated<?> annotated) {
    if(index < 0 || index >= getInterfaces().size()) {
      throw new IndexOutOfBoundsException("Invalid interface index: "+index);
    }
    return (T) this;
  }

  /**
   * @param index of an interface in the interface's list
   * @return optional at interface's index
   */
  default Optional<HxAnnotated<?>> getAnnotatedInterface(int index) {
    if(index < 0 || index >= getInterfaces().size()) {
      throw new IndexOutOfBoundsException("Invalid interface index: "+index);
    }
    return Optional.empty();
  }

  /**
   * @param index of an interface in the interface's list
   * @return this instance
   */
  default T removeAnnotatedInterface(int index) {
    return setAnnotatedInterface(index, null);
  }

  /**
   * Removes all interfaces defined by this type
   * @return this instance
   */
  default T clearInterfaces() {
    return setInterfaces(Collections.emptyList());
  }

  /**
   * @param predicate
   * @return
   */
  default List<HxType> types(Predicate<HxType> predicate) {
    return types(predicate, false);
  }

  /**
   * @param predicate
   * @param recursive
   * @return
   */
  default List<HxType> types(Predicate<HxType> predicate, boolean recursive) {
    return Collections.emptyList();
  }

  /**
   * @param predicate
   * @return
   */
  default List<HxType> interfaces(Predicate<HxType> predicate) {
    return interfaces(predicate, false);
  }

  /**
   * @param predicate
   * @param recursive
   * @return
   */
  default List<HxType> interfaces(Predicate<HxType> predicate, boolean recursive) {
    return Collections.emptyList();
  }
}
