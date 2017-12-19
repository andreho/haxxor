package net.andreho.haxxor.stub.identifiable;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.stub.errors.HxStubException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static net.andreho.haxxor.stub.ClassInjector.injectClassIntoClassLoader;

/**
 * <br/>Created by a.hofmann on 12.12.2017 at 03:55.
 */
public class Main {
  private static final String TARGET_CLASS = "net.andreho.haxxor.stub.identifiable.IdentifiableTargetClass";

  public static void main(String[] args)
  throws HxStubException, IllegalAccessException, InstantiationException, NoSuchMethodException,
         InvocationTargetException {
    Hx hx = Hx.create();
    HxType namedType = hx.resolve(TARGET_CLASS);
    namedType.addTemplate(IdentifiableTemplate.class);
    byte[] byteCode = namedType.toByteCode();
    //Debugger.trace(byteCode);
    Class<? extends Identifiable> cls =
      injectClassIntoClassLoader(TARGET_CLASS, byteCode);

    Identifiable one = create(cls.getConstructor(String.class), "OK");
    Identifiable two = create(cls.getConstructor(String.class), "OK");
    Identifiable three = create(cls.getConstructor(String.class, long.class), "OK", 3L);
    Identifiable oneB = create(cls.getConstructor(String.class, long.class), "OK", 1L);

    testCorrectness(one, two, three, oneB);
  }

  private static void testCorrectness(final Identifiable a,
                                      final Identifiable b,
                                      final Identifiable c,
                                      final Identifiable a2) {
    IdentifiableTargetClass atc = (IdentifiableTargetClass) a;
    IdentifiableTargetClass btc = (IdentifiableTargetClass) b;
    IdentifiableTargetClass ctc = (IdentifiableTargetClass) c;
    IdentifiableTargetClass atc2 = (IdentifiableTargetClass) a2;

    if(!atc.getName().equals(btc.getName()) ||
       !btc.getName().equals(ctc.getName()) ||
       !ctc.getName().equals(atc2.getName())) {
      throw new IllegalStateException("Invalid names.");
    }

    if(a.getId() != 1 ||
       b.getId() != 2 ||
       c.getId() != 3 ||
       a2.getId() != 1) {
      throw new IllegalStateException("Invalid IDs.");
    }

    if(!a.equals(a2) ||
       !a2.equals(a) ||
       a.equals(b) ||
       b.equals(c)) {
      throw new IllegalStateException("Invalid equals.");
    }

    if(a.hashCode() == b.hashCode() ||
       b.hashCode() == c.hashCode() ||
       a.hashCode() != a2.hashCode()) {
      throw new IllegalStateException("Invalid hashCode.");
    }
  }

  private static <V> V create(Constructor<V> constructor, Object ... args)
  throws IllegalAccessException, InvocationTargetException, InstantiationException {
    return constructor.newInstance(args);
  }
}
