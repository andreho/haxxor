package org.sandbox.secured;

import org.sandbox.aspects.security.Secured;
import org.sandbox.aspects.security.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Executable;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <br/>Created by a.hofmann on 04.07.2017 at 17:04.
 */
public class SubjectServiceImpl implements SecurityManager, SubjectService {
  private static final Logger LOG = LoggerFactory.getLogger(SubjectServiceImpl.class);
  private static final ThreadLocal<String> CURRENT_USER_TL = new ThreadLocal<>();
  private static final Map<String, User> LOGGED_IN_USERS = new ConcurrentHashMap<>();

  private static final Object LOCK = new Object();
  private static volatile SubjectServiceImpl SECURITY_MANAGER;

  public static SubjectServiceImpl securityManager() {
    SubjectServiceImpl securityManager = SubjectServiceImpl.SECURITY_MANAGER;

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
  public void verifyAccessTo(final Subject target,
                             final Secured secured,
                             final Executable method)
  throws IllegalAccessException {
    User user = current().orElseThrow(() -> new IllegalAccessException("There aren't any logged in users."));

    LOG.info("Checking access to: '"+target.getName()+"' with user: '"+user.getUsername()+"' via: "+method);

    if(!target.isOwnedBy(user.getUsername())) {
      String message = "Current user '"+user.getUsername()+"' isn't an owner of the given subject: '"+
                 target.getName()+"' and can't access: " + method;

      LOG.error(message);
      throw new IllegalAccessException(message);
    }

    if(!user.hasPermission(secured.value())) {
      String message = "Current user '" + user.getUsername() +
                 "' doesn't have a suitable permission to access the current subject: '" +
                 target.getName() + "' via method: " + method;

      LOG.error(message);
      throw new IllegalAccessException(message);
    }
  }


  private void verifyCredentials(final User user) {
    if(!"123".equals(user.getPassword())) {
      throw new IllegalArgumentException("Invalid password: "+user.getPassword());
    }
  }

  @Override
  public void login(final User user) {
    verifyCredentials(user);
    User current = LOGGED_IN_USERS.get(user.getUsername());
    if(current != null || null != LOGGED_IN_USERS.putIfAbsent(user.getUsername(), user)) {
      throw new IllegalStateException("Concurrent login-attempt or not properly logged out: " +user.getUsername());
    }
    CURRENT_USER_TL.set(user.getUsername());
  }

  @Override
  public void logout(final User user) {
    verifyCredentials(user);

    User current = LOGGED_IN_USERS.get(user.getUsername());
    if(current == null || !LOGGED_IN_USERS.remove(user.getUsername(), current)) {
      throw new IllegalStateException("Concurrent logout-attempt or already logged out: " +user.getUsername());
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
