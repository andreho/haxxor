package net.andreho.haxxor.spec.api;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 11.03.2016.<br/>
 */
public interface HxTypeVariable
    extends HxGeneric<HxTypeVariable> {

  /**
   * @return a formal name of this type variable
   */
  String getName();

  /**
   * @param name is the formal name of this type variable
   * @return this
   */
  HxTypeVariable setName(String name);

  HxGeneric<?> getClassBound();

  HxTypeVariable setClassBound(HxGeneric<?> classBound);

  List<HxGeneric<?>> getInterfaceBounds();

  HxTypeVariable setInterfaceBounds(List<HxGeneric<?>> interfaceBounds);

  HxTypeVariable addInterfaceBound(HxGeneric<?> interfaceBound);

  //Either a Type or one of Parameterizable
  HxMember getGenericDeclaration();

  HxTypeVariable setGenericDeclaration(HxMember member);
}
