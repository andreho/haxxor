package net.andreho.haxxor.spec.entries;

/**
 * <br/>Created by andreho on 4/4/16 at 10:42 PM.<br/>
 */
public interface AnnotationEntry<V, T> {
   /**
    * @return
    */
   String getName();

   /**
    * @return
    */
   V get();

   /**
    * @param value
    */
   void set(V value);

   /**
    * @return value that is compatible with attribute's type of
    * the referenced {@link java.lang.annotation.Annotation annotation}
    */
   T original();
}
