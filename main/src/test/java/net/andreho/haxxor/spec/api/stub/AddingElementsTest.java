package net.andreho.haxxor.spec.api.stub;

import net.andreho.haxxor.Debugger;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

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
  void addFields() {
    HxType type = hx.createType("net.andreho.test.hx.template.FieldsAdd")
                    .makePublic()
                    .setSuperType(SuperClass.class);


    type.addTemplate(IdentifiableTemplate.class);
//    type.addFieldsOf(IdentifiableTemplate.class);
//    type.addConstructorsOf(IdentifiableTemplate.class);
  }

  private static void staticTest() {}
  @Test
  void debug() {
    Runnable r1 = AddingElementsTest::staticTest;
    Runnable r2 = this::addFields;
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

@Stub.Autowire
class IdentifiableTemplate {
  @Stub.Constructor private static void __constructor__(final List<?> list) {}
  @Stub.Constructor private static void __constructor__(final Object ... objects) {}
  //------------------------------------------------------------------------------------------------------------------

  private static final AtomicInteger COUNTER = new AtomicInteger();
  private final int id;

  public IdentifiableTemplate() {
    __constructor__(new ArrayList<>());
    this.id = COUNTER.getAndIncrement();
  }
  public IdentifiableTemplate(final int id) {
    __constructor__(new ArrayList<>());
    this.id = id;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final IdentifiableTemplate that = (IdentifiableTemplate) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return id;
  }
}

class LoggerTemplate {
  @Stub.DeclaringClass static Class<?> __class__() { return LoggerTemplate.class; }
  //------------------------------------------------------------------------------------------------------------------
  private static final Logger LOG = Logger.getLogger(__class__().getName());

  public Logger log() {
    return LOG;
  }
}

abstract class SuperDelegationTemplate {
//  @Stub.Ignore public SuperDelegationTemplate(final List<?> list) {
//    super(list);
//  }

  @Stub.Super public String super_toString() { return null; }
  //------------------------------------------------------------------------------------------------------------------

  public String toString() {
    String val = super_toString();
    return "Received: " + val;
  }
}