package net.andreho.haxxor.cgen;

import net.andreho.haxxor.cgen.impl.HxLocalVariableImpl;
import net.andreho.haxxor.cgen.instr.misc.LABEL;
import net.andreho.haxxor.spec.api.HxAnnotated;

/**
 * Created by 666 on 25.06.2017.
 */
public interface HxLocalVariable
  extends HxVisitable,
          HxAnnotated<HxLocalVariable> {

  /**
   * @return
   */
  static HxLocalVariable createLocalVariable() {
    return new HxLocalVariableImpl();
  }

  /**
   * @return
   */
  static HxLocalVariable createLocalVariable(int index, String name, LABEL start, LABEL end, String descriptor, String signature) {
    return new HxLocalVariableImpl()
      .setIndex(index)
      .setName(name)
      .setStart(start)
      .setEnd(end)
      .setDescriptor(descriptor)
      .setSignature(signature);
  }

  /**
   * @return index of this local variable
   */
  int getIndex();

  /**
   * @param index of this variable
   */
  HxLocalVariable setIndex(final int index);

  /**
   * @param delta
   */
  default HxLocalVariable shift(int delta) {
    return setIndex(getIndex() + delta);
  }

  /**
   * @return descriptor of this local variable
   */
  String getDescriptor();

  /**
   * @param descriptor
   * @return
   */
  HxLocalVariable setDescriptor(String descriptor);

  /**
   * @return name of this local variable
   */
  String getName();

  /**
   * @param name
   */
  HxLocalVariable setName(String name);

  /**
   * @return full signature of this local variable
   */
  String getSignature();

  /**
   * @param signature
   */
  HxLocalVariable setSignature(String signature);

  /**
   * @return start asmLabel of this local variable
   */
  LABEL getBegin();

  /**
   * @param start
   */
  HxLocalVariable setStart(LABEL start);

  /**
   * @return end asmLabel of this local variable
   */
  LABEL getEnd();

  /**
   * @param end
   */
  HxLocalVariable setEnd(LABEL end);

  /**
   * @return count of slots that reserved by this local variable (2 for long and double, otherwise 1 always)
   */
  int size();
}
