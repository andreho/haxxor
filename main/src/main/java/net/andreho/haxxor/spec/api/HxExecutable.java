package net.andreho.haxxor.spec.api;


import net.andreho.haxxor.Utils;
import net.andreho.haxxor.misc.MappedList;
import net.andreho.haxxor.spec.impl.HxParameterImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxExecutable<E extends HxMember<E> & HxExecutable<E> & HxOwned<E> & HxGeneric<E>>
    extends HxAnnotated<E>,
            HxMember<E>,
            HxOwned<E>,
            HxGeneric<E>,
            HxOrdered,
            HxNamed,
            HxProvider {


  /**
   * @return <b>-1</b> if this executable element wasn't found or not bound to any type,
   * otherwise the zero-based position of this executable element in the corresponding collection of declaring type
   */
  @Override
  int getIndex();

  /**
   * @return <b>true</b> if this executable defines some code, <b>false</b> otherwise.
   */
  boolean hasCode();

  /**
   * @return code of this method or constructor
   */
  HxCode getCode();

  /**
   * @param index
   * @return
   */
  default boolean hasParameterAt(int index) {
    return index >= 0 && index < getParametersCount();
  }

  /**
   * @param count
   * @return
   */
  default boolean hasParametersCount(int count) {
    return hasParametersCount(count, count);
  }

  /**
   * @param min
   * @param max
   * @return
   */
  default boolean hasParametersCount(int min, int max) {
    return min <= getParametersCount() && getParametersCount() <= max;
  }

  /**
   * @return count of expected parameters
   */
  default int getParametersCount() {
    return getParameters().size();
  }

  /**
   * @param index of a parameter to take
   * @return a parameter at the given index
   */
  default HxType getParameterTypeAt(int index) {
    return getParameters().get(index)
                          .getType();
  }

  /**
   * @return a list-view of associated parameter's list
   */
  default List<HxType> getParameterTypes() {
    return MappedList.create(getParameters(), HxParameter::getType);
  }

  /**
   * @return the list with all parameters
   */
  List<HxParameter<E>> getParameters();

  /**
   * @param parameter
   * @return
   */
  default E addParameter(HxParameter<E> parameter) {
    return addParameterAt(getParametersCount(), parameter);
  }

  /**
   * @param parameter
   * @return
   */
  E addParameterAt(int index,
                   HxParameter<E> parameter);

  /**
   * @param index of wanted formal parameter
   * @return a formal parameter at the given index
   */
  HxParameter<E> getParameterAt(int index);

  /**
   * @param index     of parameter to replace
   * @param parameter new parameter at the given index
   * @return this
   */
  E setParameterAt(int index,
                   HxParameter<E> parameter);

  /**
   * @param type
   * @return
   */
  default E addParameterType(HxType type) {
    Objects.requireNonNull(type, "Parameter's type can't be null.");
    return addParameter(new HxParameterImpl<>(type));
  }

  /**
   * @param parameters
   * @return this
   */
  default E setParameters(HxParameter<E>... parameters) {
    return setParameters(Arrays.asList(parameters));
  }

  /**
   * @param parameters
   * @return this
   */
  E setParameters(List<HxParameter<E>> parameters);

  /**
   * @param types
   * @return this
   */
  default E setParameterTypes(HxType... types) {
    return setParameterTypes(Arrays.asList(types));
  }

  /**
   * @param types
   * @return this
   */
  default E setParameterTypes(List<HxType> types) {
    final List<HxParameter<E>> parameters = new ArrayList<>(types.size());
    for (HxType type : types) {
      parameters.add(new HxParameterImpl<>(type));
    }
    return setParameters(parameters);
  }

  /**
   * @param exceptionTypes exception types that are declared by a <code>throws</code> declaration
   * @return this
   */
  default E setExceptionTypes(HxType... exceptionTypes) {
    return setExceptionTypes(Arrays.asList(exceptionTypes));
  }

  /**
   * @return exception types that are declared by a <code>throws</code> declaration
   */
  List<HxType> getExceptionTypes();

  /**
   * @param exceptionTypes exception types that are declared by a <code>throws</code> declaration
   * @return this
   */
  E setExceptionTypes(List<HxType> exceptionTypes);

  /**
   * @return
   */
  default boolean hasCheckedExceptions() {
    return !getExceptionTypes().isEmpty();
  }

  /**
   * @param exceptionType
   * @return
   */
  default boolean hasCheckedException(HxType exceptionType) {
    return hasCheckedException(exceptionType.getName());
  }

  /**
   * @param exceptionClassname
   * @return
   */
  default boolean hasCheckedException(String exceptionClassname) {
    for(HxType type : getExceptionTypes()) {
      if(exceptionClassname.equals(type.getName())) {
        return true;
      }
    }
    return false;
  }

  /**
   * @return a collection with overridden methods or constructors
   */
  default Collection<HxExecutable> getOverriddenMembers() {
    return Collections.emptySet();
  }

  /**
   * @return
   */
  default boolean isVarArg() {
    return hasModifiers(HxConstructor.Modifiers.VARARGS);
  }

  /**
   * @return
   */
  default boolean isSynthetic() {
    return hasModifiers(HxConstructor.Modifiers.SYNTHETIC);
  }

  /**
   * @return
   */
  default boolean isPublic() {
    return hasModifiers(HxConstructor.Modifiers.PUBLIC);
  }

  /**
   * @return
   */
  default boolean isPrivate() {
    return hasModifiers(HxConstructor.Modifiers.PRIVATE);
  }

  /**
   * @return
   */
  default boolean isProtected() {
    return hasModifiers(HxConstructor.Modifiers.PROTECTED);
  }

  /**
   * @return
   */
  default boolean isInternal() {
    return !isPublic() && !isProtected() && !isPrivate();
  }

  /**
   * @return a method descriptor of this method
   */
  default String toDescriptor() {
    return toDescriptor(new StringBuilder()).toString();
  }

  @Override
  HxType getDeclaringMember();

  /**
   * @param builder to use for printing a method descriptor
   * @return given builder instance containing a method descriptor of this method
   */
  default Appendable toDescriptor(Appendable builder) {
    try {
      builder.append('(');

      for (int i = 0, len = getParametersCount(); i < len; i++) {
        final HxType type = getParameterTypeAt(i);
        type.toDescriptor(builder);
      }

      builder.append(')');

      if (this instanceof HxMethod) {
        ((HxMethod) this).getReturnType()
                         .toDescriptor(builder);
      } else {
        builder.append('V');
      }
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }

    return builder;
  }

  /**
   * @param descriptor
   * @return
   */
  default boolean hasDescriptor(String descriptor) {
    return Utils.hasDescriptor(this, descriptor);
  }
}
