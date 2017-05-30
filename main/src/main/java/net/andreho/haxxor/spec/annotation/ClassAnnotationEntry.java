package net.andreho.haxxor.spec.annotation;

import net.andreho.haxxor.spec.HxType;

import java.lang.ref.WeakReference;
import java.util.Objects;

/**
 * Created by a.hofmann on 25.05.2016.
 */
public class ClassAnnotationEntry extends AbstractAnnotationEntry<HxType, Class<?>> {
   private volatile WeakReference<Class<?>> classReference;

   public ClassAnnotationEntry(final String name, final HxType type) {
      super(name, Objects.requireNonNull(type, "Type can't be null."));
   }

   @Override
   public Class<?> original(Class<?> type) {
      WeakReference<Class<?>> reference = this.classReference;
      if (reference == null) {
         Class<?> cls = get().loadClass();
         this.classReference = reference = new WeakReference<>(cls);
      }
      return reference.get();
   }
}
