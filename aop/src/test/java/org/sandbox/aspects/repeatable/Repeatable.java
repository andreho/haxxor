package org.sandbox.aspects.repeatable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * <br/>Created by a.hofmann on 10.07.2017 at 17:12.
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface Repeatable {
}
