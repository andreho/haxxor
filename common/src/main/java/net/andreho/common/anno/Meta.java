package net.andreho.common.anno;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to create composable meta-annotations using the composition of
 * available annotations on top of the custom one.
 * You may create your own annotations that suit one or more use-cases.
 *
 * <br/>Created by a.hofmann on 29.11.2016 at 06:56.
 *
 * @implSpec In cases when the qualifier value, wasn't explicitly defined,
 * then the following resolution schema applies:
 * <ul>
 * </ul>
 */
@Target({ElementType.METHOD,
         ElementType.CONSTRUCTOR,
         ElementType.FIELD,
         ElementType.PARAMETER,
         ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(MetaSet.class)
public @interface Meta {
   /**
    * Allows to define the target annotation type to that meta binding relates
    * @return a related annotation type (by default {@link Annotation})
    * @see Meta
    */
   Class<? extends Annotation> type() default Annotation.class;

   /**
    * @return an unique qualifying identifier that matches only the marked element
    * @implNote for example, this attribute would be bindable as: <code>net.andreho.common.anno.Meta:value()</code> or
    * any other system-wide unique alias placed at <code>value()</code> in case when the {@link #type()}
    * attribute was set to {@link Meta} annotation class
    * @see Meta
    */
   String value() default "";
}
