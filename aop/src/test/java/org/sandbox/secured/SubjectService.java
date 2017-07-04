package org.sandbox.secured;

import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 04.07.2017 at 17:05.
 */
public interface SubjectService {

  /**
   * @param username
   * @param password
   */
  void login(String username, String password);

  /**
   * @param username
   * @param password
   */
  void logout(String username, String password);

  /**
   *
   */
  Optional<User> current();
}
