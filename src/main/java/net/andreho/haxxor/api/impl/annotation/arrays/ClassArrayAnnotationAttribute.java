package net.andreho.haxxor.api.impl.annotation.arrays;

import net.andreho.haxxor.api.HxAnnotationAttribute;
import net.andreho.haxxor.api.HxType;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Arrays;

/**
 * Created by a.hofmann on 25.05.2016.
 */
public class ClassArrayAnnotationAttribute
    extends AbstractArrayAnnotationAttribute<HxType[], Class<?>[]> {

  private static final SoftReference<Class<?>[]> EMPTY_ARRAY = new SoftReference<>(new Class[0]);
  private volatile Reference<Class<?>[]> classReference;

  public ClassArrayAnnotationAttribute(final String name, final HxType[] types) {
    super(name, types);
  }

  @Override
  public Class<?>[] original(Class<?> type) {
    Reference<Class<?>[]> reference = this.classReference;
    if (reference == null) {
      HxType[] types = getValue();
      if (types.length != 0) {
        synchronized (this) {
          if ((reference = this.classReference) == null) {
            reference = findClasses(type.getClassLoader(), types);
          }
        }
      } else {
        this.classReference = reference = EMPTY_ARRAY;
      }
    }
    return reference.get();
  }

  @Override
  public boolean hasValue(final Object o) {
    if(o instanceof HxType[]) {
      HxType[] array = (HxType[]) o;
      return Arrays.equals(getValue(), array);
    }
    return false;
  }

  private Reference<Class<?>[]> findClasses(final ClassLoader classLoader,
                                            final HxType[] types) {
    final Reference<Class<?>[]> reference;
    Class<?>[] classes = new Class[types.length];
    for (int i = 0; i < types.length; i++) {
      try {
        final String className = types[i].getName();
        classes[i] = Class.forName(className, false, classLoader);
      } catch (ClassNotFoundException e) {
        throw new IllegalStateException(e);
      }
    }
    this.classReference = reference = new SoftReference<>(classes);
    return reference;
  }

  @Override
  public HxAnnotationAttribute<HxType[], Class<?>[]> clone() {
    return new ClassArrayAnnotationAttribute(getName(), getValue());
  }
}
