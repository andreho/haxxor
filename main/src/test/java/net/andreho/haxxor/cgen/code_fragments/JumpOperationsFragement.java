package net.andreho.haxxor.cgen.code_fragments;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 22:21.
 */
public class JumpOperationsFragement {
  static int IFEQ(int a) {
    if(a != 0) {
      return 1;
    }
    return 0;
  }

  static int IFNE(int a) {
    if(a == 0) {
      return 1;
    }
    return 0;
  }

  static int IFLT(int a) {
    if(a >= 0) {
      return 1;
    }
    return 0;
  }

  static int IFGE(int a) {
    if(a < 0) {
      return 1;
    }
    return 0;
  }

  static int IFGT(int a) {
    if(a <= 0) {
      return 1;
    }
    return 0;
  }

  static int IFLE(int a) {
    if(a > 0) {
      return 1;
    }
    return 0;
  }

  static int IF_ICMPEQ(int a, int b) {
    if(a != b) {
      return 1;
    }
    return 0;
  }

  static int IF_ICMPNE(int a, int b) {
    if(a == b) {
      return 1;
    }
    return 0;
  }

  static int IF_ICMPLT(int a, int b) {
    if(a >= b) {
      return 1;
    }
    return 0;
  }

  static int IF_ICMPGE(int a, int b) {
    if(a < b) {
      return 1;
    }
    return 0;
  }

  static int IF_ICMPGT(int a, int b) {
    if(a <= b) {
      return 1;
    }
    return 0;
  }

  static int IF_ICMPLE(int a, int b) {
    if(a > b) {
      return 1;
    }
    return 0;
  }

  static int IF_ACMPEQ(Object a, Object b) {
    if(a != b) {
      return 1;
    }
    return 0;
  }

  static int IF_ACMPNE(Object a, Object b) {
    if(a == b) {
      return 1;
    }
    return 0;
  }

  static int IFNULL(Object a) {
    if(a != null) {
      return 1;
    }
    return 0;
  }

  static int IFNONNULL(Object a) {
    if(a == null) {
      return 1;
    }
    return 0;
  }

  static int GOTO() {
    int sum = 0;
    int idx = 0;
    while (idx++ < 10) {
      sum += idx * 10;
    }
    return sum;
  }
}
