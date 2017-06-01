package net.andreho.haxxor.spec.api;


import net.andreho.haxxor.spec.impl.HxParameterImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxParameterizable<P extends HxMember<P> & HxParameterizable<P> & HxOwned<P>>
    extends HxAnnotated<P>,
            HxMember<P>,
            HxOwned<P>,
            HxProvider {

  /**
   * @return <b>true</b> if this executable defines some code, <b>false</b> otherwise.
   */
  boolean hasCode();

  /**
   * @return code of this method or constructor
   */
  HxCode getCode();

  /**
   * @return length of expected arguments
   */
  default int getArity() {
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
   * @return the list with all parameters
   */
  List<HxParameter<P>> getParameters();

  /**
   * @param parameter
   * @return
   */
  P addParameter(HxParameter<P> parameter);

  /**
   * @param type
   * @return
   */
  default P addParameterType(HxType type) {
    return addParameter(new HxParameterImpl<>(this, type));
  }

  /**
   * @param parameters
   * @return this
   */
  default P setParameters(HxParameter<P>... parameters) {
    return setParameters(Arrays.stream(parameters)
                               .collect(Collectors.toList()));
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
    final List<HxParameter<P>> parameters = new ArrayList<>(types.length);
    for (HxType type : types) {
      parameters.add(new HxParameterImpl<>(this, type));
    }
    return setParameters(parameters);
  }

  /**
   * @param types
   * @return this
   */
  default P setParameterTypes(List<HxType> types) {
    final List<HxParameter<P>> parameters = new ArrayList<>(types.size());
    for (HxType type : types) {
      parameters.add(new HxParameterImpl<>(this, type));
    }
    return setParameters(parameters);
  }

  /**
   * @return exception types that are declared by a <code>throws</code> declaration
   */
  List<HxType> getExceptionTypes();

  /**
   * @param exceptionTypes exception types that are declared by a <code>throws</code> declaration
   * @return this
   */
  default P setExceptionTypes(HxType... exceptionTypes) {
    return setExceptionTypes(Arrays.stream(exceptionTypes)
                                   .collect(Collectors.toList()));
  }

  /**
   * @param exceptionTypes exception types that are declared by a <code>throws</code> declaration
   * @return this
   */
  P setExceptionTypes(List<HxType> exceptionTypes);

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
  P setParameterAt(int index, HxParameter<P> parameter);

  /**
   * @param index     of parameter to move ahead by one
   * @param parameter new parameter at the given index
   * @return this
   */
  P addParameterAt(int index, HxParameter<P> parameter);


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

      for (int i = 0, len = getArity(); i < len; i++) {
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
}
