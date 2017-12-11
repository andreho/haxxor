package net.andreho.haxxor.stub.printable;

import net.andreho.haxxor.stub.Stub;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 18.11.2017 at 16:03.
 */
public class PrintableTemplate implements Printable {
  @Stub.Class static Class __class__() { return PrintableTemplate.class; }

  private static final String[] NAMES;
  private static final MethodHandle[] HANDLES;

  static {
    final List<String> names = new ArrayList<>();
    final List<MethodHandle> handles = new ArrayList<>();
    final MethodHandles.Lookup lookup = MethodHandles.lookup();
    try {
      Class current = __class__();
      while(current != null && current != Object.class) {
        for(Field field : current.getFields()) {
          if(field.isAnnotationPresent(Printable.Element.class)) {
            names.add(field.getName());
            handles.add(lookup.unreflectGetter(field));
          }
        }
        current = current.getSuperclass();
      }
    } catch (IllegalAccessException e) {
      throw new IllegalStateException(e);
    }
    NAMES = names.toArray(new String[0]);
    HANDLES = handles.toArray(new MethodHandle[0]);
  }

  @Override
  public Printer print(final Printer printer) {
    Printer current = printer.enter(this);
    final String[] names = NAMES;
    final MethodHandle[] handles = HANDLES;
    try {
      for (int i = 0; i < NAMES.length; i++) {
        final String name = names[i];
        final MethodHandle handle = handles[i];
        current = current.property(name)
                         .print(handle.invokeExact(this));
      }
    } finally {
      return current.leave(this);
    }
  }
}
