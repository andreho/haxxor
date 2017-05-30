package net.andreho.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows to instantiate the matching aspect and use its member methods as further interceptors.
 * This annotation must be used on exactly one <b>public static</b> method of the matching aspect and
 * this method must return a valid instance of the matching aspect.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Factory {
}
