package net.andreho.haxxor.api.impl.annotation;

import net.andreho.haxxor.api.HxAnnotationAttribute;
import net.andreho.haxxor.api.HxType;

import java.lang.ref.WeakReference;

/**
 * Created by a.hofmann on 25.05.2016.
 */
public class ClassAnnotationAttribute
    extends AbstractAnnotationAttribute<HxType, Class<?>> {

  private volatile WeakReference<Class<?>> classReference;

  public ClassAnnotationAttribute(final String name,
                                  final HxType value) {
    super(name, value);
  }

  @Override
  public Class<?> original(Class<?> type) {
    WeakReference<Class<?>> reference = this.classReference;
    if (reference == null) {
      try {
        Class<?> cls = getValue().loadClass(type.getClassLoader());
        this.classReference = reference = new WeakReference<>(cls);
      } catch (ClassNotFoundException ex) {
        throw new IllegalStateException("Given class-loader was unable to load the given enum-class: " + getValue(),
                                        ex);
      }
    }
    return reference.get();
  }

  @Override
  public HxAnnotationAttribute<HxType, Class<?>> clone() {
    return new ClassAnnotationAttribute(getName(), getValue());
  }
}
