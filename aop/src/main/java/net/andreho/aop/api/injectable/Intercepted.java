package net.andreho.aop.api.injectable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * This annotation injects either {@link Method method} reference of currently intercepted method's invocation
 * or {@link Constructor constructor} reference of currently intercepted constructor's invocation
 * or {@link Field field} for an intercepted field's access into the marked parameter. <br/>
 *
 * Because of this diversity you may want to use either one of compatible types above
 * and define for each case an appropriate interceptor
 * or use {@link Executable} or even more abstract {@link Member} as parameter type.
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 * @see Method
 * @see Field
 * @see Constructor
 * @see Executable
 * @see Member
 * @see Parameter
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Intercepted {
}
