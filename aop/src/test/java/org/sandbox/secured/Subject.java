package org.sandbox.secured;

import org.sandbox.aspects.security.Secured;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * <br/>Created by a.hofmann on 04.07.2017 at 17:04.
 */
public class Subject {
  private String name;
  private final Set<String> users;

  public Subject(final String name, final String ... users) {
    super();
    this.name = name;
    this.users = new LinkedHashSet<>(Arrays.asList(users));
    //AFTER
  }

  @Secured
  public void setName(final String name) {
    this.name = requireNonNull(name);
  }

  @Secured
  public void addAccessFor(String username) {
    users.add(username);
  }

  @Secured
  public void removeAccessFor(String username) {
    users.remove(username);
  }

  public String getName() {
    return name;
  }

  public boolean isAccessibleFrom(String username) {
    return users.contains(username);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final Subject subject = (Subject) o;

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
