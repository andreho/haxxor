package examples.replayable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 22:16.
 */
public class ReplayableException extends RuntimeException {
   private final Executable executable;
   private final Object host;
   private final Object[] arguments;
   private final int line;

   public ReplayableException(final Throwable cause,
                              final Executable executable,
                              final Object host,
                              final Object[] arguments,
                              final int line) {
      super(cause);
      this.executable = executable;
      this.host = host;
      this.arguments = arguments;
      this.line = line;
   }

   public Object replay() throws InvocationTargetException, IllegalAccessException, InstantiationException {
      if(this.executable instanceof Constructor) {
         return ((Constructor) executable).newInstance(this.arguments);
      }
      return ((Method) this.executable).invoke(this.host, this.arguments);
   }

   public Executable getExecutable() {
      return executable;
   }

   public Object getHost() {
      return host;
   }

   public Object[] getArguments() {
      return arguments;
   }

   public int getLine() {
      return line;
   }

   @Override
   public String toString() {
      return "ReplayableException {\n\t" +
             "cause: " + getCause() + ",\n\t" +
             "host: " + safelyToString(getHost()) + ",\n\t" +
             "executable: " + getExecutable().toString() + ",\n\t" +
             "arguments: " + printSafelyArguments() + ",\n\t" +
             "line: " + getLine() + "\n}";
   }

   private String printSafelyArguments() {
      return Arrays.stream(getArguments())
                   .map(this::safelyToString)
                   .collect(Collectors.joining(",", "[", "]"));
   }

   private String safelyToString(Object o) {
      if(o == null) {
         return "null";
      }
      try {
         return o.toString();
      } catch (Throwable t) {
         return "<failure>: " + t.getMessage();
      }
   }
}
