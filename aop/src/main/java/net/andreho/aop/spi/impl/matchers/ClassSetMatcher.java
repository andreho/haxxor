package net.andreho.aop.spi.impl.matchers;

import net.andreho.haxxor.api.HxType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:42.
 */
public class ClassSetMatcher
    extends AbstractMatcher<HxType> {
  private final Set<String> classSet;

  public ClassSetMatcher(final HxType[] types) {
    this(Arrays.stream(types).map(HxType::getName).collect(Collectors.toSet()));
  }

  public ClassSetMatcher(String ... classNames) {
    this(new HashSet<>(Arrays.asList(classNames)));
  }

  public ClassSetMatcher(final Collection<String> classSet) {
    this(new HashSet<>(classSet));
  }

  private ClassSetMatcher(final Set<String> classSet) {
    this.classSet = classSet == null || classSet.isEmpty()? Collections.emptySet() : classSet;
  }

  @Override
  public boolean matches(final HxType type) {
    return classSet.contains(type.getName());
  }

  @Override
  public String toString() {
    return "TYPE_IS_IN "+(classSet.isEmpty()? "[ANY]" : classSet.toString());
  }
}
