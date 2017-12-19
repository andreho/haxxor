package net.andreho.haxxor.cgen;

/**
 * <br/>Created by a.hofmann on 25.10.2017 at 03:59.
 */
public interface HxFrame {
  HxInstruction getFirst();
  HxInstruction getLast();
  HxFrame setLast(HxInstruction last);

  HxStack getStack();
  HxLocals getLocals();
  HxFrame copy();
}
