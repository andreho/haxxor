package net.andreho.haxxor.api.stub.printable;

import java.io.IOException;

/**
 * <br/>Created by a.hofmann on 18.11.2017 at 16:26.
 */
public class JsonPrinter
  implements Printer {

  private final StringBuilder builder;
  private int indent;

  public JsonPrinter() {
    this(new StringBuilder());
  }
  
  public JsonPrinter(final StringBuilder builder) {
    this.builder = builder;
  }

  @Override
  public Printer print(final boolean val) {
    builder.append(val);
    return this;
  }

  @Override
  public Printer print(final byte val) {
    builder.append(val);
    return this;
  }

  @Override
  public Printer print(final char val) {
    builder.append(val);
    return this;
  }

  @Override
  public Printer print(final short val) {
    builder.append(val);
    return this;
  }

  @Override
  public Printer print(final int val) {
    builder.append(val);
    return this;
  }

  @Override
  public Printer print(final float val) {
    builder.append(val);
    return this;
  }

  @Override
  public Printer print(final long val) {
    builder.append(val);
    return this;
  }

  @Override
  public Printer print(final double val) {
    builder.append(val);
    return this;
  }

  @Override
  public Printer print(final Object val) {
    if(val instanceof Printable) {
      return print((Printable) val);
    } else {
      builder.append(val);
    }
    return this;
  }

  @Override
  public Printer print(final Printable printable) {
    return printable.print(this);
  }

  @Override
  public Printer print(final String val) {
    builder.append(val);
    return this;
  }

  @Override
  public Printer print(final boolean[] array) {
    print("[");
    if(array.length > 0) {
      print(array[0]);
      for (int i = 1; i < array.length; i++) {
        print(",").print(array[i]);
      }
    }
    return print("]");
  }

  @Override
  public Printer print(final byte[] array) {
    print("[");
    if(array.length > 0) {
      print(array[0]);
      for (int i = 1; i < array.length; i++) {
        print(",").print(array[i]);
      }
    }
    return print("]");
  }

  @Override
  public Printer print(final char[] array) {
    print("[");
    if(array.length > 0) {
      print(array[0]);
      for (int i = 1; i < array.length; i++) {
        print(",").print(array[i]);
      }
    }
    return print("]");
  }

  @Override
  public Printer print(final char[] array,
                       final int begin,
                       final int length) {
    for (int i = begin, len = Math.min(array.length, begin + length); i < len; i++) {
      builder.append(array[i]);
    }
    return this;
  }

  @Override
  public Printer print(final short[] array) {
    print("[");
    if(array.length > 0) {
      print(array[0]);
      for (int i = 1; i < array.length; i++) {
        print(",").print(array[i]);
      }
    }
    return print("]");
  }

  @Override
  public Printer print(final int[] array) {
    print("[");
    if(array.length > 0) {
      print(array[0]);
      for (int i = 1; i < array.length; i++) {
        print(",").print(array[i]);
      }
    }
    return print("]");
  }

  @Override
  public Printer print(final float[] array) {
    print("[");
    if(array.length > 0) {
      print(array[0]);
      for (int i = 1; i < array.length; i++) {
        print(",").print(array[i]);
      }
    }
    return print("]");
  }

  @Override
  public Printer print(final long[] array) {
    print("[");
    if(array.length > 0) {
      print(array[0]);
      for (int i = 1; i < array.length; i++) {
        print(",").print(array[i]);
      }
    }
    return print("]");
  }

  @Override
  public Printer print(final double[] array) {
    print("[");
    if(array.length > 0) {
      print(array[0]);
      for (int i = 1; i < array.length; i++) {
        print(",").print(array[i]);
      }
    }
    return print("]");
  }

  @Override
  public Printer print(final Object[] array) {
    print("[");
    if(array.length > 0) {
      print(array[0]);
      for (int i = 1; i < array.length; i++) {
        print(",").print(array[i]);
      }
    }
    return print("]");
  }

  @Override
  public Printer enter(final Printable val) {
    indent++;
    return print("{");
  }

  @Override
  public Printer property(final String name) {
    return print("\n\"").print(name).print("\": ");
  }

  @Override
  public Printer leave(final Printable val) {
    indent--;
    return print("\n}");
  }

  @Override
  public void drainTo(final Appendable appendable) {
    try {
      appendable.append(builder);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public String toString() {
    return builder.toString();
  }
}
