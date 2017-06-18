package net.andreho.aop.api.injectable;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation injects into a the marked parameter with compatible type,
 * that extends {@link Throwable} type, the currently caught exception
 * if available or <b>null</b> as default value.
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Caught {
}
