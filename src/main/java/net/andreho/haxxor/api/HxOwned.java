package net.andreho.haxxor.api;

import java.util.Objects;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxOwned<O extends HxOwned<O>> {

  /**
   * @param member to check against
   * @return
   */
  default boolean isDeclaredBy(HxMember<?> member) {
    return Objects.equals(getDeclaringMember(), member);
  }

  /**
   * @return
   */
  default HxMember getDeclaringMember() {
    return null;
  }

  /**
   * @param declaringMember
   */
  default O setDeclaringMember(HxMember declaringMember) {
    throw new UnsupportedOperationException("This element can't be owned by any other element: "+this);
  }
}
