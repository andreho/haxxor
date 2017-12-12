package net.andreho.haxxor.stub.loggable;

import net.andreho.haxxor.Debugger;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.stub.errors.HxStubException;

import java.util.logging.Logger;

import static net.andreho.haxxor.stub.ClassInjector.injectClassIntoClassLoader;

/**
 * <br/>Created by a.hofmann on 12.12.2017 at 01:10.
 */
public class Main {
  public static void main(String[] args)
  throws HxStubException, IllegalAccessException, InstantiationException {
    Hx hx = Hx.create();
    HxType namedType = hx.resolve("net.andreho.haxxor.stub.loggable.LoggableTargetClass");
    namedType.addTemplate(LoggableTemplate.class);
    byte[] byteCode = namedType.toByteCode();
    Debugger.trace(byteCode);

    Class<? extends Loggable> cls =
      injectClassIntoClassLoader("net.andreho.haxxor.stub.loggable.LoggableTargetClass", byteCode);
    Loggable instance = cls.newInstance();
    testCorrectness(instance);
  }

  private static void testCorrectness(final Loggable instance) {
    Logger log = instance.log();
    if(log == null) {
      throw new IllegalStateException("Log is null");
    }
    if(!instance.getClass().getName().equals(log.getName())) {
      throw new IllegalStateException("Log has wrong name: "+log.getName());
    }
    log.warning("Everything is OK");
  }
}
