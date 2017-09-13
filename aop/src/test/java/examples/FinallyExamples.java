package examples;

import net.andreho.aop.api.redefine.Redefinition;
import net.andreho.haxxor.Debugger;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * <br/>Created by a.hofmann on 03.07.2017 at 16:42.
 */
public class FinallyExamples {
  public static String methodThatThrowsIOException() throws IOException {
    final Appendable appendable = new StringBuilder("ok");
    return appendable.toString();
  }
  public static Redefinition<String> methodThatHandlesIOException(IOException e) {
    return Redefinition.as("not-ok");
  }
  public static Redefinition<String> methodThatHandlesNullPointerException(NullPointerException e) {
    return Redefinition.as("not-ok");
  }
  public static Redefinition<String> methodThatHandlesFinally() {
    return Redefinition.as("finally");
  }

  public static String tryCatchIOException() {

    String result = null;
    try {
      result = methodThatThrowsIOException();
    } catch (IOException e) {
      result = methodThatHandlesIOException(e).asObject(result);
    } catch (NullPointerException npe) {
      result = methodThatHandlesNullPointerException(npe).asObject(result);
    } finally {
      result = methodThatHandlesFinally().asObject(result);
    }
    return result;
  }

  @Test
  void printByteCode() {
    Debugger.trace(getClass());
  }
}
