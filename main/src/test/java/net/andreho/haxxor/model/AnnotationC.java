package net.andreho.haxxor.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br/>Created by a.hofmann on 01.06.2017 at 23:26.
 */
@Target({ElementType.CONSTRUCTOR,
         ElementType.TYPE,
         ElementType.METHOD,
         ElementType.FIELD,
         ElementType.ANNOTATION_TYPE,
         ElementType.LOCAL_VARIABLE,
         ElementType.PACKAGE,
         ElementType.PARAMETER,
         ElementType.TYPE_PARAMETER,
         ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationC {
  String value();
}
