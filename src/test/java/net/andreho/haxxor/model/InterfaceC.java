package net.andreho.haxxor.model;

/**
 * <br/>Created by a.hofmann on 01.06.2017 at 02:55.
 */
public interface InterfaceC
    extends InterfaceB {
  default int intValue() {
    return 0;
  }
  default String stringValue() {
    return "";
  }
  default void someWork(Object o, int a, long ... b) {
  }
}
