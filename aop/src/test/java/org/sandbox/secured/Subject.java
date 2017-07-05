package org.sandbox.secured;

/**
 * <br/>Created by a.hofmann on 05.07.2017 at 15:09.
 */
public interface Subject {
  String getName();
  boolean isOwnedBy(String username);
}
