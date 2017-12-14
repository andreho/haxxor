package net.andreho.haxxor.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 06.12.2017 at 09:11.
 */
public interface HxInheritanceManager<T> extends HxInitializable<HxType>, HxProvider {
  /**
   * @return the parent-type of this type
   */
  default Optional<HxType> getSupertype() {
    return Optional.empty();
  }

  /**
   * @param supertype of this type
   * @return this instance
   * @throws UnsupportedOperationException if this operation isn't allowed
   */
  default T setSupertype(HxType supertype) {
    throw new UnsupportedOperationException("This class can't define a supertype: " + this);
  }

  /**
   * Shortcut for: <code>this.setSupertype(getHaxxor().reference(supertype))</code>
   *
   * @param supertype to reference as super type
   * @return this instance
   * @throws UnsupportedOperationException if this operation isn't allowed
   */
  default T setSupertype(String supertype) {
    return setSupertype(getHaxxor().reference(supertype));
  }

  /**
   * Shortcut for: <code>this.setSupertype(getHaxxor().reference(superclass.getName()))</code>
   *
   * @param superclass to reference as super type
   * @return this instance
   * @throws UnsupportedOperationException if this operation isn't allowed
   * @throws IllegalArgumentException if the given class can't be used as a superclass
   */
  default T setSupertype(Class<?> superclass) {
    if (superclass.isPrimitive() ||
        superclass.isArray() ||
        superclass.isInterface()) {
      throw new IllegalArgumentException("Given class can't be used as superclass: " + superclass);
    }
    return setSupertype(getHaxxor().reference(superclass.getName()));
  }

  /**
   * @param supertype to look for
   * @return <b>true</b> if this type has the given supertype
   */
  default boolean hasSupertype(String supertype) {
    return getSupertype().isPresent() ?
           getSupertype().get().hasName(supertype) :
           supertype == null;
  }

  /**
   * @param superType to look for
   * @return <b>true</b> if this type has the given supertype
   */
  default boolean hasSupertype(HxType superType) {
    return getSupertype().isPresent() ?
           getSupertype().get().equals(superType) :
           superType == null;
  }

  /**
   * @param superclass to check against
   * @return <b>true</b> if this type has the given superclass
   */
  default boolean hasSupertype(Class<?> superclass) {
    return hasSupertype(superclass.getName());
  }

  /**
   * @return <b>true</b> if this type has a reference to a supertype, <b>false</b> otherwise.
   */
  default boolean hasSupertype() {
    return getSupertype().isPresent();
  }

  /**
   * @param annotated
   * @return this instance
   */
  default T setAnnotatedSupertype(HxAnnotated<?> annotated) {
    throw new UnsupportedOperationException("This class can't define any superclass annotations: "+this);
  }

  /**
   * @return
   */
  default Optional<HxAnnotated<?>> getAnnotatedSupertype() {
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
    for(Class<?> itf : interfaces) {
      if (itf == null || !itf.isInterface()) {
        throw new IllegalArgumentException("Given array with classes must contain interfaces only: " + itf);
      }
    }
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
}
