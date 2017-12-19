package net.andreho.haxxor.model;

import java.util.List;
import java.util.Map;

/**
 * <br/>Created by a.hofmann on 06.06.2017 at 23:23.
 */
public interface GenericInterfaceB<B> extends GenericInterfaceA<GenericInterfaceB<B>, B, String> {
  List<B> methodX(Map<B,B> map);
}
