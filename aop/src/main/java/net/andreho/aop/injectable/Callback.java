package net.andreho.aop.injectable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.Callable;

/**
 * This annotation injects the invokable/callable reference of the original intercepted method code.
 * <br/>Created by a.hofmann on 24.12.2016.
 * @implSpec The receiving parameter must be one of: {@link Runnable} or {@link Callable}
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Callback {
}
