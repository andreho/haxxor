package net.andreho.haxxor.cgen.code_fragments;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 23:15.
 */
public class LoadStoreFragment {
  public static int loadBoolean(boolean b) {
    return b? 1 : -1;
  }

  public static int loadByte(byte b) {
    return b == 0? 1 : -1;
  }

  public static int loadShort(short b) {
    return b == 0? 1 : -1;
  }

  public static int loadChar(char b) {
    return b == 0? 1 : -1;
  }

  public static int loadInt(int b) {
    return b == 0? 1 : -1;
  }

  public static int loadFloat(float b) {
    return b == 0f? 1 : -1;
  }

  public static int loadLong(long b) {
    return b == 0? 1 : -1;
  }

  public static int loadDouble(double b) {
    return b == 0? 1 : -1;
  }

  public static int loadReference(Object b) {
    return b == null? 1 : -1;
  }

  public static int post_inc(int a, int b) {
    switch (b) {
      case 1: return a++;
      case 2: return a+=2;
      case 3: return a+=3;
      case 4: return a+=4;
      case 5: return a+=4;
    }
    return a;
  }

  public static int pre_inc(int a, int b) {
    return ++a;
  }

  public static void storeBoolean(boolean b) {
    boolean a = false;
    if(b) {
      a = b;
    }
  }

  public static void storeByte(byte b) {
    byte a = 0;
    if(b == 0) {
      a = b;
    }
  }

  public static void storeShort(short b) {
    short a = 0;
    if(b == 0) {
      a = b;
    }
  }

  public static void storeChar(char b) {
    char a = 0;
    if(b == 0) {
      a = b;
    }
  }

  public static void storeInt(int b) {
    int a = 0;
    if(b == 0) {
      a = b;
    }
  }

  public static void storeFloat(float b) {
    float a = 0;
    if(b == 0) {
      a = b;
    }
  }

  public static void storeLong(long b) {
    long a = 0;
    if(b == 0) {
      a = b;
    }
  }

  public static void storeDouble(double b) {
    double a = 0;
    if(b == 0) {
      a = b;
    }
  }

  public static void storeReference(Object b) {
    Object a = 0;
    if(b != null) {
      a = b;
    }
  }
}
