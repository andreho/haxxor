package net.andreho.aop.impl.search;

import net.andreho.haxxor.spec.api.HxType;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:42.
 */
public class ClassSetFragment
    implements Predicate<HxType> {

  private final Set<String> classSet;

  public ClassSetFragment(final HxType[] types) {
    this(Arrays.stream(types).map(HxType::getName).collect(Collectors.toSet()));
  }

  public ClassSetFragment(final Collection<String> classSet) {
    this(new HashSet<>(classSet));
  }

  private ClassSetFragment(final Set<String> classSet) {
    this.classSet = classSet;
  }

  @Override
  public boolean test(final HxType type) {
    return classSet.contains(type.getName());
  }
}
