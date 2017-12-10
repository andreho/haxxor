package net.andreho.haxxor.api;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxOwned<O extends HxOwned<O>> {

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
