package net.andreho.haxxor.spi;

import net.andreho.haxxor.api.HxMember;
import net.andreho.haxxor.api.HxType;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 21.10.2017 at 14:28.
 */
@FunctionalInterface
public interface HxStubInterpreter {

  /**
   * @param target
   * @param stub
   * @return
   */
  List<HxMember<?>> interpret(HxType target, HxType stub);
}
