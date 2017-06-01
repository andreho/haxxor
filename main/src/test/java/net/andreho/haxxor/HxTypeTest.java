package net.andreho.haxxor;


import net.andreho.haxxor.model.AbstractBean;
import net.andreho.haxxor.spec.api.HxType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <br/>Created by a.hofmann on 31.05.2017 at 00:28.
 */
class HxTypeTest {
  public static final Class<AbstractBean> ABSTRACT_ITEM_CLASS = AbstractBean.class;

  @Test
  void printByteCodeOfObject() {
    HxType type = new Haxxor().resolve(ABSTRACT_ITEM_CLASS.getName());
    assertEquals(ABSTRACT_ITEM_CLASS.getName(), type.getJavaName());
  }
}