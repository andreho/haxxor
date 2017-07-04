package examples;

import net.andreho.haxxor.Debugger;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * <br/>Created by a.hofmann on 03.07.2017 at 16:42.
 */
public class ThrowExamples {
  public static String methodThatThrowsIOException() throws IOException {
    final Appendable appendable = new StringBuilder("ok");
    return appendable.toString();
  }
  public static String methodThatHandlesIOException(IOException e) {
    return "not-ok";
  }
  public static String methodThatHandlesFinally() {
    return "finally";
  }

  public static String tryCatchIOException() {
    String[][][] strings = new String[1][2][3];
    int[][][] ints = new int[1][2][3];
    /*
{
mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "tryCatchIOException", "()Ljava/lang/String;", null, null);
mv.visitCode();
Label l0 = new Label();
Label l1 = new Label();
Label l2 = new Label();
mv.visitTryCatchBlock(l0, l1, l2, "java/io/IOException");
mv.visitLabel(l0);
mv.visitLineNumber(22, l0);
mv.visitMethodInsn(INVOKESTATIC, "examples/ThrowExamples", "methodThatThrowsIOException", "()Ljava/lang/String;", false);
mv.visitLabel(l1);
mv.visitInsn(ARETURN);
mv.visitLabel(l2);
mv.visitLineNumber(23, l2);
mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/io/IOException"});
mv.visitVarInsn(ASTORE, 0);
Label l3 = new Label();
mv.visitLabel(l3);
mv.visitLineNumber(24, l3);
mv.visitVarInsn(ALOAD, 0);
mv.visitMethodInsn(INVOKESTATIC, "examples/ThrowExamples", "methodThatHandlesIOException", "(Ljava/io/IOException;)Ljava/lang/String;", false);
mv.visitInsn(ARETURN);
Label l4 = new Label();
mv.visitLabel(l4);
mv.visitLocalVariable("e", "Ljava/io/IOException;", null, l3, l4, 0);
mv.visitMaxs(1, 1);
mv.visitEnd();
}
     */
    try {
      return methodThatThrowsIOException();
    } catch (IOException e) {
      return methodThatHandlesIOException(e);
    } finally {
      methodThatHandlesFinally();
    }
  }

  @Test
  void printByteCode() {
    Debugger.trace(getClass());
  }
}
