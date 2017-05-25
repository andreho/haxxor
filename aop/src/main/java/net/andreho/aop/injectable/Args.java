package net.andreho.aop.injectable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation injects an <b>Object[]</b> array containing all passed parameters into marked parameter
 * of the intercepted method call.<br/>
 * Accessing <code>length</code> of this array is equal to the arity of the intercepted method.
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Args {
}
