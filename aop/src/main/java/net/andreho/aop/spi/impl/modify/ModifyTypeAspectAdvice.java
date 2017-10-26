package net.andreho.aop.spi.impl.modify;

import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.impl.advices.AbstractAspectAdvice;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static net.andreho.aop.spi.Helpers.buildKeyFor;
import static net.andreho.aop.spi.Helpers.getMethodOf;

/**
 * <br/>Created by a.hofmann on 05.07.2017 at 18:06.
 */
public class ModifyTypeAspectAdvice
  extends AbstractAspectAdvice<HxType> {

  private final HxMethod interceptor;
  private final Method method;

  private static Class<?> loadClass(final ClassLoader classLoader,
                                    final HxType type) {
    try {
      return Class.forName(type.getName(), true, classLoader);
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException(e);
    }
  }

  private static Method findMethod(final Class<?> referencedClass,
                                   final HxMethod interceptor) {
    return getMethodOf(referencedClass,buildKeyFor(interceptor));
  }

  public ModifyTypeAspectAdvice(final int index,
                                final String profileName,
                                final AspectAdviceType type,
                                final ElementMatcher<HxType> elementMatcher,
                                final HxMethod interceptor) {
    super(type, elementMatcher, profileName); //index,
    this.interceptor = requireNonNull(interceptor);
    this.method = findMethod(loadClass(getClass().getClassLoader(), interceptor.getDeclaringType()), interceptor);
  }

  @Override
  public List<HxMethod> getInterceptors() {
    return Collections.emptyList();
  }

  @Override
  public boolean apply(final AspectContext context,
                       final HxType type) {
    if (!matches(type)) {
      return false;
    }

    try {
      Object result = method.invoke(null, new Object[]{type});
      return Boolean.TRUE.equals(result);

    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new IllegalStateException("Failed to execute @Modify.Type processor: "+method, e);
    }
  }
}
