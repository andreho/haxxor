package net.andreho.haxxor.cgen.code_fragments;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 08:34.
 */
public class BinaryOperationsFragment {
  static int lsh(int a, int b) {
    return a << b;
  }

  static long lsh(long a, int b) {
    return a << b;
  }


  static int rsh(int a, int b) {
    return a >> b;
  }

  static long rsh(long a, int b) {
    return a >> b;
  }


  static int ursh(int a, int b) {
    return a >>> b;
  }

  static long ursh(long a, int b) {
    return a >>> b;
  }


  static int and(int a, int b) {
    return a & b;
  }

  static long and(long a, long b) {
    return a & b;
  }


  static int or(int a, int b) {
    return a | b;
  }

  static long or(long a, long b) {
    return a | b;
  }


  static int xor(int a, int b) {
    return a ^ b;
  }

  static long xor(long a, long b) {
    return a ^ b;
  }


  static int not(int a) {
    return ~a;
  }

  static long not(long a) {
    return ~a;
  }
}
