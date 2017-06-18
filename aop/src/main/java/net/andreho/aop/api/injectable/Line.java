package net.andreho.aop.api.injectable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation injects into an <b>int</b> parameter current line number
 * provided by the visited byte code.
 * @implNote if the debug information isn't available for current byte-code
 * then the marked parameter receives: <b>-1</b>
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Line {
}
