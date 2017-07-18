package examples;

import net.andreho.haxxor.Debugger;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * <br/>Created by a.hofmann on 13.07.2017 at 07:54.
 */
class LambdaExamples {

  @Test
  void test()
  throws Exception {
    createLambda(1d,true, (byte) 1, (short) 1, (char) 1, 1, 1f, 1l, null, null).call();
    Debugger.trace(LambdaExamples.class);
  }

  Callable<String> createLambda(double d, boolean bool, byte b, short s, char c, int i, float f, long l, String o, Serializable ser)
  throws Exception {
    return () -> String.valueOf(o) + d + bool + b + s + c + ser + i + f + l + this;
  }

  static {
    System.out.println("Class-Initialized: "+LambdaExamples.class);
  }
}
