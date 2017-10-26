package net.andreho.haxxor.api;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 11.03.2016.<br/>
 */
public interface HxTypeVariable
    extends HxGenericElement<HxTypeVariable> {

  /**
   * @return a formal name of this type variable
   */
  String getName();

  /**
   * @return the associated class-bound
   */
  HxGenericElement<?> getClassBound();

  /**
   * @return a list with interfaces
   */
  List<HxGenericElement<?>> getInterfaceBounds();

  //Either a Type or one of Parameterizable
  HxMember getGenericDeclaration();
}
