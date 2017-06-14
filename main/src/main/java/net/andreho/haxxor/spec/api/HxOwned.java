package net.andreho.haxxor.spec.api;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxOwned<O extends HxOwned<O>> {

  /**
   * @return
   */
  HxMember getDeclaringMember();

  /**
   * @param declaringMember
   */
  O setDeclaringMember(HxMember declaringMember);
}
