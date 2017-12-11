package net.andreho.haxxor.stub;

import net.andreho.haxxor.Debugger;
import net.andreho.haxxor.Haxxor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 13.10.2017 at 20:16.
 */
class AddingElementsTest {
  private Haxxor hx;

  @BeforeEach
  void init() {
    hx = new Haxxor();
  }

  @Test
  void addIdentifiableTemplate() {
//    HxType type = hx.createType("net.andreho.test.hx.template.FieldsAdd")
//                    .makePublic()
//                    .setSuperType(SuperClass.class);
//
//
//    type.addTemplate(IdentifiableTemplate.class);
  }

  public static void main(String[] args) {
    Debugger.trace(AddingElementsTest.class);
  }
}

class SuperClass {
  protected final List<?> list;
  public SuperClass(@Stub.Named final List<?> list) {
    this.list = list;
  }
  public SuperClass(@Stub.Named final Object ... objects) {
    this(Arrays.asList(objects));
  }

  @Override
  public String toString() {
    return "SuperClass";
  }
}