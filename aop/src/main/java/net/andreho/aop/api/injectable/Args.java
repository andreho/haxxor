package net.andreho.aop.api.injectable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation injects an <b>Object[]</b> array containing all passed parameters into marked parameter
 * of the intercepted method call.<br/>
 * Accessing <code>length</code> of this array is equal to the arity of the intercepted method.<br/>
 * Users may alternatively use: {@code net.andreho.args.Arguments} as parameter's type to minimize the amount of produced garbage.
 * In order to do so the <code>arguments</code> module must be present in the classpath.
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Args {
}
