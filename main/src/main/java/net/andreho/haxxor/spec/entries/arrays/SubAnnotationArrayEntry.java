package net.andreho.haxxor.spec.entries.arrays;

import net.andreho.haxxor.spec.HxAnnotation;
import net.andreho.haxxor.spec.entries.AbstractAnnotationEntry;

import java.lang.annotation.Annotation;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class SubAnnotationArrayEntry extends AbstractAnnotationEntry<HxAnnotation[], Annotation[]> {
   private Annotation[] annotations;

   public SubAnnotationArrayEntry(final String name, final HxAnnotation... values) {
      super(name, values);
   }

   @Override
   public Annotation[] original() {
      if (this.annotations == null) {
         final Annotation[] array = this.annotations = new Annotation[get().length];
         for (int i = 0; i < array.length; i++) {
            array[i] = get()[i].getView();
         }
      }
      return this.annotations;
   }
}
