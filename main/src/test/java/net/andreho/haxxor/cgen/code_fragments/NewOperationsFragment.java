package net.andreho.haxxor.cgen.code_fragments;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 08:34.
 */
public class NewOperationsFragment {
  static boolean[] createBooleanArray(int len) {
    return new boolean[len];
  }

  static byte[] createByteArray(int len) {
    return new byte[len];
  }

  static short[] createShortArray(int len) {
    return new short[len];
  }

  static char[] createCharArray(int len) {
    return new char[len];
  }

  static int[] createIntArray(int len) {
    return new int[len];
  }

  static float[] createFloatArray(int len) {
    return new float[len];
  }

  static long[] createLongArray(int len) {
    return new long[len];
  }

  static double[] createDoubleArray(int len) {
    return new double[len];
  }

  static Object[] createObjectArray(int len) {
    return new Object[len];
  }


  static boolean[][] multiDimensionalBooleanArray() {
    return new boolean[0][1];
  }

  static byte[][] multiDimensionalByteArray() {
    return new byte[0][1];
  }

  static char[][] multiDimensionalCharArray() {
    return new char[0][1];
  }

  static short[][] multiDimensionalShortArray() {
    return new short[0][1];
  }

  static int[][] multiDimensionalIntegerArray() {
    return new int[0][1];
  }

  static float[][] multiDimensionalFloatArray() {
    return new float[0][1];
  }

  static long[][] multiDimensionalLongArray() {
    return new long[0][1];
  }

  static double[][] multiDimensionalDoubleArray() {
    return new double[0][1];
  }

  static Object[][] multiDimensionalObjectArray() {
    return new Object[0][1];
  }

  static Object newObject() {
    return new Object();
  }
}
