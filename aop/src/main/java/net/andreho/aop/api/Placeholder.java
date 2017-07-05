package net.andreho.aop.api;

import java.io.Serializable;

/**
 * <br/>Created by a.hofmann on 05.07.2017 at 14:15.
 */
public abstract class Placeholder
  implements Serializable {
  private Placeholder() {
    throw new IllegalStateException("Not instantiable.");
  }
}
