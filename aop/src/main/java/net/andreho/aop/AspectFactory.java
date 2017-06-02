package net.andreho.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows to instantiate the matching aspect when it needed and use its member methods as possible interceptors.
 * This annotation must be used either on exactly one <b>public static</b> method or on exactly one <b>public
 * constructor</b> of the matching aspect. The static factory method must return a valid instance of the matching
 * aspect.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface AspectFactory {
}
