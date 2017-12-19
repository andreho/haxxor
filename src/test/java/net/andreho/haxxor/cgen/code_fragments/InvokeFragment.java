package net.andreho.haxxor.cgen.code_fragments;

import java.util.Objects;
import java.util.function.Function;

/**
 * <br/>Created by a.hofmann on 13.06.2017 at 00:48.
 */
public class InvokeFragment {
  public InvokeFragment INVOKEVIRTUAL() {
    return INVOKESPECIAL();
  }

  private InvokeFragment INVOKESPECIAL() {
    return INVOKESPECIAL();
  }

  static void INVOKESTATIC(InvokeFragment o) {
    Objects.requireNonNull(o);

    new InvokeFragment() //INVOKESPECIAL TOO
        .INVOKEVIRTUAL();
  }

  public static void INVOKESTATIC_ON_INTERFACE() {
    Function.identity();
  }

  static void INVOKEINTERFACE(Runnable r) {
    r.run();
  }

  static int INVOKEINTERFACE(Number n) {
    return n.intValue();
  }

  public Runnable INVOKEDYNAMIC_ON_INVOKEVIRTUAL() {
    return this::INVOKEVIRTUAL;
  }

  public Runnable INVOKEDYNAMIC_ON_INVOKESPECIAL() {
    return this::INVOKESPECIAL;
  }
}

