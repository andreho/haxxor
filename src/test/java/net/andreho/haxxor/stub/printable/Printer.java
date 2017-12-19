package net.andreho.haxxor.stub.printable;

/**
 * <br/>Created by a.hofmann on 18.11.2017 at 15:54.
 */
public interface Printer {
  Printer print(boolean val);
  Printer print(byte val);
  Printer print(char val);
  Printer print(short val);
  Printer print(int val);
  Printer print(float val);
  Printer print(long val);
  Printer print(double val);
  Printer print(Object val);
  Printer print(String val);
  Printer print(Printable printable);

  Printer print(boolean[] array);
  Printer print(byte[] array);
  Printer print(char[] array);
  Printer print(char[] array, int begin, int length);
  Printer print(short[] array);
  Printer print(int[] array);
  Printer print(float[] array);
  Printer print(long[] array);
  Printer print(double[] array);
  Printer print(Object[] array);

  Printer enter(Printable val);
  Printer property(String name);
  Printer leave(Printable val);

  void drainTo(Appendable appendable);
  String toString();
}
