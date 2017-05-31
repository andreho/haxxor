package net.andreho.haxxor.spec.annotation.arrays;

import net.andreho.haxxor.spec.annotation.AbstractAnnotationEntry;
import net.andreho.haxxor.spec.api.HxType;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Objects;

/**
 * Created by a.hofmann on 25.05.2016.
 */
public class ClassArrayAnnotationEntry
    extends AbstractAnnotationEntry<HxType[], Class<?>[]> {

  private static final SoftReference<Class<?>[]> EMPTY_ARRAY = new SoftReference<>(new Class[0]);
  private volatile Reference<Class<?>[]> classReference;

  public ClassArrayAnnotationEntry(final String name, final HxType... types) {
    super(name, Objects.requireNonNull(types, "Value is null."));
  }

  @Override
  public Class<?>[] original(Class<?> type) {
    Reference<Class<?>[]> reference = this.classReference;
    if (reference == null) {
      HxType[] types = get();
      if (types.length != 0) {
        synchronized (this) {
          if ((reference = this.classReference) == null) {
            reference = loadClasses(types);
          }
        }
      } else {
        this.classReference = reference = EMPTY_ARRAY;
      }
    }
    return reference.get();
  }

  private Reference<Class<?>[]> loadClasses(final HxType[] types) {
    final Reference<Class<?>[]> reference;
    Class<?>[] classes = new Class[types.length];
    for (int i = 0; i < types.length; i++) {
      classes[i] = types[i].loadClass();
    }
    this.classReference = reference = new SoftReference<>(classes);
    return reference;
  }
}
