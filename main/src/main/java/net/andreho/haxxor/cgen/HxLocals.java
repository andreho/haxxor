package net.andreho.haxxor.cgen;

/**
 * <br/>Created by a.hofmann on 25.10.2017 at 01:38.
 */
public interface HxLocals {

  HxLocals set(int index,
               Object type);

  HxLocals set(int index,
               Object type,
               Object top);

  Object get(int index);

  boolean has(int index);

  boolean has(int index,
              Object type);

  int size();

  void clear();

  HxLocals copy();
}
