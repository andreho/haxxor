package net.andreho.haxxor.spec.impl.annotation;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;
import net.andreho.haxxor.spec.api.HxType;

import java.lang.ref.WeakReference;

/**
 * Created by a.hofmann on 25.05.2016.
 */
public class ClassAnnotationAttribute
    extends AbstractAnnotationAttribute<HxType, Class<?>> {

  private volatile WeakReference<Class<?>> classReference;

  public ClassAnnotationAttribute(final String name, final HxType type) {
    super(name, type);
  }

  @Override
  public Class<?> original(Class<?> type) {
    WeakReference<Class<?>> reference = this.classReference;
    if (reference == null) {
      Class<?> cls = getValue().loadClass();
      this.classReference = reference = new WeakReference<>(cls);
    }
    return reference.get();
  }

  @Override
  public HxAnnotationAttribute<HxType, Class<?>> clone() {
    return new ClassAnnotationAttribute(getName(), getValue());
  }
}
