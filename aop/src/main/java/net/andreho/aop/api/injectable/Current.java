package net.andreho.aop.api.injectable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation injects the current (or previous) value
 * of the field being intercepted with either a READ or WRITE access
 * into the marked parameter with most compatible type or {@link Object}.<br/>
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Current {
}
