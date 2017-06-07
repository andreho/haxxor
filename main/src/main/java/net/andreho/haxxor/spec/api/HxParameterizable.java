package net.andreho.haxxor.spec.api;


import net.andreho.haxxor.Utils;
import net.andreho.haxxor.spec.impl.HxParameterImpl;
import net.andreho.haxxor.spec.impl.misc.MappedList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxParameterizable<P extends HxMember<P> & HxParameterizable<P> & HxOwned<P>>
    extends HxAnnotated<P>,
            HxMember<P>,
            HxOwned<P>,
            HxIndexed,
            HxProvider {


  /**
   * @return <b>-1</b> if this parameterizable element wasn't found or not bound to any type,
   * otherwise the zero-based position of this parameterizable element in the corresponding collection of declaring type
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
  List<HxParameter<P>> getParameters();

  /**
   * @param parameter
   * @return
   */
  default P addParameter(HxParameter<P> parameter) {
    return addParameterAt(getParametersCount(), parameter);
  }

  /**
   * @param parameter
   * @return
   */
  P addParameterAt(int index,
                   HxParameter<P> parameter);

  /**
   * @param index of wanted formal parameter
   * @return a formal parameter at the given index
   */
  HxParameter<P> getParameterAt(int index);

  /**
   * @param index     of parameter to replace
   * @param parameter new parameter at the given index
   * @return this
   */
  P setParameterAt(int index,
                   HxParameter<P> parameter);

  /**
   * @param type
   * @return
   */
  default P addParameterType(HxType type) {
    Objects.requireNonNull(type, "Parameter's type can't be null.");
    return addParameter(new HxParameterImpl<>(type));
  }

  /**
   * @param parameters
   * @return this
   */
  default P setParameters(HxParameter<P>... parameters) {
    return setParameters(Arrays.asList(parameters));
  }

  /**
   * @param parameters
   * @return this
   */
  P setParameters(List<HxParameter<P>> parameters);

  /**
   * @param types
   * @return this
   */
  default P setParameterTypes(HxType... types) {
    return setParameterTypes(Arrays.asList(types));
  }

  /**
   * @param types
   * @return this
   */
  default P setParameterTypes(List<HxType> types) {
    final List<HxParameter<P>> parameters = new ArrayList<>(types.size());
    for (HxType type : types) {
      parameters.add(new HxParameterImpl<>(type));
    }
    return setParameters(parameters);
  }

  /**
   * @param exceptionTypes exception types that are declared by a <code>throws</code> declaration
   * @return this
   */
  default P setExceptionTypes(HxType... exceptionTypes) {
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
  P setExceptionTypes(List<HxType> exceptionTypes);


  /**
   * @return a collection with overridden methods or constructors
   */
  default Collection<HxParameterizable> getOverriddenMembers() {
    return Collections.emptySet();
  }

  /**
   * @return <b>true</b> if this parameterizable instance has some generic information
   */
  default boolean isGeneric() {
    return getGenericSignature() != null;
  }

  /**
   * @return raw generic signature of this element
   */
  default String getGenericSignature() {
    return null;
  }

  /**
   * @param genericSignature new raw generic signature
   * @return current generic signature of this method as whole including parameters and return type
   */
  default P setGenericSignature(String genericSignature) {
    //NO OP
    return (P) this;
  }

  /**
   * @return
   * @implSpec
   */
  default List<HxGeneric> getGenericParameterTypes() {
    if (!getGenericSignature().isEmpty()) {
      //create generic definitions for each parameter
    }
    return getParameters().stream()
                          .map(HxParameter::getType)
                          .collect(Collectors.toList());
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
