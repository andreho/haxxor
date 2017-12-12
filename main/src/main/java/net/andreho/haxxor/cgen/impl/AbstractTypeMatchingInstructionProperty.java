package net.andreho.haxxor.cgen.impl;

import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionType;

/**
 * <br/>Created by a.hofmann on 12.12.2017 at 02:23.
 */
public abstract class AbstractTypeMatchingInstructionProperty<I extends HxInstruction,V>
  extends AbstractClassMatchingInstructionProperty<I,V> {
  private final HxInstructionType type;
  public AbstractTypeMatchingInstructionProperty(final String name,
                                                 final Class<I> cls,
                                                 final HxInstructionType type) {
    super(name, cls);
    this.type = type;
  }
  @Override
  public boolean isReadable(final HxInstruction instruction) {
    return instruction.hasType(type) && super.isReadable(instruction);
  }
}
