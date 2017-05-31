package net.andreho.haxxor.cgen.editor;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.cgen.CodeStream;

/**
 * <br/>Created by a.hofmann on 28.03.2017 at 22:42.
 */
public class Editor {

  private final Haxxor haxxor;
  private final CodeStream stream;

  public Editor(final Haxxor haxxor, final CodeStream stream) {
    this.haxxor = haxxor;
    this.stream = stream;
  }

  public Haxxor getHaxxor() {
    return haxxor;
  }

  public CodeStream getStream() {
    return stream;
  }
}
