package net.andreho.aop.api.injectable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.Callable;

/**
 * This annotation injects the invokable/callable reference to the original intercepted method code.
 * <br/>Created by a.hofmann on 24.12.2016.
 * @apiNote The receiving parameter must have {@link Callable} type
 * @see net.andreho.aop.api.Around
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Original {
}
