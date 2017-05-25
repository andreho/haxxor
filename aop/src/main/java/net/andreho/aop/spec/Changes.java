package net.andreho.aop.spec;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Memorizes a list of changes done due a processing of {@link net.andreho.aop.transform.ClassTransformer}
 * <br/>Created by a.hofmann on 26.09.2015.<br/>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
public @interface Changes {
   /**
    * @return
    */
   Change[] value();
}
