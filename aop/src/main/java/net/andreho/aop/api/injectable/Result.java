package net.andreho.aop.api.injectable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation injects either the current return value of the intercepted method call or
 * a new value that was passed to the writing field access.
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Result {
}
