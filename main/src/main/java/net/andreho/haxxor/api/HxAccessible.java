package net.andreho.haxxor.api;

/**
 * <br/>Created by a.hofmann on 22.06.2017 at 07:09.
 */
public interface HxAccessible<A extends HxMember<A> & HxOwned<A>> extends HxMember<A>, HxOwned<A> {
  /**
   * @return
   */
  HxType getDeclaringType();

  /**
   * @param type
   * @return
   */
  default boolean isAccessibleFrom(HxType type) {
    final HxType declaringType = getDeclaringType();
    if(hasModifiers(HxModifiers.PUBLIC)) {
      return true;
    } else if(hasModifiers(HxModifiers.PRIVATE) &&
              type.equals(declaringType)) {
      return true;
    } else if(hasModifiers(HxModifiers.PROTECTED) &&
              declaringType.isAssignableFrom(type)) {
      return true;
    }
    return type.getPackageName().equals(declaringType.getPackageName());
  }

  /**
   * @param member
   * @return
   */
  default boolean isAccessibleFrom(HxOwned<?> member) {
    return isAccessibleFrom((HxType) member.getDeclaringMember());
  }
}
