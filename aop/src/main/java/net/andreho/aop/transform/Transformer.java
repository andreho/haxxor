package net.andreho.aop.transform;

import net.andreho.aop.spec.ClassRef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation automatically registers the marked implementation of {@link ClassTransformer} interface.
 * @implSpec This annotation <b>MUST</b> be directly used to register the marked class-transformer on instantiable
 * implementation class that implements {@link ClassTransformer} interface.<br/>
 * There are also packages that are not inspected (escaped via <u>hardcoded</u> prefix matching):<br/>
 * <ul>
 * <li>java</li>
 * <li>javax</li>
 * <li>sun</li>
 * <li>com/sun</li>
 * <li>com/intellij</li>
 * <li>net/andreho/aop</li>
 * <li>net/andreho/haxxor</li>
 * </ul>
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 * @see net.andreho.aop.transform.ordering.Order
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Transformer {
   /**
    * @return
    */
   ClassRef[] value() default {};
}
