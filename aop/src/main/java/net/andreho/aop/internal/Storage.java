package net.andreho.aop.internal;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Arrays;

/**
 * <br/>Created by a.hofmann on 21.09.2015.<br/>
 */
public class Storage {
   private static Reference<?>[] STORAGE = new Reference[64];

   private static int size = 0;

   /**
    * @return
    */
   public static int size() {
      return size;
   }

   /**
    * @param ref
    * @return
    */
   public synchronized static int add(Reference<?> ref) {
      int size = Storage.size;

      if (size >= STORAGE.length) {
         STORAGE = Arrays.copyOf(STORAGE, STORAGE.length * 2);
      }

      STORAGE[size] = ref;

      return Storage.size++;
   }

   /**
    * @param value
    * @return
    */
   public static int add(Object value) {
      return add(new SoftReference<>(value));
   }

   /**
    * @param id
    * @param value
    */
   public synchronized static void set(int id, Object value) {
      set(id, new SoftReference<>(value));
   }

   /**
    * @param id
    * @param ref
    */
   public synchronized static void set(int id, Reference<?> ref) {
      STORAGE[id] = ref;
   }

   /**
    * @param id
    * @return
    * @throws IndexOutOfBoundsException
    */
   public static Object get(int id) {
      return STORAGE[id].get();
   }
}
