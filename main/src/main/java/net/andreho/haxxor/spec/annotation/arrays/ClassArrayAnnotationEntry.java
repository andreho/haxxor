package net.andreho.haxxor.spec.annotation.arrays;

import net.andreho.haxxor.spec.HxType;
import net.andreho.haxxor.spec.annotation.AbstractAnnotationEntry;

import java.lang.ref.WeakReference;
import java.util.Objects;

/**
 * Created by a.hofmann on 25.05.2016.
 */
public class ClassArrayAnnotationEntry extends AbstractAnnotationEntry<HxType[], Class<?>[]> {
   private static final WeakReference<Class<?>[]> EMPTY_ARRAY = new WeakReference<>(new Class[0]);
   private volatile WeakReference<Class<?>[]> classReference;

   public ClassArrayAnnotationEntry(final String name, final HxType... types) {
      super(name, Objects.requireNonNull(types, "Value is null."));
   }

   @Override
   public Class<?>[] original(Class<?> type) {
      WeakReference<Class<?>[]> reference = this.classReference;
      if (reference == null) {
         HxType[] types = get();
         if (types.length == 0) {
            this.classReference = reference = EMPTY_ARRAY;
         } else {
            Class<?>[] classes = new Class[types.length];
            for (int i = 0; i < types.length; i++) {
               classes[i] = types[i].loadClass();
            }
            this.classReference = reference = new WeakReference<>(classes);
         }
      }
      return reference.get();
   }
}
