package org.sandbox.secured;

import org.sandbox.aspects.security.SecurityManager;

import java.lang.reflect.Executable;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <br/>Created by a.hofmann on 04.07.2017 at 17:04.
 */
public class SubjectServiceImpl implements SecurityManager, SubjectService {
  private static final ThreadLocal<String> CURRENT_USER_TL = new ThreadLocal<>();
  private static final Map<String, User> LOGGED_IN_USERS = new ConcurrentHashMap<>();

  private static final Object LOCK = new Object();
  private static volatile SecurityManager SECURITY_MANAGER;

  public static SecurityManager securityManager() {
    SecurityManager securityManager = SubjectServiceImpl.SECURITY_MANAGER;

    if(securityManager == null) {
      synchronized (LOCK) {
        securityManager = SubjectServiceImpl.SECURITY_MANAGER;
        if(securityManager == null) {
          SubjectServiceImpl.SECURITY_MANAGER = securityManager = new SubjectServiceImpl();
        }
      }
    }
    return securityManager;
  }

  @Override
  public void checkAccessTo(final Object target,
                            final Executable method) {
    System.out.println("Checking access to: "+target+" via: "+method);
  }

  @Override
  public void login(final String username,
                    final String password) {
    if(!"123".equals(password)) {
      throw new IllegalArgumentException("Invalid password: "+password);
    }
    User user = LOGGED_IN_USERS.get(username);
    if(user == null || null != LOGGED_IN_USERS.putIfAbsent(username, user = new User(username, password))) {
      throw new IllegalStateException("Concurrent login-attempt or not properly logged out: " +username);
    }
    CURRENT_USER_TL.set(username);
  }

  @Override
  public void logout(final String username,
                     final String password) {
    if(!"123".equals(password)) {
      throw new IllegalArgumentException("Invalid password: "+password);
    }
    User user = LOGGED_IN_USERS.get(username);
    if(user == null || !LOGGED_IN_USERS.remove(username, user)) {
      throw new IllegalStateException("Concurrent logout-attempt or already logged out: " +username);
    }
    CURRENT_USER_TL.set(null);
  }

  @Override
  public Optional<User> current() {
    final String username = CURRENT_USER_TL.get();
    return username == null?
           Optional.empty() :
           Optional.ofNullable(LOGGED_IN_USERS.get(username));
  }
}
