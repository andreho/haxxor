package net.andreho.haxxor.api;

import java.util.Objects;
import java.util.Set;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxParameter
  extends HxAnnotated<HxParameter>,
          HxMember<HxParameter>,
          HxOwned<HxParameter>,
          HxNamed,
          HxTyped,
          HxOrdered,
          HxProvider,
          Cloneable {

  /**
   *
   */
  enum Modifiers
    implements HxModifier {
    FINAL(0x0010),
    SYNTHETIC(0x1000),
    MANDATED(0x8000);

    final int bit;

    /**
     * Transforms given modifiers to an equal enum-set
     *
     * @param modifiers to transform
     * @return enum-set representation of given parameter's modifiers
     */
    public static Set<Modifiers> toSet(int modifiers) {
      return HxModifier.toSet(Modifiers.class, modifiers);
    }

    Modifiers(int bit) {
      this.bit = bit;
    }

    @Override
    public int toBit() {
      return bit;
    }
  }

  /**
   * @return a new copy of this parameter
   */
  HxParameter clone();

  /**
   * @param name of the cloned parameter
   * @return a new copy of this parameter
   */
  HxParameter clone(String name);

  /**
   * @param name            of the cloned parameter
   * @param withAnnotations whether to copy annotations or not
   * @return a new copy of this parameter
   */
  HxParameter clone(String name,
                    boolean withAnnotations);

  /**
   * @return zero-based index of this parameter in the parameter list of the owning method/constructor
   */
  int getIndex();

  /**
   * @return name of this parameter if any or 'arg'+{@link #getIndex()} of this parameter
   */
  String getName();

  /**
   * @param name of this parameter
   * @return this
   */
  HxParameter setName(String name);

  /**
   * @return type of this parameter
   */
  HxType getType();

  /**
   * @param type of this parameter
   */
  HxParameter setType(HxType type);

  /**
   * @return index of the corresponding slot in the locals
   */
  int getSlotIndex();

  /**
   * @return <b>true</b> if the parameter has a name according to the class file, <b>false</b> otherwise.
   */
  boolean isNamePresent();

  /**
   * @return <b>true</b> if this parameter represents a variable argument list, <b>false</b> otherwise.
   */
  boolean isVarArgs();

  /**
   * @return <b>true</b> if this parameter is implicitly declared in source code, <b>false</b> otherwise.
   */
  boolean isImplicit();

  /**
   * @return <b>true</b> if this parameter is neither implicitly nor explicitly declared in source code,
   * <b>false</b> otherwise.
   */
  boolean isSynthetic();

  /**
   * @return owning constructor or method instance
   */
  @Override
  HxMethod getDeclaringMember();

  @Override
  HxParameter setModifiers(int modifiers);

  /**
   * @param name
   * @return
   */
  default boolean hasName(String name) {
    return isNamePresent() &&
           Objects.equals(getName(), name);
  }

  /**
   * @param typeName
   * @return
   */
  default boolean hasType(String typeName) {
    return getType().hasName(typeName);
  }

  /**
   * @param type
   * @return
   */
  default boolean hasType(Class<?> type) {
    return hasName(type.getName());
  }

  /**
   * @param type
   * @return
   */
  default boolean hasType(HxType type) {
    return getType().equals(type);
  }
}
