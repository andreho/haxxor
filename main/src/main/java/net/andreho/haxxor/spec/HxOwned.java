package net.andreho.haxxor.spec;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxOwned<O extends HxOwned<O>> {
   /**
    * @param <M>
    * @return
    */
   <M extends HxMember> M getDeclaringMember();

   /**
    * @param declaringMember
    */
   O setDeclaringMember(HxMember declaringMember);
}
