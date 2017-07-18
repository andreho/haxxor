package net.andreho.aop.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows to modify returned result-value of an intercepted method invocation.
 *
 * @implSpec the marked intercepting method should return <s>either an expected type-compatible value or</s>
 * a not-null {@link net.andreho.aop.api.redefine.Redefinition} instance.
 * @implNote this annotation can only be used in conjunction
 * with {@link Before}, {@link After} or {@link Catch} annotations
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD}) //ElementType.FIELD
public @interface Redefine {
}
