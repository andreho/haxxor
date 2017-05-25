package net.andreho.haxxor.spec.generics;

import net.andreho.haxxor.spec.HxGeneric;
import net.andreho.haxxor.spec.HxMember;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 11.03.2016.<br/>
 */
public interface HxTypeVariable extends HxGeneric {
   /**
    * @return a formal name of this type variable
    */
   String getName();

   /**
    * @param name is the formal name of this type variable
    * @return this
    */
   HxTypeVariable setName(String name);

   List<HxGeneric> getBounds();

   HxTypeVariable setBounds(List<HxGeneric> bounds);

   //Either a Type or one of Parameterizable
   HxMember getGenericDeclaration();

   HxTypeVariable setGenericDeclaration(HxMember member);
}
