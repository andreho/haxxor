package net.andreho.haxxor.spec.api;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.misc.MappedList;
import net.andreho.haxxor.spec.impl.HxParameterImpl;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

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

  int ALLOWED_MODIFIERS =
    Opcodes.ACC_PUBLIC | Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL |
    Opcodes.ACC_SYNCHRONIZED | Opcodes.ACC_BRIDGE | Opcodes.ACC_VARARGS | Opcodes.ACC_NATIVE | Opcodes.ACC_ABSTRACT |
    Opcodes.ACC_STRICT | Opcodes.ACC_SYNTHETIC;

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

    /**
     * Transforms given modifiers to an equal enum-set
     *
     * @param modifiers to transform
     * @return enum-set representation of given modifiers
     */
    public static Set<Modifiers> toSet(int modifiers) {
      return HxModifier.toSet(Modifiers.class, modifiers);
    }

    /**
     * @param bit of represented modifier
     */
    Modifiers(int bit) {
      this.bit = bit;
    }

    @Override
    public int toBit() {
      return bit;
    }
  }

  /**
   * @return
   */
  default HxMethod clone() {
    return clone(getName());
  }

  /**
   * @param parts to copy
   * @return
   * @see CloneableParts#ALL_PARTS
   * @see CloneableParts#BODY_PART
   * @see CloneableParts#ANNOTATIONS_PART
   * @see CloneableParts#PARAMETERS_ANNOTATIONS_PART
   */
  default HxMethod clone(int parts) {
    return clone(getName(), parts);
  }

  /**
   * @param name of the cloned method
   * @return a cloned version of this method with given name
   */
  HxMethod clone(String name);

  /**
   * @param name  of the cloned method
   * @param parts as a bitset for pars to clone; <code>{@link CloneableParts#ALL_PARTS}</code> for all method's parts
   * @return a cloned version of this method with given name
   * @see CloneableParts#ALL_PARTS
   * @see CloneableParts#BODY_PART
   * @see CloneableParts#ANNOTATIONS_PART
   * @see CloneableParts#PARAMETERS_ANNOTATIONS_PART
   */
  HxMethod clone(String name,
                 int parts);

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
   * @return <b>true</b> if this method return any value and in other words this means a not <code>void</code> method
   */
  default boolean hasReturnType() {
    return !hasReturnType(HxSort.VOID);
  }

  /**
   * @param returnClassname to match against
   * @return <b>true</b> if this method returns the given type
   */
  default boolean hasReturnType(String returnClassname) {
    if (getReturnType() == null) {
      return returnClassname == null;
    }
    return getReturnType().hasName(returnClassname);
  }

  /**
   * @param returnType to match against
   * @return <b>true</b> if this method returns the given type
   */
  default boolean hasReturnType(HxType returnType) {
    return hasReturnType(Objects.requireNonNull(returnType).getName());
  }

  /**
   * @param cls to match against
   * @return <b>true</b> if this method returns the given type
   */
  default boolean hasReturnType(Class<?> cls) {
    return getReturnType().hasName(cls.getName());
  }

  /**
   * @param sort to match against
   * @return <b>true</b> if this method returns the given type sort
   */
  default boolean hasReturnType(HxSort sort) {
    return getReturnType().getSort() == sort;
  }

  /**
   * @return the return value of this method
   */
  HxType getReturnType();

  /**
   * @param returnType of this method
   * @return this
   * @implSpec return value can't be null
   */
  default HxMethod setReturnType(String returnType) {
    return setReturnType(getHaxxor().reference(returnType));
  }

  /**
   * @param returnType of this method
   * @return this
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
   *
   * @param methodBody to used
   * @return
   */
  HxMethod setBody(HxMethodBody methodBody);

  /**
   * @return <b>true</b> if this method is a constructor, <b>false</b> otherwise.
   */
  default boolean isConstructor() {
    return HxConstants.CONSTRUCTOR_METHOD_NAME.equals(getName());
  }

  /**
   * @return <b>true</b> if this method is a static class-initializer, <b>false</b> otherwise.
   */
  default boolean isClassInitializer() {
    return isStatic() &&
           HxConstants.CLASS_INITIALIZER_METHOD_NAME.equals(getName()) &&
           hasDescriptor("()V");
  }

  /**
   * @param index of parameter to check
   * @return
   */
  default boolean hasParameterAt(int index) {
    return index >= 0 && index < getParametersCount();
  }

  /**
   * @param count of parameters
   * @return <b>true</b> this method has given parameters' count, <b>false</b> otherwise
   */
  default boolean hasParametersCount(int count) {
    return getParametersCount() == count;
  }

  /**
   * @param min count of parameters
   * @param max count of parameters (incl.)
   * @return <b>true</b> if count of parameters of this method lies in the given range, <b>false</b> otherwise
   */
  default boolean hasParametersCountInRange(int min,
                                            int max) {
    return min <= max &&
           min <= getParametersCount() &&
           getParametersCount() <= max;
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
  default int getParametersSlotSize() {
    int slots = 0;
    for (HxParameter parameter : getParameters()) {
      slots += parameter.getType().getSlotSize();
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
   * @param types of new parameters of this method
   * @return this
   */
  default HxMethod setParameterTypes(HxType... types) {
    return setParameterTypes(Arrays.asList(types));
  }

  /**
   * @param types of new parameters of this method
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
   * @return the list with all parameters
   */
  List<HxParameter> getParameters();

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
   * @param index
   * @return
   */
  default int getParametersSlotAt(int index) {
    if (index < 0 || index >= getParametersCount()) {
      throw new IndexOutOfBoundsException("Invalid index: " + index + ", method's arity is: " + getParametersCount());
    }
    int slot = isStatic() ? 0 : 1;
    for (int i = 0; i <= index; i++) {
      slot += getParameterAt(i).getType().getSlotSize();
    }
    return slot;
  }

  /**
   * @param type
   * @return
   */
  default HxMethod addParameterType(HxType type) {
    Objects.requireNonNull(type, "Parameter's type can't be null.");
    return addParameter(new HxParameterImpl(type));
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
  default HxMethod makeSynthetic() {
    return addModifier(Modifiers.SYNTHETIC);
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
  default HxMethod makePublic() {
    return makeInternal().addModifier(Modifiers.PUBLIC);
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
  default HxMethod makePrivate() {
    return makeInternal().addModifier(Modifiers.PRIVATE);
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
  default HxMethod makeProtected() {
    return makeInternal().addModifier(Modifiers.PROTECTED);
  }

  /**
   * @return
   */
  default boolean isInternal() {
    return !isPublic() && !isProtected() && !isPrivate();
  }

  /**
   * @return
   */
  default HxMethod makeInternal() {
    return setModifiers((~(Opcodes.ACC_PUBLIC | Opcodes.ACC_PROTECTED | Opcodes.ACC_PRIVATE)) & getModifiers());
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
  default HxMethod makeStatic() {
    return setModifiers((~(Opcodes.ACC_ABSTRACT | Opcodes.ACC_FINAL)) & getModifiers())
      .addModifier(Modifiers.STATIC);
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
  default HxMethod makeAbstract() {
    return setModifiers((~(Opcodes.ACC_STATIC | Opcodes.ACC_FINAL)) & getModifiers())
      .addModifier(Modifiers.ABSTRACT);
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
  default HxMethod makeSynchronized() {
    return addModifier(Modifiers.SYNCHRONIZED);
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
  default HxMethod makeBridge() {
    return addModifier(Modifiers.BRIDGE);
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
  default HxMethod makeNative() {
    return addModifier(Modifiers.NATIVE);
  }

  /**
   * @return
   */
  default boolean isFinal() {
    return hasModifiers(Modifiers.FINAL);
  }

  /**
   * @return
   */
  default HxMethod makeFinal() {
    return setModifiers((~(Opcodes.ACC_STATIC | Opcodes.ACC_ABSTRACT)) & getModifiers())
      .addModifier(Modifiers.FINAL);
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
   * @param descriptor to match
   * @return <b>true</b> if this method has given descriptor, <b>false</b> otherwise
   */
  boolean hasDescriptor(String descriptor);

  /**
   * @return default value of this annotation's attribute
   */
  Object getDefaultValue();

  /**
   * @param value is a new default value for this annotation's attribute
   * @return this
   */
  HxMethod setDefaultValue(Object value);

  /**
   * @return <b>true</b> if this method was declared by an annotation and represents an annotation's attribute and has a
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
      hasReturnType() &&
      !"hashCode".equals(getName()) &&
      !"toString".equals(getName()) &&
      !"annotationType".equals(getName());
  }

  /**
   * @param index     of a parameter
   * @param classname
   * @return
   */
  default boolean hasParameterWithTypeAt(int index,
                                         String classname) {
    return hasParametersCountInRange(index + 1, Integer.MAX_VALUE) &&
           getParameterTypeAt(index).hasName(classname);
  }

  /**
   * @param index
   * @param type
   * @return
   */
  default boolean hasParameterWithTypeAt(int index,
                                         HxType type) {
    return hasParameterWithTypeAt(index, type.getName());
  }

  /**
   * @param index
   * @param cls
   * @return
   */
  default boolean hasParameterWithTypeAt(int index,
                                         Class<?> cls) {
    return hasParameterWithTypeAt(index, cls.getName());
  }

  /**
   * @param annotationType
   * @return
   */
  default boolean hasParameterWithAnnotation(String annotationType) {
    for (HxParameter parameter : getParameters()) {
      if (parameter.isAnnotationPresent(annotationType)) {
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

  /**
   * @param annotationType to search for
   * @return a list with all defined parameters that have given annotation
   */
  default List<HxParameter> findParametersWithAnnotation(String annotationType) {
    final List<HxParameter> result = new ArrayList<>();
    for (HxParameter parameter : getParameters()) {
      if (parameter.isAnnotationPresent(annotationType)) {
        result.add(parameter);
      }
    }
    return result;
  }

  /**
   * @param annotationType
   * @return
   */
  default List<HxParameter> findParametersWithAnnotation(HxType annotationType) {
    return findParametersWithAnnotation(annotationType.getName());
  }

  /**
   * @param annotationType
   * @return
   */
  default List<HxParameter> findParametersWithAnnotation(Class<? extends Annotation> annotationType) {
    return findParametersWithAnnotation(annotationType.getName());
  }

  /**
   * @param condition to test on each parameter
   * @return <b>true</b> if there is at least one parameter satisfying the given condition, <b>false</b> otherwise
   */
  default boolean hasParameterWith(Predicate<HxParameter> condition) {
    for (HxParameter parameter : getParameters()) {
      if (condition.test(parameter)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param condition to test on each parameter
   * @return a list with parameters that satisfies given condition
   */
  default List<HxParameter> findParametersWith(Predicate<HxParameter> condition) {
    final List<HxParameter> result = new ArrayList<>();
    for (HxParameter parameter : getParameters()) {
      if (condition.test(parameter)) {
        result.add(parameter);
      }
    }
    return result;
  }

  /**
   * This class defines a set of constants for {@link #clone(int)} method
   */
  abstract class CloneableParts {

    public static final int ALL_PARTS = -1;
    public static final int BODY_PART = 1;
    public static final int ANNOTATIONS_PART = 2;
    public static final int PARAMETERS_ANNOTATIONS_PART = 4;

    private CloneableParts() {
    }
  }
}
