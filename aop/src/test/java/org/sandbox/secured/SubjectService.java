package org.sandbox.secured;

import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 04.07.2017 at 17:05.
 */
public interface SubjectService {

  /**
   * @param user
   */
  void login(User user);

  /**
   * @param user
   */
  void logout(User user);

  /**
   *
   */
  Optional<User> current();
}
