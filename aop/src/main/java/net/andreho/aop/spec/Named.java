package net.andreho.aop.spec;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows to select elements by their name using either ANT-style for matching or Java
 * {@link java.util.regex.Pattern regexp} syntax.
 * Created by a.hofmann on 20.05.2016.
 * @see java.util.regex.Pattern
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Named {
   /**
    * @return a pattern for name selection. This can be either an ANT-style selector or Java {@link java.util.regex.Pattern regexp}.
    * @implNote for example: <code>'my.company.foo.*.bar'</code>, <code>'set?*Service'</code> or as RegExp: <code>'my\.domain\.services\..*'</code>
    */
   String value() default "*";

   /**
    * @return <b>true</b> if this name-selector must be treated as plain {@link java.util.regex.Pattern regexp} or <b>false</b>
    * to treat this name-selector as a lightweight selector with minimal regexp support (e.g.: <code>*</code> or <code>?</code>).
    */
   boolean asRegExp() default false;
}
