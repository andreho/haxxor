package net.andreho.aop.transform.ordering;

import java.util.Comparator;
import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 15:34.
 */
public class OrderUtils implements Comparator<Object> {
   private static final Class<Order> ORDER_ANNOTATION_CLASS = Order.class;
   private static volatile Comparator COMPARATOR = new OrderUtils();

   /**
    * Allows to override the default comparator instance
    * @param comparator
    */
   public static void overrideDefault(Comparator comparator) {
      COMPARATOR = Objects.requireNonNull(comparator, "Given comparator can't be null.");
   }

   /**
    * @param <T>
    * @return the
    */
   public static <T> Comparator<T> comparator() {
      return COMPARATOR;
   }

   /**
    * Calculates the ordering value according to given values
    * @param a to compare
    * @param b to compare
    * @return
    * @see Comparator
    */
   public static int order(Object a, Object b) {
      return COMPARATOR.compare(a, b);
   }

   @Override
   public int compare(final Object a, final Object b) {
      final Class<Order> orderAnnotationClass = ORDER_ANNOTATION_CLASS;

      if(a.getClass().isAnnotationPresent(orderAnnotationClass) ||
         b.getClass().isAnnotationPresent(orderAnnotationClass)) {

         final Order ao = a.getClass().getAnnotation(orderAnnotationClass);
         final Order bo = b.getClass().getAnnotation(orderAnnotationClass);

         if(ao != null && bo != null) {
            return Integer.compare(ao.value(), bo.value());
         } else if(ao != null) {
            return -1;
         } else if(bo != null) {
            return 1;
         }
      }

      return fallback(a, b);
   }

   protected int fallback(final Object a, final Object b) {
      return 0;
   }
}
