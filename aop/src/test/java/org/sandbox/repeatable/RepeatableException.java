package org.sandbox.repeatable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <br/>Created by a.hofmann on 10.07.2017 at 17:39.
 */
public class RepeatableException extends RuntimeException {
  private final Object target;
  private final Executable executable;
  private final Object[] arguments;

  public static boolean isRepeatable(Throwable throwable) {
    return throwable.getClass() == RepeatableException.class;
  }

  public RepeatableException(final Throwable cause,
                             final Object target,
                             final Executable executable,
                             final Object[] arguments) {
    super(cause);
    this.target = target;
    this.executable = executable;
    this.arguments = arguments;
  }

  public Object getTarget() {
    return target;
  }

  public Executable getExecutable() {
    return executable;
  }

  public Object[] getArguments() {
    return arguments.clone();
  }

  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }

  public <T> T repeat()
  throws IllegalAccessException, InvocationTargetException, InstantiationException {
    Executable executable = this.executable;
    if(executable instanceof Constructor) {
      return (T) ((Constructor) executable).newInstance(arguments);
    }
    return (T) ((Method) executable).invoke(target, arguments);
  }
}
