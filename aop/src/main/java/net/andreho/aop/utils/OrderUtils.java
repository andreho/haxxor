package net.andreho.aop.utils;

import net.andreho.aop.api.Order;

import java.util.Comparator;
import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 15:34.
 */
public class OrderUtils
    implements Comparator<Object> {

  private static final Class<Order> ORDER_ANNOTATION_CLASS = Order.class;
  private static volatile Comparator COMPARATOR = new OrderUtils();

  @Override
  public int compare(final Object a,
                     final Object b) {
    final Class<Order> orderAnnotationClass = ORDER_ANNOTATION_CLASS;

    int aoIndex = Integer.MAX_VALUE;
    int boIndex = Integer.MAX_VALUE;

    if (a.getClass().isAnnotationPresent(orderAnnotationClass)) {
      aoIndex = getOrderAnnotation(a, orderAnnotationClass).value();
    }

    if (b.getClass().isAnnotationPresent(orderAnnotationClass)) {
      boIndex = getOrderAnnotation(b, orderAnnotationClass).value();
    }

    if(aoIndex == boIndex) {
      return fallback(a, b);
    }

    return Integer.compare(aoIndex, boIndex);
  }

  private Order getOrderAnnotation(final Object a,
                                   final Class<Order> orderAnnotationClass) {
    return a.getClass().getAnnotation(orderAnnotationClass);
  }

  protected int fallback(final Object a,
                         final Object b) {
    return 0;
  }

  /**
   * Allows to override the default comparator instance
   *
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
   *
   * @param a to compare
   * @param b to compare
   * @return
   * @see Comparator
   */
  public static int order(Object a,
                          Object b) {
    return COMPARATOR.compare(a, b);
  }
}
