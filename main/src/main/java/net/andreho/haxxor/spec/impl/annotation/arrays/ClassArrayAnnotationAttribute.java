package net.andreho.haxxor.spec.impl.annotation.arrays;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;
import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.impl.annotation.AbstractAnnotationAttribute;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

/**
 * Created by a.hofmann on 25.05.2016.
 */
public class ClassArrayAnnotationAttribute
    extends AbstractAnnotationAttribute<HxType[], Class<?>[]> {

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

  @Override
  public HxAnnotationAttribute<HxType[], Class<?>[]> clone() {
    return new ClassArrayAnnotationAttribute(getName(), getValue());
  }
}
