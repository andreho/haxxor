package net.andreho.haxxor.cgen;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 15.03.2016.<br/>
 */
public interface StackPush {

  /**
   * @return
   */
  List<Object> get();

  /**
   * @return
   */
  default StackPush prepare() {
    get().clear();
    return this;
  }

  /**
   * @param value
   * @return
   */
  default StackPush push(Object value) {
    get().add(value);
    return this;
  }

  /**
   * @param value1
   * @param value2
   * @return
   */
  default StackPush push(Object value1, Object value2) {
    return push(value1).push(value2);
  }

  /**
   * @param value1
   * @param value2
   * @param value3
   * @return
   */
  default StackPush push(Object value1, Object value2, Object value3) {
    return push(value1).push(value2)
                       .push(value3);
  }

  /**
   * @param value1
   * @param value2
   * @param value3
   * @param value4
   * @return
   */
  default StackPush push(Object value1, Object value2, Object value3, Object value4) {
    return push(value1).push(value2)
                       .push(value3)
                       .push(value4);
  }

  /**
   * @param value1
   * @param value2
   * @param value3
   * @param value4
   * @param value5
   * @return
   */
  default StackPush push(Object value1, Object value2, Object value3, Object value4, Object value5) {
    return push(value1).push(value2)
                       .push(value3)
                       .push(value4)
                       .push(value5);
  }

  /**
   * @param value1
   * @param value2
   * @param value3
   * @param value4
   * @param value5
   * @param value6
   * @return
   */
  default StackPush push(Object value1, Object value2, Object value3, Object value4, Object value5, Object value6) {
    return push(value1).push(value2)
                       .push(value3)
                       .push(value4)
                       .push(value5)
                       .push(value6);
  }
}
