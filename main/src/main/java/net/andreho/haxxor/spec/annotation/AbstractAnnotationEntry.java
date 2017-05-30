package net.andreho.haxxor.spec.annotation;

/**
 * Created by a.hofmann on 21.05.2016.
 */
public abstract class AbstractAnnotationEntry<V, T> implements AnnotationEntry<V, T> {
   private String name;
   private V value;

   public AbstractAnnotationEntry(final String name, final V value) {
      this.name = name;
      this.value = value;
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public V get() {
      return value;
   }

   @Override
   public void set(final V value) {
      this.value = value;
   }

   @Override
   public T original(Class<?> type) {
      return (T) get();
   }

   @Override
   public String toString() {
      return getName() + ": " + get();
   }
}
