package examples;

import net.andreho.func.F0;
import net.andreho.func.F1;
import net.andreho.func.FN;
import net.andreho.func.V1;
import org.junit.jupiter.api.Test;

/**
 * <br/>Created by a.hofmann on 18.07.2017 at 14:22.
 */
class FuncExamples {

  @Test
  void test() {
    F0<Boolean> f_boolean = FuncExamples::f_boolean;
    FN fn = f_boolean;
    V1<Integer> v_void_int = this::f_void_int;
    fn = v_void_int.toFunc(null);
    F1<Integer, Integer> f_int_int = v_void_int.toFunc(1);
    fn = f_int_int;
  }

  private static boolean f_boolean() {
    return true;
  }

  private void f_void() {

  }

  private void f_void_int(int i) {

  }
}
