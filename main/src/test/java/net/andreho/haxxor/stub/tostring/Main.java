package net.andreho.haxxor.stub.tostring;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.stub.errors.HxStubException;

/**
 * <br/>Created by a.hofmann on 10.12.2017 at 19:13.
 */
public class Main {
  public static void main(String[] args)
  throws HxStubException {
    Hx hx = Hx.create();
    HxType namedType = hx.resolve(NamedClass.class);
    namedType.addTemplate(NameCheckingTemplate.class);

    //ClassInjector.injectClassIntoClassLoader();
  }
}
