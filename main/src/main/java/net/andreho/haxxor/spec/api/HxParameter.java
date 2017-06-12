package net.andreho.haxxor.spec.api;

import java.util.Set;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxParameter<P extends HxExecutable<P>>
    extends HxAnnotated<HxParameter<P>>,
            HxMember<HxParameter<P>>,
            HxOwned<HxParameter<P>>,
            HxIndexed,
            HxProvider,
            Cloneable {

  /**
   * @return a new copy of this parameter
   */
  HxParameter<P> clone();

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
  HxExecutable getDeclaringMember();

  @Override
  HxParameter<P> setModifiers(HxModifier... modifiers);

  @Override
  HxParameter<P> setModifiers(int modifiers);

  /**
   *
   */
  enum Modifiers
      implements HxModifier {
    FINAL(0x0010),
    SYNTHETIC(0x1000),
    MANDATED(0x8000);

    final int bit;

    Modifiers(int bit) {
      this.bit = bit;
    }

    /**
     * Transforms given modifiers to an equal enum-set
     *
     * @param modifiers to transform
     * @return enum-set representation of given parameter's modifiers
     */
    public static Set<Modifiers> toSet(int modifiers) {
      return HxModifier.toSet(Modifiers.class, modifiers);
    }

    @Override
    public int toBit() {
      return bit;
    }
  }
}
