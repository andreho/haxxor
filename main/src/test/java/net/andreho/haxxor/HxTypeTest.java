package net.andreho.haxxor;


import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.struct.AbstractItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <br/>Created by a.hofmann on 31.05.2017 at 00:28.
 */
class HxTypeTest {
  public static final Class<AbstractItem> ABSTRACT_ITEM_CLASS = AbstractItem.class;

  @Test
  void printByteCodeOfObject() {
    HxType type = new Haxxor().resolve(ABSTRACT_ITEM_CLASS.getName());
    assertEquals(ABSTRACT_ITEM_CLASS.getName(), type.getJavaName());
  }
}