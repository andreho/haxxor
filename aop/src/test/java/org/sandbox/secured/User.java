package org.sandbox.secured;

/**
 * <br/>Created by a.hofmann on 04.07.2017 at 17:08.
 */
public class User {
  private final String username;
  private final String password;

  public User(final String username,
              final String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final User user = (User) o;

    if (!username.equals(user.username)) {
      return false;
    }
    return password.equals(user.password);
  }

  @Override
  public int hashCode() {
    int result = username.hashCode();
    result = 31 * result + password.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() +" {" +
           "username='" + username + '\'' +
           ", password='" + password + '\'' +
           '}';
  }
}
