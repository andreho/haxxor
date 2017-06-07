package net.andreho.haxxor.model;

import java.util.List;
import java.util.Map;

/**
 * <br/>Created by a.hofmann on 06.06.2017 at 23:23.
 */
public interface GenericInterfaceA<A extends GenericInterfaceA<A, B, C>, B, C> {
  A methodA();
  void methodB(B b);
  B methodC(C c);
  List<C> methodD(Map<B,C> map);
}
