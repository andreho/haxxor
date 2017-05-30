package net.andreho.haxxor.spec;


import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.impl.HxParameterImpl;

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
       extends HxAnnotated<P>, HxMember<P>, HxOwned<P> {
   /**
    * @return owning haxxor instance
    */
   Haxxor getHaxxor();

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
      return getParameters().get(index).getType();
   }

   /**
    * @return the list with all parameters
    */
   List<HxParameter> getParameters();

   /**
    * @param type
    * @return
    */
   default P addParameter(HxType type) {
      final int idx = getParameters().size();
      getParameters().add(new HxParameterImpl(this, idx, type));
      return (P) this;
   }

   /**
    * @param parameters
    * @return this
    */
   default P setParameters(HxParameter ... parameters) {
      return setParameters(Arrays.stream(parameters).collect(Collectors.toList()));
   }

   /**
    * @param parameters
    * @return this
    */
   P setParameters(List<HxParameter> parameters);

   /**
    * @param types
    * @return this
    */
   default P setParameterTypes(HxType ... types) {
      final List<HxParameter> parameters = new ArrayList<>(types.length);
      for (int i = 0; i < types.length; i++) {
         final HxType type = types[i];
         parameters.add(new HxParameterImpl(this, i, type));
      }
      return setParameters(parameters);
   }

   /**
    * @param types
    * @return this
    */
   default P setParameterTypes(List<HxType> types) {
      final List<HxParameter> parameters = new ArrayList<>(types.size());
      for (int i = 0; i < types.size(); i++) {
         final HxType type = types.get(i);
         parameters.add(new HxParameterImpl(this, i).setType(type));
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
   default P setExceptionTypes(HxType ... exceptionTypes) {
      return setExceptionTypes(Arrays.stream(exceptionTypes).collect(Collectors.toList()));
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
   HxParameter getParameterAt(int index);

   /**
    * @param index of parameter to modify
    * @param parameter new parameter at the given index
    * @return this
    */
   P setParameterAt(int index, HxParameter parameter);

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
   default StringBuilder toDescriptor(StringBuilder builder) {
      builder.append('(');

      for (int i = 0, len = getArity(); i<len; i++) {
         final HxType type = getParameterTypeAt(i);
         type.toDescriptor(builder);
      }

      builder.append(')');

      if (this instanceof HxMethod) {
         ((HxMethod) this).getReturnType().toDescriptor(builder);
      } else {
         builder.append('V');
      }

      return builder;
   }
}
