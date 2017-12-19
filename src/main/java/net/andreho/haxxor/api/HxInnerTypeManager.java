package net.andreho.haxxor.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 06.12.2017 at 13:38.
 */
public interface HxInnerTypeManager<O extends HxInnerTypeManager<O>>
  extends HxInitializable<O>, HxProvider {

  /**
   * @param innerType of this type
   * @return this
   */
  default O addInnerType(final HxType innerType) {
    Objects.requireNonNull(innerType, "Given inner type is null.");
    initialize(HxInitializablePart.INNER_TYPES)
      .getInnerTypes()
      .add(innerType);
    return (O) this;
  }

  /**
   * @return a list with all registered inner types of this type
   */
  default List<HxType> getInnerTypes() {
    return Collections.emptyList();
  }

  /**
   * @param declaredTypes a list with all declared inner types
   * @return this
   */
  default HxType setInnerTypes(List<HxType> declaredTypes) {
    throw new UnsupportedOperationException("This class can't define any inner types: " + this);
  }

  /**
   * @param declaredTypes a list with all declared inner types
   * @return this
   */
  default HxType setInnerTypes(HxType... declaredTypes) {
    return setInnerTypes(Arrays.asList(declaredTypes));
  }

  /**
   * @param cls to look for
   * @return
   */
  default boolean hasInnerType(Class<?> cls) {
    Objects.requireNonNull(cls, "Given class is null.");
    return cls.getDeclaringClass() != null && hasInnerType(cls.getName());
  }

  /**
   * @param type to look for
   * @return
   */
  default boolean hasInnerType(HxType type) {
    Objects.requireNonNull(type, "Given type is null.");
    return hasInnerType(type.getName());
  }

  /**
   * @param classname to look for
   * @return
   */
  default boolean hasInnerType(String classname) {
    for(HxType innerType : getInnerTypes()) {
      if(innerType.hasName(classname)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Removes given type from list of inner types
   * @param type to remove
   * @return
   */
  default O removeInnerType(HxType type) {
    Objects.requireNonNull(type, "Given type is null.");
    for(Iterator<HxType> iterator = getInnerTypes().iterator(); iterator.hasNext(); ){
      if (iterator.next().equals(type)) {
        iterator.remove();
        break;
      }
    }
    return (O) this;
  }

  /**
   * If this type is an inner class then this method return a type reference to the enclosing type
   * @return the enclosing type of this type if present
   */
  default Optional<HxType> getEnclosingType() {
    return Optional.empty();
  }

  /**
   * If this type is an inner local class then this method return a type reference to the enclosing method/constructor
   * @return the enclosing method of this type if present
   */
  default Optional<HxMethod> getEnclosingMethod() {
    return Optional.empty();
  }
}
