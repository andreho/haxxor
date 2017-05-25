package net.andreho.common.utils;

import net.andreho.common.anno.Order;

import java.util.Comparator;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 15:34.
 */
public class OrderUtils implements Comparator<Object> {
   private static final Class<Order> ORDER_ANNOTATION_CLASS = Order.class;
   public static volatile OrderUtils COMPARATOR = new OrderUtils();

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
