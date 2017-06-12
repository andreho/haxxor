package net.andreho.haxxor.cgen.editor;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.cgen.HxCodeStream;

/**
 * <br/>Created by a.hofmann on 28.03.2017 at 22:42.
 */
public class Editor {

  private final Haxxor haxxor;
  private final HxCodeStream stream;

  public Editor(final Haxxor haxxor, final HxCodeStream stream) {
    this.haxxor = haxxor;
    this.stream = stream;
  }

  public Haxxor getHaxxor() {
    return haxxor;
  }

  public HxCodeStream getStream() {
    return stream;
  }
}
