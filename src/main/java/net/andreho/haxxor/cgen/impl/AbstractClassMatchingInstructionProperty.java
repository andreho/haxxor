package net.andreho.haxxor.cgen.impl;

import net.andreho.haxxor.cgen.HxInstruction;

/**
 * <br/>Created by a.hofmann on 12.12.2017 at 02:23.
 */
public abstract class AbstractClassMatchingInstructionProperty<I extends HxInstruction, V> extends AbstractInstructionProperty<V> {
  private final Class<I> type;
  public AbstractClassMatchingInstructionProperty(final String name,
                                                  final Class<I> type) {
    super(name);
    this.type = type;
  }
  @Override
  public boolean isReadable(final HxInstruction instruction) {
    return type.isInstance(instruction);
  }
  @Override
  public V read(final HxInstruction instruction) {
    return readCasted(type.cast(instruction));
  }
  protected abstract V readCasted(final I instruction);
}
