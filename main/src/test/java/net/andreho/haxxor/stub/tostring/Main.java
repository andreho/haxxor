package net.andreho.haxxor.stub.tostring;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.stub.errors.HxStubException;

import static net.andreho.haxxor.stub.ClassInjector.injectClassIntoClassLoader;

/**
 * <br/>Created by a.hofmann on 10.12.2017 at 19:13.
 */
public class Main {
  public static void main(String[] args)
  throws HxStubException, IllegalAccessException, InstantiationException {
    Hx hx = Hx.create();
    HxType namedType = hx.resolve("net.andreho.haxxor.stub.tostring.NamedClass");
    namedType.addTemplate(NameCheckingTemplate.class);
    byte[] byteCode = namedType.toByteCode();
    //Debugger.trace(byteCode);

    Class<? extends NameProvider> cls =
      injectClassIntoClassLoader("net.andreho.haxxor.stub.tostring.NamedClass", byteCode);
    NameProvider instance = cls.newInstance();

    testCorrectness(instance);
  }

  private static void testCorrectness(final NameProvider instance) {
    instance.setName("OK");
    if(!"OK".equals(instance.getName())) {
      throw new IllegalStateException("Setter doesn't work.");
    }
    try {
      instance.setName(null);
      throw new IllegalStateException("Setter doesn't handle null properly.");
    } catch (IllegalArgumentException e) {
      if(!"Invalid name".equals(e.getMessage())) {
        throw new IllegalStateException("Setter doesn't create proper error message: "+e.getMessage());
      }
    }
  }
}
