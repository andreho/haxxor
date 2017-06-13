package net.andreho.haxxor.cgen.code_fragments;

/**
 * <br/>Created by a.hofmann on 13.06.2017 at 00:37.
 */
public class CompareFragment {
  static boolean LCMP_GT(long a, long b) {
    return a > b;
  }

  static boolean LCMP_GE(long a, long b) {
    return a >= b;
  }

  static boolean LCMP_LT(long a, long b) {
    return a < b;
  }

  static boolean LCMP_LE(long a, long b) {
    return a <= b;
  }

  static boolean LCMP_EQ(long a, long b) {
    return a == b;
  }

  static boolean LCMP_NE(long a, long b) {
    return a != b;
  }

  static boolean FCMP_GT(float a, float b) {
    return a > b;
  }

  static boolean FCMP_GE(float a, float b) {
    return a >= b;
  }

  static boolean FCMP_LT(float a, float b) {
    return a < b;
  }

  static boolean FCMP_LE(float a, float b) {
    return a <= b;
  }

  static boolean FCMP_EQ(float a, float b) {
    return a == b;
  }

  static boolean FCMP_NE(float a, float b) {
    return a != b;
  }

  static boolean DCMP_GT(double a, double b) {
    return a > b;
  }

  static boolean DCMP_GE(double a, double b) {
    return a >= b;
  }

  static boolean DCMP_LT(double a, double b) {
    return a < b;
  }

  static boolean DCMP_LE(double a, double b) {
    return a <= b;
  }

  static boolean DCMP_EQ(double a, double b) {
    return a == b;
  }

  static boolean DCMP_NE(double a, double b) {
    return a != b;
  }

}
