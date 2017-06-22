package net.andreho.haxxor;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;

/**
 * <br/>Created by a.hofmann on 31.05.2017 at 00:28.
 */
class SomeObservations {

  @Test
  @Disabled
  void printByteCodeOfObject() {
    Debugger.trace(MethodHandles.Lookup.class);
  }

  @Test
  @Disabled
  void printInterfacesOfArray() {
    Object array =
        //new Object[0];
        new byte[0];
    for (Class<?> itf : array.getClass()
                             .getInterfaces()) {
      System.out.println(itf);
    }
  }

  @Test
  @Disabled
  void testTypeInterference() {
//    boolean v = true;
//    byte v = 1;
//    short v = 1;
//    char v = 1;
//    int v = 1;
//    float v = 1;
//    long v = 1;
    double v = 1;

    x(v);
  }

//  static void x(boolean b) {
//    System.out.println("boolean: "+b);
//  }
//  static void x(byte b) {
//    System.out.println("byte: "+b);
//  }
//  static void x(short s) {
//    System.out.println("short: "+s);
//  }
//  static void x(char c) {
//    System.out.println("char: "+c);
//  }
//  static void x(int i) {
//    System.out.println("int: "+i);
//  }
//  static void x(float f) {
//    System.out.println("float: "+f);
//  }
//  static void x(long l) {
//    System.out.println("long: "+l);
//  }
//  static void x(double d) {
//    System.out.println("double: "+d);
//  }

//  static void x(Boolean bool) {
//    System.out.println("Boolean: "+bool);
//  }
//  static void x(Byte bbyte) {
//    System.out.println("Byte: "+bbyte);
//  }
//  static void x(Short bshort) {
//    System.out.println("Short: "+bs);
//  }
//  static void x(Character bchar) {
//    System.out.println("Character: "+bc);
//  }
//  static void x(Integer bint) {
//    System.out.println("Int: "+bint);
//  }
//  static void x(Float bfloat) {
//    System.out.println("Float: "+bfloat);
//  }
//  static void x(Long blong) {
//    System.out.println("Long: "+blong);
//  }
//  static void x(Double bdouble) {
//    System.out.println("Double: "+bdouble);
//  }
//  static void x(Number number) {
//    System.out.println("Number: "+number);
//  }
  static void x(Serializable serializable) {
    System.out.println("Serializable: "+serializable);
  }
//  static void x(Comparable<Double> comparable) {
//    System.out.println("Comparable: "+comparable);
//  }
  static void x(Object obj) {
    System.out.println("Any: "+obj);
  }
}