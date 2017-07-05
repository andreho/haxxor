package net.andreho.haxxor.cgen.instr;

import net.andreho.asm.org.objectweb.asm.Label;

/**
 * <br/>Created by a.hofmann on 05.07.2017 at 14:22.
 */
public class NAMED_LABEL extends LABEL {
  private final String prefix;

  public NAMED_LABEL(final String prefix) {
    this.prefix = prefix;
  }

  public NAMED_LABEL(final String prefix, final Label asmLabel) {
    super(asmLabel);
    this.prefix = prefix;
  }

  public NAMED_LABEL(final String prefix,
                     final Label asmLabel,
                     final boolean link) {
    super(asmLabel, link);
    this.prefix = prefix;
  }

  @Override
  protected String prefix() {
    return prefix;
  }
}
