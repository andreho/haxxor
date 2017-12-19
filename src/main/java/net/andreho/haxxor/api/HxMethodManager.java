package net.andreho.haxxor.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 30.11.2017 at 08:49.
 */
public interface HxMethodManager<O extends HxMethodManager<O>>
  extends HxInitializable<O>,
          HxNamed,
          HxProvider {

  /**
   * Loads all declared methods of this class if not already done
   * @return owning instance
   */
  default O loadMethods() {
    throw new UnsupportedOperationException("This class '"+getName()+"' can't define any methods.");
  }

  /**
   * Removes all defined methods/constructors from this class and also allows to reuse {@link #loadMethods()}
   * @return owning instance
   */
  default O clearMethods() {
    return setMethods(Collections.emptyList());
  }

  /**
   * @return the list with all declared methods and constructors
   */
  default List<HxMethod> getMethods() {
    return Collections.emptyList();
  }

  /**
   * @param methods is a new list with all methods of this class
   * @return
   */
  default O setMethods(HxMethod ... methods) {
    return setMethods(Arrays.asList(methods));
  }

  /**
   * @param methods is a new list with all methods of this class
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
  default int indexOfMethod(HxMethod method) {
    if (!equals(method.getDeclaringMember())) {
      return -1;
    }
    int idx = 0;
    for (HxMethod hxMethod : getMethods()) {
      if (method == hxMethod ||
          method.equals(hxMethod)) {
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
   * @implNote adding any method yourself doesn't lead to deactivation of {@link #loadMethods()}
   */
  default O addMethod(HxMethod method) {
    return addMethodAt(getMethods().size(), method);
  }

  /**
   * Adds the given method instance to this owner at a specific index
   * @param index  where to insert given method into to the methods' list
   * @param method to add
   * @return owning instance
   * @implNote adding any method yourself doesn't lead to deactivation of {@link #loadMethods()}
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
   * @param index where to insert given constructor into to the methods' list
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
