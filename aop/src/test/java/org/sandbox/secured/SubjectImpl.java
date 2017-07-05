package org.sandbox.secured;

import org.sandbox.aspects.security.Secured;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <br/>Created by a.hofmann on 04.07.2017 at 17:04.
 */
public class SubjectImpl implements Subject {

  public static final String SUBJECT_CREATION = "subject.creation";
  public static final String OWNERSHIP_REMOVAL = "subject.ownership.remove";
  public static final String OWNERSHIP_ADDITION = "subject.ownership.add";

  private String name;
  private final Set<String> owners;

  @Secured(SUBJECT_CREATION)
  public SubjectImpl(final String name, final String ... owners) {
    super();
    this.name = name;
    this.owners = new LinkedHashSet<>(Arrays.asList(owners));
  }

  @Override
  public String getName() {
    return name;
  }

  @Secured(OWNERSHIP_ADDITION)
  public final void addOwnership(final String username) {
    owners.add(username);
  }

  @Secured(OWNERSHIP_REMOVAL)
  public final void removeOwnership(final String username) {
    owners.remove(username);
  }

  @Override
  public final boolean isOwnedBy(String username) {
    return owners.contains(username);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final SubjectImpl subject = (SubjectImpl) o;

    return name.equals(subject.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + " {name='" + name + "'}";
  }
}
