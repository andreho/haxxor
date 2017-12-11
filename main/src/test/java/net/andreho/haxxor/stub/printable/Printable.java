package net.andreho.haxxor.stub.printable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br/>Created by a.hofmann on 18.11.2017 at 15:53.
 */
public interface Printable {

  /**
   * @param printer
   * @return
   */
  Printer print(Printer printer);

  @Target(ElementType.FIELD)
  @Retention(RetentionPolicy.RUNTIME)
  @interface Element {}
}
