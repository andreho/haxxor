package net.andreho.haxxor.spec.api;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxParameter<P extends HxParameterizable<P>>
    extends HxAnnotated<HxParameter<P>>,
            HxMember<HxParameter<P>>,
            HxOwned<HxParameter<P>>,
            HxProvider,
            Cloneable {

  /**
   * @return index of this parameter in the parameter list of the owning method/constructor
   */
  int getIndex();

  /**
   * @return name of this parameter
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
   * @return owning constructor or method instance
   */
  @Override
  HxParameterizable getDeclaringMember();

  @Override
  HxParameter<P> setModifiers(HxModifier... modifiers);

  @Override
  HxParameter<P> setModifiers(int modifiers);

  /**
   * @return a new copy of this parameter
   */
  HxParameter<P> clone();

  /**
   *
   */
  enum Modifiers
      implements HxModifier {
    FINAL(0x0010),
    // class, field, method, parameter
    SYNTHETIC(0x1000),
    // class, field, method, parameter
    MANDATED(0x8000); // parameter

    final int bit;

    Modifiers(int bit) {
      this.bit = bit;
    }

    @Override
    public int toBit() {
      return bit;
    }
  }
}
