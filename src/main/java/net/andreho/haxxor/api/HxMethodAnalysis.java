package net.andreho.haxxor.api;

import net.andreho.haxxor.Hx;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static net.andreho.haxxor.api.HxConstants.CONSTRUCTOR_METHOD_NAME;

/**
 * <br/>Created by a.hofmann on 30.11.2017 at 08:56.
 */
public interface HxMethodAnalysis<O extends HxMethodManager<O>>
  extends HxMethodManager<O> {

  /**
   * Loads the declared methods if needed and then caches them for faster analysis
   *
   * @return owning instance
   */
  default O cacheMethods() {
    throw new UnsupportedOperationException("This class '" + getName() + "' can't define any methods.");
  }

  /**
   * @param name of methods to fetch
   * @return a collection with methods with the given name (should not be modified)
   */
  default Collection<HxMethod> getMethods(String name) {
    return Collections.emptyList();
  }

  /**
   * Fetches all defined constructors
   *
   * @return a collection with all defined constructors
   */
  default Collection<HxMethod> getConstructors() {
    return getMethods(CONSTRUCTOR_METHOD_NAME);
  }

  /**
   * @param predicate
   * @return
   */
  default List<HxMethod> methods(Predicate<HxMethod> predicate) {
    return methods(predicate, false);
  }

  /**
   * @param predicate
   * @param recursive
   * @return
   */
  default List<HxMethod> methods(Predicate<HxMethod> predicate,
                                 boolean recursive) {
    return Collections.emptyList();
  }

  /**
   * @param predicate
   * @return
   */
  default List<HxMethod> constructors(Predicate<HxMethod> predicate) {
    return constructors(predicate, false);
  }

  /**
   * @param predicate
   * @param recursive
   * @return
   */
  default List<HxMethod> constructors(Predicate<HxMethod> predicate,
                                      boolean recursive) {
    return Collections.emptyList();
  }

  /**
   * Looks for a method with the given unique name and without any parameters
   *
   * @param name of a method without any parameters
   * @return {@link Optional#empty() empty} or a method with given name
   * @throws IllegalStateException if there is more than one method with given name
   */
  default Optional<HxMethod> findMethod(String name) {
    Collection<HxMethod> methods = getMethods(name);
    if (!methods.isEmpty()) {
      if (methods.size() > 1) {
        throw new IllegalStateException("There are more than one method with the given name: " + name);
      }
      return Optional.of(methods.iterator().next());
    }
    return Optional.empty();
  }

  /**
   * Looks for a method with the given unique name and with the given descriptor
   *
   * @param name       is the name of the wanted method
   * @param descriptor is the signature descriptor of the desired method
   * @return {@link Optional#empty() empty} or a method with given name and signature
   */
  default Optional<HxMethod> findMethodDirectly(String name,
                                                String descriptor) {
    for (HxMethod method : getMethods(name)) {
      if (method.hasDescriptor(descriptor)) {
        return Optional.of(method);
      }
    }
    return Optional.empty();
  }

  /**
   * @param method to look for
   * @return
   */
  default Optional<HxMethod> findMethod(Method method) {
    return findMethod(method.getReturnType(), method.getName(), method.getParameterTypes());
  }

  /**
   * @param constructor to look for
   * @return
   */
  default Optional<HxMethod> findConstructor(HxMethod constructor) {
    return findMethod(CONSTRUCTOR_METHOD_NAME, constructor.getParameterTypes());
  }

  /**
   * @param constructor to look for
   * @return
   */
  default Optional<HxMethod> findConstructor(Constructor<?> constructor) {
    return findMethod(CONSTRUCTOR_METHOD_NAME, constructor.getParameterTypes());
  }

  /**
   * @param parameters of a constructor to look for
   * @return
   */
  default Optional<HxMethod> findConstructor(String... parameters) {
    return findMethod(CONSTRUCTOR_METHOD_NAME, parameters);
  }

  /**
   * @param parameters of a constructor to look for
   * @return
   */
  default Optional<HxMethod> findConstructor(Class<?>... parameters) {
    return findMethod(CONSTRUCTOR_METHOD_NAME, parameters);
  }

  /**
   * @param parameters of a constructor to look for
   * @return
   */
  default Optional<HxMethod> findConstructor(HxType... parameters) {
    return findMethod(CONSTRUCTOR_METHOD_NAME, parameters);
  }

  /**
   * @param parameters of a constructor to look for
   * @return
   */
  default Optional<HxMethod> findConstructor(List<HxType> parameters) {
    return findMethod(CONSTRUCTOR_METHOD_NAME, parameters);
  }

  /**
   * @param method
   * @return
   */
  default Optional<HxMethod> findMethod(HxMethod method) {
    return findMethod(method.getReturnType(),
                      method.getName(),
                      method.getParameterTypes());
  }

  /**
   * @param returnType of the method to find
   * @param name       of the method to find
   * @param parameters of the method to find
   * @return
   */
  default Optional<HxMethod> findMethod(Optional<HxType> returnType,
                                        String name,
                                        List<HxType> parameters) {
    for (HxMethod method : getMethods()) {
      if (method.hasName(name) &&
          method.hasParameters(parameters) &&
          (!returnType.isPresent() ||
           Objects.equals(returnType.get(), method.getReturnType()))) {
        return Optional.of(method);
      }
    }
    return Optional.empty();
  }

  /**
   * @param returnType of a classname in binary format
   * @param name       of the method to find
   * @param parameters as classnames in binary format
   * @return {@link Optional#empty() empty} or a method with given name and signature
   */
  default Optional<HxMethod> findMethod(String returnType,
                                        String name,
                                        String... parameters) {
    return findMethod(getHaxxor().reference(returnType), name, getHaxxor().references(parameters));
  }

  /**
   * @param returnType of the method to find
   * @param name       of the method to find
   * @param parameters of the method to find
   * @return
   */
  default Optional<HxMethod> findMethod(HxType returnType,
                                        String name,
                                        HxType... parameters) {
    return findMethod(returnType, name, Arrays.asList(parameters));
  }

  /**
   * @param returnType of the method to find
   * @param name       of the method to find
   * @param parameters of the method to find
   * @return
   */
  default Optional<HxMethod> findMethod(HxType returnType,
                                        String name,
                                        List<HxType> parameters) {
    return findMethod(Optional.ofNullable(returnType), name, parameters);
  }

  /**
   * @param returnType of the method to find
   * @param name       of the method to find
   * @param parameters of the method to find
   * @return
   */
  default Optional<HxMethod> findMethod(Class<?> returnType,
                                        String name,
                                        Class<?>... parameters) {
    Hx hx = getHaxxor();
    return findMethod(hx.reference(returnType), name, hx.references(hx.toNormalizedClassnames(parameters)));
  }

  /**
   * @param name       of the method to find
   * @param parameters of the method to find
   * @return
   */
  default Optional<HxMethod> findMethod(String name,
                                        List<HxType> parameters) {
    return findMethod(Optional.empty(), name, parameters);
  }

  /**
   * @param name       of the method to find
   * @param parameters of the method to find
   * @return
   */
  default Optional<HxMethod> findMethod(String name,
                                        HxType... parameters) {
    return findMethod(name, Arrays.asList(parameters));
  }

  /**
   * @param name       of the method to find
   * @param parameters as classnames in binary format
   * @return
   */
  default Optional<HxMethod> findMethod(String name,
                                        String... parameters) {
    return findMethod(name, getHaxxor().references(parameters));
  }

  /**
   * @param name       of the method to find
   * @param parameters of the method to find
   * @return
   */
  default Optional<HxMethod> findMethod(String name,
                                        Class<?>... parameters) {
    return findMethod(name, getHaxxor().references(getHaxxor().toNormalizedClassnames(parameters)));
  }

  /**
   * @param prototype
   * @return
   */
  default Optional<HxMethod> findMethodRecursively(HxMethod prototype) {
    return findMethodRecursively(prototype.getReturnType(),
                                 prototype.getName(),
                                 prototype.getParameterTypes());
  }

  /**
   * @param returnType of the method to find
   * @param name       of the method to find
   * @param parameters of the method to find
   * @return
   */
  default Optional<HxMethod> findMethodRecursively(String returnType,
                                                   String name,
                                                   String... parameters) {
    return findMethodRecursively(
      returnType == null ? null : getHaxxor().reference(returnType),
      name,
      getHaxxor().references(parameters)
    );
  }

  /**
   * @param returnType of the method to find
   * @param name       of the method to find
   * @param parameters of the method to find
   * @return
   */
  default Optional<HxMethod> findMethodRecursively(Class<?> returnType,
                                                   String name,
                                                   Class<?>... parameters) {
    return findMethodRecursively(
      returnType == null ? null : getHaxxor().reference(returnType),
      name,
      getHaxxor().references(parameters)
    );
  }

  /**
   * @param returnType of the method to find
   * @param name       of the method to find
   * @param parameters of the method to find
   * @return
   */
  default Optional<HxMethod> findMethodRecursively(HxType returnType,
                                                   String name,
                                                   HxType... parameters) {
    return findMethodRecursively(returnType, name, Arrays.asList(parameters));
  }

  /**
   * @param returnType of the method to find
   * @param name       of the method to find
   * @param parameters of the method to find
   * @return
   */
  default Optional<HxMethod> findMethodRecursively(HxType returnType,
                                                   String name,
                                                   List<HxType> parameters) {
    return findMethod(returnType, name, parameters);
  }

  /**
   * @return
   */
  default Optional<HxMethod> findDefaultConstructor() {
    return findConstructor(Collections.emptyList());
  }

  /**
   * @return a set with constructors that propagate their invocation to a constructor defined by the super type
   */
  default Collection<HxMethod> findForwardingConstructors() {
    return Collections.emptySet();
  }

  /**
   * @return
   */
  default Optional<HxMethod> findOrCreateDefaultConstructor() {
    return findDefaultConstructor();
  }

  /**
   * @return
   */
  default Optional<HxMethod> findOrCreateClassInitializer() {
    return findClassInitializer();
  }

  /**
   * @return
   */
  default Optional<HxMethod> findClassInitializer() {
    return findMethodDirectly(HxConstants.CLASS_INITIALIZER_METHOD_NAME, "()V");
  }

  /**
   * @param constructor
   * @return
   */
  default boolean hasConstructor(Constructor<?> constructor) {
    return findConstructor(constructor).isPresent();
  }

  /**
   * @param constructor
   * @return
   */
  default boolean hasConstructor(HxMethod constructor) {
    return findConstructor(constructor.getParameterTypes()).isPresent();
  }

  /**
   * @param parameters as classnames in binary format
   * @return
   */
  default boolean hasConstructor(String... parameters) {
    return findConstructor(parameters).isPresent();
  }

  /**
   * @param parameters
   * @return
   */
  default boolean hasConstructor(HxType... parameters) {
    return findConstructor(parameters).isPresent();
  }

  /**
   * @param parameters
   * @return
   */
  default boolean hasConstructor(List<HxType> parameters) {
    return findConstructor(parameters).isPresent();
  }

  /**
   * @param parameters
   * @return
   */
  default boolean hasConstructor(Class<?>... parameters) {
    return findConstructor(parameters).isPresent();
  }

  /**
   * Checks the existence of the prototype method
   *
   * @param method is the prototype to look for
   * @return <b>true</b> if the method exists in this container, <b>false</b> otherwise
   */
  default boolean hasMethod(Method method) {
    return findMethod(method).isPresent();
  }

  /**
   * Checks the existence of the prototype method
   *
   * @param method is the prototype to look for
   * @return <b>true</b> if the method exists in this container, <b>false</b> otherwise
   */
  default boolean hasMethod(HxMethod method) {
    return findMethod(method).isPresent();
  }

  /**
   * Checks the existence of a method with the name
   *
   * @param name of a method
   * @return <b>true</b> if the method exists, <b>false</b> otherwise
   */
  default boolean hasMethod(String name) {
    return findMethod(name).isPresent();
  }

  /**
   * Checks the existence of a method with the return-type, name and parameters
   *
   * @param returnType as a classname in binary format
   * @param name       of a method
   * @param parameters as classnames in binary format
   * @return <b>true</b> if the method exists, <b>false</b> otherwise
   */
  default boolean hasMethod(String returnType,
                            String name,
                            String... parameters) {
    return findMethod(returnType, name, parameters).isPresent();
  }

  /**
   * Checks the existence of a method with the return-type, name and parameters
   *
   * @param returnType of a method
   * @param name       of a method
   * @param parameters of a method
   * @return <b>true</b> if the method exists, <b>false</b> otherwise
   */
  default boolean hasMethod(HxType returnType,
                            String name,
                            HxType... parameters) {
    return findMethod(returnType, name, parameters).isPresent();
  }

  /**
   * Checks the existence of a method with the return-type, name and parameters
   *
   * @param returnType of a method
   * @param name       of a method
   * @param parameters of a method
   * @return <b>true</b> if the method exists, <b>false</b> otherwise
   */
  default boolean hasMethod(HxType returnType,
                            String name,
                            List<HxType> parameters) {
    return findMethod(returnType, name, parameters).isPresent();
  }

  /**
   * Checks the existence of a method with the name and parameters
   *
   * @param name       of a method
   * @param parameters of a method
   * @return <b>true</b> if the method exists, <b>false</b> otherwise
   */
  default boolean hasMethod(Class<?> returnType,
                            String name,
                            Class<?>... parameters) {
    return findMethod(returnType, name, parameters).isPresent();
  }

  /**
   * Checks the existence of a method with the name and parameters
   *
   * @param name       of a method
   * @param parameters of a method
   * @return <b>true</b> if the method exists, <b>false</b> otherwise
   */
  default boolean hasMethod(String name,
                            Class<?>... parameters) {
    return findMethod(name, parameters).isPresent();
  }

  /**
   * Checks the existence of a method with the name and parameters
   *
   * @param name       of a method
   * @param parameters as classnames in binary format
   * @return <b>true</b> if the method exists, <b>false</b> otherwise
   */
  default boolean hasMethod(String name,
                            String... parameters) {
    return findMethod(name, parameters).isPresent();
  }

  /**
   * Checks the existence of a method with the name and parameters
   *
   * @param name       of a method
   * @param parameters of a method
   * @return <b>true</b> if the method exists, <b>false</b> otherwise
   */
  default boolean hasMethod(String name,
                            HxType... parameters) {
    return findMethod(name, parameters).isPresent();
  }

  /**
   * Checks the existence of a method with the name and parameters
   *
   * @param name       of a method
   * @param parameters of a method
   * @return <b>true</b> if the method exists, <b>false</b> otherwise
   */
  default boolean hasMethod(String name,
                            List<HxType> parameters) {
    return findMethod(name, parameters).isPresent();
  }

  /**
   * Checks the existence of a method with the return-type, name and parameters
   *
   * @param returnType as a classname in binary format
   * @param name       of a method
   * @param parameters as classnames in binary format
   * @return <b>true</b> if the method exists, <b>false</b> otherwise
   */
  default boolean hasMethodRecursively(String returnType,
                                       String name,
                                       String... parameters) {
    return findMethodRecursively(returnType, name, parameters).isPresent();
  }

  /**
   * Checks the existence of a method with the return-type, name and parameters
   *
   * @param returnType of a method
   * @param name       of a method
   * @param parameters of a method
   * @return <b>true</b> if the method exists, <b>false</b> otherwise
   */
  default boolean hasMethodRecursively(HxType returnType,
                                       String name,
                                       HxType... parameters) {
    return findMethodRecursively(returnType, name, parameters).isPresent();
  }

  /**
   * Checks the existence of a method with the return-type, name and parameters
   *
   * @param returnType of a method
   * @param name       of a method
   * @param parameters of a method
   * @return <b>true</b> if the method exists, <b>false</b> otherwise
   */
  default boolean hasMethodRecursively(HxType returnType,
                                       String name,
                                       List<HxType> parameters) {
    return findMethodRecursively(returnType, name, parameters).isPresent();
  }

  /**
   * Checks the existence of a method with the name and parameters
   *
   * @param name       of a method
   * @param parameters of a method
   * @return <b>true</b> if the method exists, <b>false</b> otherwise
   */
  default boolean hasMethodRecursively(Class<?> returnType,
                                       String name,
                                       Class<?>... parameters) {
    return findMethodRecursively(returnType, name, parameters).isPresent();
  }

  /**
   * Checks the existence of a method with the name and parameters
   *
   * @param name       of a method
   * @param parameters of a method
   * @return <b>true</b> if the method exists, <b>false</b> otherwise
   */
  default boolean hasMethodRecursively(String name,
                                       Class<?>... parameters) {
    return findMethodRecursively(null, name, parameters).isPresent();
  }

  /**
   * Checks the existence of a method with the name and parameters
   *
   * @param name       of a method
   * @param parameters as classnames in binary format
   * @return <b>true</b> if the method exists, <b>false</b> otherwise
   */
  default boolean hasMethodRecursively(String name,
                                       String... parameters) {
    return findMethodRecursively(null, name, parameters).isPresent();
  }

  /**
   * Checks the existence of a method with the name and parameters
   *
   * @param name       of a method
   * @param parameters of a method
   * @return <b>true</b> if the method exists, <b>false</b> otherwise
   */
  default boolean hasMethodRecursively(String name,
                                       HxType... parameters) {
    return findMethod(name, parameters).isPresent();
  }

  /**
   * Checks the existence of a method with the name and parameters
   *
   * @param name       of a method
   * @param parameters of a method
   * @return <b>true</b> if the method exists, <b>false</b> otherwise
   */
  default boolean hasMethodRecursively(String name,
                                       List<HxType> parameters) {
    return findMethod(name, parameters).isPresent();
  }

  /**
   * @param prototype
   * @return
   */
  default Optional<HxMethod> findOrCreateMethod(HxMethod prototype) {
    return findOrCreateMethod(prototype.getReturnType(), prototype.getName(), prototype.getParameterTypes());
  }

  /**
   * @param prototype
   * @return
   */
  default Optional<HxMethod> findOrCreateConstructor(HxMethod prototype) {
    assert prototype.isConstructor() : "Not a constructor: " + prototype;
    return findOrCreateConstructor(prototype.getParameterTypes());
  }

  /**
   * @param returnType is classname in binary format
   * @param name
   * @param parameters are classnames in binary format
   * @return
   */
  default Optional<HxMethod> findOrCreateMethod(String returnType,
                                                String name,
                                                String... parameters) {
    return findOrCreateMethod(getHaxxor().reference(returnType), name, getHaxxor().references(parameters));
  }

  /**
   * @param returnType
   * @param name
   * @param parameters
   * @return
   */
  default Optional<HxMethod> findOrCreateMethod(Class<?> returnType,
                                                String name,
                                                Class<?>... parameters) {
    return findOrCreateMethod(getHaxxor().reference(returnType), name, getHaxxor().references(parameters));
  }

  /**
   * @param returnType
   * @param name
   * @param parameters
   * @return
   */
  default Optional<HxMethod> findOrCreateMethod(HxType returnType,
                                                String name,
                                                HxType... parameters) {
    return findOrCreateMethod(returnType, name, Arrays.asList(parameters));
  }

  /**
   * @param returnType
   * @param name
   * @param parameters
   * @return
   */
  default Optional<HxMethod> findOrCreateMethod(HxType returnType,
                                                String name,
                                                List<HxType> parameters) {
    Optional<HxMethod> optional = findMethod(returnType, name, parameters);
    if (optional.isPresent()) {
      return optional;
    }
    HxMethod method = getHaxxor().createMethod(returnType, name, parameters.toArray(HxConstants.EMPTY_TYPE_ARRAY));
    addMethod(method);
    return Optional.of(method);
  }

  /**
   * @param parameters as classnames in binary format
   * @return
   */
  default Optional<HxMethod> findOrCreateConstructor(String... parameters) {
    return findOrCreateConstructor(getHaxxor().references(parameters));
  }

  /**
   * @param parameters
   * @return
   */
  default Optional<HxMethod> findOrCreateConstructor(Class<?>... parameters) {
    return findOrCreateConstructor(getHaxxor().references(parameters));
  }

  /**
   * @param parameters
   * @return
   */
  default Optional<HxMethod> findOrCreateConstructor(HxType... parameters) {
    Optional<HxMethod> optional = findMethod(CONSTRUCTOR_METHOD_NAME, parameters);
    if (optional.isPresent()) {
      return optional;
    }
    HxMethod constructor = getHaxxor().createConstructor(parameters);
    addMethod(constructor);
    return Optional.of(constructor);
  }

  /**
   * @param parameters
   * @return
   */
  default Optional<HxMethod> findOrCreateConstructor(List<HxType> parameters) {
    Optional<HxMethod> optional = findMethod(CONSTRUCTOR_METHOD_NAME, parameters);
    if (optional.isPresent()) {
      return optional;
    }
    HxMethod constructor = getHaxxor().createConstructor(parameters.toArray(HxConstants.EMPTY_TYPE_ARRAY));
    addMethod(constructor);
    return Optional.of(constructor);
  }


}
