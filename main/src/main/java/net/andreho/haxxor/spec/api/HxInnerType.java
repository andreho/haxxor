package net.andreho.haxxor.spec.api;

import net.andreho.haxxor.utils.NamingUtils;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 08:27.
 */
public interface HxInnerType
  extends HxOwned<HxInnerType>,
          HxMember<HxInnerType>,
          HxProvider,
          HxNamed {

  /**
   * @return
   */
  default String getSimpleName() {
    return NamingUtils.toSimpleName(getName());
  }

  /**
   * @return <b>true</b> if this is an anonymous type, <b>false</b> otherwise.
   */
  default boolean isAnonymous() {
    return NamingUtils.isAnonymous(getName());
  }

  /**
   * @return <b>true</b> if this is a local type, <b>false</b> otherwise.
   */
  default boolean isLocalType() {
    return !hasModifiers(HxType.Modifiers.STATIC) &&
           !NamingUtils.isAnonymous(getName()) &&
           toType().getDeclaringMember() instanceof HxMethod;
  }

  /**
   * @return <b>true</b> if this is a member type, <b>false</b> otherwise.
   */
  default boolean isMemberType() {
    return !hasModifiers(HxType.Modifiers.STATIC) &&
           NamingUtils.hasSimpleBinaryName(getName());
  }

  /**
   * @return
   */
  HxType toType();
}
