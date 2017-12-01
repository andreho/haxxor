package net.andreho.haxxor.api;

import java.util.Collections;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 30.11.2017 at 08:49.
 */
public interface HxMethodOwner<O extends HxMethodOwner<O> & HxInitializable<InitializablePart, HxMethodOwner<O>>>
  extends HxInitializable<InitializablePart, HxMethodOwner<O>>,
          HxNamed,
          HxProvider {

  /**
   * Loads the declared methods if needed
   * @return owning instance
   */
  default O loadMethods() {
    throw new UnsupportedOperationException("This class '"+getName()+"' can't define any methods.");
  }

  /**
   * @return the list with all methods
   */
  default List<HxMethod> getMethods() {
    return Collections.emptyList();
  }

  /**
   * @param methods
   * @return owning instance
   */
  default O setMethods(List<HxMethod> methods) {
    throw new UnsupportedOperationException("This class '"+getName()+"' can't define any methods.");
  }

  /**
   * @param method to search for
   * @return <b>-1</b> if given method doesn't belong to this type,
   * otherwise zero-based position of the given method in the {@link #getMethods()} list
   */
  default int indexOf(HxMethod method) {
    if (!equals(method.getDeclaringMember())) {
      return -1;
    }
    int idx = 0;
    for (HxMethod hxMethod : getMethods()) {
      if (method == hxMethod || method.equals(hxMethod)) {
        return idx;
      }
      idx++;
    }
    return -1;
  }

  /**
   * Adds the given method instance to this owner
   * @param method to add
   * @return owning instance
   */
  default O addMethod(HxMethod method) {
    return addMethodAt(getMethods().size(), method);
  }

  /**
   * Adds the given method instance to this owner at a specific index
   * @param index  where to insert given method
   * @param method to add
   * @return owning instance
   */
  default O addMethodAt(int index, HxMethod method) {
    throw new UnsupportedOperationException("This class '"+getName()+"' can't define any methods.");
  }

  /**
   * Removes the given method from this method-owner
   * @param method to remove
   * @return owning instance
   */
  default O removeMethod(HxMethod method) {
    throw new UnsupportedOperationException("This class '"+getName()+"' can't define any methods.");
  }

  /**
   * @param constructor to add
   * @return owning instance
   */
  default O addConstructor(HxMethod constructor) {
    return addConstructorAt(getMethods().size(), constructor);
  }

  /**
   * @param index for the given constructor
   * @param constructor to add
   * @return owning instance
   */
  default O addConstructorAt(int index, HxMethod constructor) {
    if (!constructor.hasName(HxConstants.CONSTRUCTOR_METHOD_NAME)) {
      throw new IllegalArgumentException("Not a constructor: " + constructor);
    }
    return addMethodAt(index, constructor);
  }

  /**
   * @param constructor to remove
   * @return owning instance
   */
  default O removeConstructor(HxMethod constructor) {
    if (!constructor.hasName(HxConstants.CONSTRUCTOR_METHOD_NAME)) {
      throw new IllegalArgumentException("Not a constructor: " + constructor);
    }
    return removeMethod(constructor);
  }
}
