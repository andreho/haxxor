package net.andreho.haxxor.spec.api;

import net.andreho.asm.org.objectweb.asm.Opcodes;
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
import java.util.Set;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxMethod
  extends HxAnnotated<HxMethod>,
          HxMember<HxMethod>,
          HxOwned<HxMethod>,
          HxGeneric<HxMethod>,
          HxAccessible<HxMethod>,
          HxOrdered,
          HxNamed,
          HxProvider,
          Cloneable {

  enum Modifiers
    implements HxModifier {
    PUBLIC(Opcodes.ACC_PUBLIC),
    // class, field, method
    PRIVATE(Opcodes.ACC_PRIVATE),
    // class, field, method
    PROTECTED(Opcodes.ACC_PROTECTED),
    // class, field, method
    STATIC(Opcodes.ACC_STATIC),
    // field, method
    FINAL(Opcodes.ACC_FINAL),
    // class, field, method, parameter
    SYNCHRONIZED(Opcodes.ACC_SYNCHRONIZED),
    // method
    BRIDGE(Opcodes.ACC_BRIDGE),
    // method
    VARARGS(Opcodes.ACC_VARARGS),
    // method
    NATIVE(Opcodes.ACC_NATIVE),
    // method
    ABSTRACT(Opcodes.ACC_ABSTRACT),
    // class, method
    STRICT(Opcodes.ACC_STRICT),
    // method
    SYNTHETIC(Opcodes.ACC_SYNTHETIC); // class, field, method, parameter

    final int bit;

    Modifiers(int bit) {
      this.bit = bit;
    }

    @Override
    public int toBit() {
      return bit;
    }

    /**
     * Transforms given modifiers to an equal enum-set
     *
     * @param modifiers to transform
     * @return enum-set representation of given modifiers
     */
    public static Set<Modifiers> toSet(int modifiers) {
      return HxModifier.toSet(Modifiers.class, modifiers);
    }
  }

  int BODY_PART = 1;
  int ANNOTATIONS_PART = 2;
  int PARAMETERS_ANNOTATIONS_PART = 4;

  /**
   * @return
   */
  default HxMethod clone() {
    return clone(getName());
  }

  /**
   * @param name of the cloned method
   * @return a cloned version of this method with given name
   */
  HxMethod clone(String name);

  /**
   * @param name of the cloned method
   * @param parts as a bitset for pars to clone; <code>-1</code> for all method's parts
   * @return a cloned version of this method with given name
   * @see #BODY_PART
   * @see #ANNOTATIONS_PART
   * @see #PARAMETERS_ANNOTATIONS_PART
   */
  HxMethod clone(String name, int parts);

  /**
   * @return name of this method
   */
  String getName();

  /**
   * @param name
   * @return
   */
  default boolean hasName(String name) {
    return Objects.equals(name, getName());
  }

  /**
   * @param returnType
   * @return
   */
  default boolean hasReturnType(String returnType) {
    if (getReturnType() == null) {
      return returnType == null;
    }
    return getReturnType().getName()
                          .equals(returnType);
  }

  /**
   * @param returnType
   * @return
   */
  default boolean hasReturnType(HxType returnType) {
    return hasReturnType(Objects.requireNonNull(returnType).getName());
  }

  /**
   * @return the return value of this method
   */
  HxType getReturnType();

  /**
   * @param returnType
   * @return
   * @implSpec return value can't be null
   */
  default HxMethod setReturnType(String returnType) {
    return setReturnType(getHaxxor().reference(returnType));
  }

  /**
   * @param returnType
   * @return
   * @implSpec return value can't be null
   */
  HxMethod setReturnType(HxType returnType);

  /**
   * @return <b>-1</b> if this executable element wasn't found or not bound to any type,
   * otherwise the zero-based position of this executable element in the corresponding collection of declaring type
   */
  @Override
  int getIndex();

  /**
   * @return <b>true</b> if this executable defines some code, <b>false</b> otherwise.
   */
  boolean hasBody();

  /**
   * @return code of this method or constructor
   */
  HxMethodBody getBody();

  /**
   * Allows to replace or set methods's body to given one
   * @param methodBody to used
   * @return
   */
  HxMethod setBody(HxMethodBody methodBody);

  /**
   * @return <b>true</b> if this method is a constructor, <b>false</b> otherwise.
   */
  default boolean isConstructor() {
    return "<init>".equals(getName());
  }

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
  default boolean hasParametersCount(int min,
                                     int max) {
    return min <= getParametersCount() && getParametersCount() <= max;
  }

  /**
   * @return count of expected parameters
   */
  default int getParametersCount() {
    return getParameters().size();
  }

  /**
   * @return number of slots on local variable table to hold this parameters' list;
   * <b>this</b> for member methods isn't included.
   */
  default int getParametersSlots() {
    int slots = 0;
    for (HxParameter parameter : getParameters()) {
      slots += parameter.getType().getSlotsCount();
    }
    return slots;
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
  List<HxParameter> getParameters();

  /**
   * @param parameter
   * @return
   */
  default HxMethod addParameter(HxParameter parameter) {
    return addParameterAt(getParametersCount(), parameter);
  }

  /**
   * @param parameter
   * @return
   */
  HxMethod addParameterAt(int index,
                   HxParameter parameter);

  /**
   * @param index of wanted formal parameter
   * @return a formal parameter at the given index
   */
  HxParameter getParameterAt(int index);

  /**
   * @param index     of parameter to replace
   * @param parameter new parameter at the given index
   * @return this
   */
  HxMethod setParameterAt(int index,
                   HxParameter parameter);

  /**
   * @param type
   * @return
   */
  default HxMethod addParameterType(HxType type) {
    Objects.requireNonNull(type, "Parameter's type can't be null.");
    return addParameter(new HxParameterImpl(type));
  }

  /**
   * @param parameters
   * @return this
   */
  default HxMethod setParameters(HxParameter... parameters) {
    return setParameters(Arrays.asList(parameters));
  }

  /**
   * @param parameters
   * @return this
   */
  HxMethod setParameters(List<HxParameter> parameters);

  /**
   * @param types
   * @return this
   */
  default HxMethod setParameterTypes(HxType... types) {
    return setParameterTypes(Arrays.asList(types));
  }

  /**
   * @param types
   * @return this
   */
  default HxMethod setParameterTypes(List<HxType> types) {
    final List<HxParameter> parameters = new ArrayList<>(types.size());
    for (HxType type : types) {
      parameters.add(new HxParameterImpl(type));
    }
    return setParameters(parameters);
  }

  /**
   * @param exceptionTypes exception types that are declared by a <code>throws</code> declaration
   * @return this
   */
  default HxMethod setExceptionTypes(HxType... exceptionTypes) {
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
  HxMethod setExceptionTypes(List<HxType> exceptionTypes);

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
    for (HxType type : getExceptionTypes()) {
      if (type.hasName(exceptionClassname)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @return a collection with overridden methods or constructors
   */
  default Collection<HxMethod> getOverriddenMembers() {
    return Collections.emptySet();
  }

  /**
   * @return
   */
  default boolean isVarArg() {
    return hasModifiers(HxMethod.Modifiers.VARARGS);
  }

  /**
   * @return
   */
  default boolean isSynthetic() {
    return hasModifiers(HxMethod.Modifiers.SYNTHETIC);
  }

  /**
   * @return
   */
  default boolean isPublic() {
    return hasModifiers(HxMethod.Modifiers.PUBLIC);
  }

  /**
   * @return
   */
  default boolean isPrivate() {
    return hasModifiers(HxMethod.Modifiers.PRIVATE);
  }

  /**
   * @return
   */
  default boolean isProtected() {
    return hasModifiers(HxMethod.Modifiers.PROTECTED);
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

  @Override
  default HxType getDeclaringType() {
    return getDeclaringMember();
  }

  /**
   * @param builder to use for printing a method descriptor
   * @return given builder instance containing a method descriptor of this method
   */
  default Appendable toDescriptor(Appendable builder) {
    try {
      builder.append('(');

      for (int i = 0, len = getParametersCount(); i < len; i++) {
        getParameterTypeAt(i).toDescriptor(builder);
      }

      builder.append(')');
      getReturnType().toDescriptor(builder);

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

  /**
   * @return default value of this annotation attribute
   */
  Object getDefaultValue();

  /**
   * @param value is a new default value for this annotation attribute
   * @return this
   */
  HxMethod setDefaultValue(Object value);

  /**
   * @return <b>true</b> if this method was declared by an annotation, represents an annotation's attribute and has a
   * default value, <b>false</b> otherwise.
   */
  default boolean hasDefaultValue() {
    return isAnnotationAttribute() && getDefaultValue() != null;
  }

  /**
   * @return
   */
  default HxGenericElement getGenericReturnType() {
    return getReturnType();
  }

  /**
   * @return <b>true</b> if this method is an annotation's attribute, <b>false</b> otherwise.
   */
  default boolean isAnnotationAttribute() {
    HxType type = getDeclaringMember();
    if (type == null || !type.isAnnotation()) {
      return false;
    }
    return
      isPublic() &&
      isAbstract() &&
      getParametersCount() == 0 &&
      !hasReturnType("void") &&
      !"hashCode".equals(getName()) &&
      !"toString".equals(getName()) &&
      !"annotationType".equals(getName());
  }

  /**
   * @return
   */
  default boolean isStatic() {
    return hasModifiers(Modifiers.STATIC);
  }

  /**
   * @return
   */
  default boolean isAbstract() {
    return hasModifiers(Modifiers.ABSTRACT);
  }

  /**
   * @return
   */
  default boolean isSynchronized() {
    return hasModifiers(Modifiers.SYNCHRONIZED);
  }

  /**
   * @return
   */
  default boolean isBrindge() {
    return hasModifiers(Modifiers.BRIDGE);
  }

  /**
   * @return
   */
  default boolean isNative() {
    return hasModifiers(Modifiers.NATIVE);
  }

  /**
   * @return
   */
  default boolean isFinal() {
    return hasModifiers(Modifiers.FINAL);
  }

  /**
   * @param annotationType
   * @return
   */
  default boolean hasParameterWithAnnotation(String annotationType) {
    for(HxParameter parameter : getParameters()) {
      if(parameter.isAnnotationPresent(annotationType)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param annotationType
   * @return
   */
  default boolean hasParameterWithAnnotation(HxType annotationType) {
    return hasParameterWithAnnotation(annotationType.getName());
  }

  /**
   * @param annotationType
   * @return
   */
  default boolean hasParameterWithAnnotation(Class<?> annotationType) {
    return hasParameterWithAnnotation(annotationType.getName());
  }
}
