package net.andreho.haxxor.cgen.code_fragments;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 23:49.
 */
public class ConstantsFragment {
  static Object ACONST_NULL() {
    return null;
  }

  static int ICONST_M1() {
    return -1;
  }

  static int ICONST_0() {
    return 0;
  }

  static int ICONST_1() {
    return 1;
  }

  static int ICONST_2() {
    return 2;
  }

  static int ICONST_3() {
    return 3;
  }

  static int ICONST_4() {
    return 4;
  }

  static int ICONST_5() {
    return 5;
  }

  static long LCONST_0() {
    return 0L;
  }

  static long LCONST_1() {
    return 1L;
  }

  static float FCONST_0() {
    return 0f;
  }

  static float FCONST_1() {
    return 1f;
  }

  static float FCONST_2() {
    return 2f;
  }

  static double DCONST_0() {
    return 0d;
  }

  static double DCONST_1() {
    return 1d;
  }

  static byte BIPUSH() {
    String xyz = System.getProperty("xyz");
    if("xyz".equals(xyz)) {
      return -128;
    } else if("xyz1".equals(xyz)) {
      return -2;
    } else if("xyz2".equals(xyz)) {
      return 6;
    }
    return 127;
  }

  static short SIPUSH() {
    String xyz = System.getProperty("xyz");
    if("xyz".equals(xyz)) {
      return -129;
    }
    return 128;
  }

  static int LDC_INT() {
    return 1234567890;
  }

  static float LDC_FLOAT() {
    return 1.125f;
  }

  static long LDC_LONG() {
    return 1234567890L;
  }

  static double LDC_DOUBLE() {
    return 1.125d;
  }

  static String LDC_STRING() {
    return "xyz";
  }

  static Class<?> LDC_TYPE() {
    return String.class;
  }

  static Runnable LDC_METHOD_HANDLE_AND_METHOD_TYPE() {
    return () -> System.out.println("This will be a static method.");
  }
}
