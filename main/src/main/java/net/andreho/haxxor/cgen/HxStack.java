package net.andreho.haxxor.cgen;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 25.10.2017 at 01:38.
 */
public interface HxStack {
  HxStack push(Object operand);

  HxStack push(Object a, Object b);

  HxStack push(Object a, Object b, Object c);

  HxStack push(Object a, Object b, Object c, Object d);

  HxStack push(List<Object> pushList);

  Object peek();

  Object pop();

  Object popTwice();

  HxStack pop(int count);

  Object peek(int depth);

  int size();

  void clear();

  HxStack copy();
}
