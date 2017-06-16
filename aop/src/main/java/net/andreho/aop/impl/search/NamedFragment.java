package net.andreho.aop.impl.search;

import net.andreho.haxxor.spec.api.HxNamed;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:42.
 */
public class NamedFragment
    implements Predicate<HxNamed> {

  private final Pattern pattern;

  public NamedFragment(final String pattern,
                       final boolean asRegExp) {
//    if(asRegExp) {
    this.pattern = Pattern.compile(pattern);
//    } else {
//    }
  }

  @Override
  public boolean test(final HxNamed hxNamed) {
    return pattern.matcher(hxNamed.getName()).matches();
  }
}
