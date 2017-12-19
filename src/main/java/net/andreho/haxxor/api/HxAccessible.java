package net.andreho.haxxor.api;

/**
 * <br/>Created by a.hofmann on 22.06.2017 at 07:09.
 */
public interface HxAccessible<A extends HxMember<A> & HxOwned<A>> extends HxMember<A>, HxOwned<A> {
  /**
   * @return a declaring type or self if this is a none inner type
   */
  default HxType getDeclaringType() {
    return (HxType) getDeclaringMember();
  }

  /**
   * @param type whose access should be checked
   * @return <b>true</b> if this class may be accessed from methods of the given one type, <b>false</b> otherwise
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
    return type.hasSamePackageName(declaringType);
  }

  /**
   * @param method whose access should be checked
   * @return <b>true</b> if this class may be accessed from the given method, <b>false</b> otherwise
   */
  default boolean isAccessibleFrom(HxMethod method) {
    return isAccessibleFrom(method.getDeclaringMember());
  }
}
