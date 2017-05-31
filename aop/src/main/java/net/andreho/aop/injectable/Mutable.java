package net.andreho.aop.injectable;

import net.andreho.haxxor.spec.api.HxType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation injects the mutable representation of the matched type.<br/>
 * The marked parameter must have: {@link HxType} type
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mutable {
}
