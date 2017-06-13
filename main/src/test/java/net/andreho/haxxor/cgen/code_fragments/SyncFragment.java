package net.andreho.haxxor.cgen.code_fragments;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 23:49.
 */
public class SyncFragment {
  static synchronized void classSync() {
    System.out.println("test");
  }

  void thisSync() {
    synchronized (this) {
      System.out.println("test");
    }
  }

  synchronized void thisSyncMethod() {
    System.out.println("test");
  }
}
