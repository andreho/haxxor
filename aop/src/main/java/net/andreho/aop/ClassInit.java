package net.andreho.aop;

import net.andreho.aop.injectable.Declaring;
import net.andreho.aop.injectable.Line;
import net.andreho.aop.injectable.Marker;
import net.andreho.aop.spec.Supports;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation allows to modify or extend the <b>&lt;clinit&gt;</b> method
 * (aka: <b>static class initializer</b>) with additional logic.
 * <br/>Created by a.hofmann on 24.03.2017 at 00:15.
 */
@Supports({
    Marker.class,
    Declaring.class,
    Line.class})
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassInit {

}
