package net.andreho.aop.impl.search;

import net.andreho.haxxor.spec.api.HxType;

import java.util.function.Predicate;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:42.
 */
public class ClassSelectorFragment
    implements Predicate<HxType> {

//  Annotated[] annotated() default {};
//  ClassSelector[] value() default {};
//  ClassSelector[] extending() default {};
//  ClassSelector[] implementing() default {};



  @Override
  public boolean test(final HxType type) {
    return false;
  }
}
