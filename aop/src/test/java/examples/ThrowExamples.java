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
    String result = null;
    /*
{
mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "tryCatchIOException", "()Ljava/lang/String;", null, null);
mv.visitCode();
Label l0 = new Label();
Label l1 = new Label();
Label l2 = new Label();
mv.visitTryCatchBlock(l0, l1, l2, "java/io/IOException");
Label l3 = new Label();
mv.visitLabel(l3);
mv.visitLineNumber(24, l3);
mv.visitInsn(ACONST_NULL);
mv.visitVarInsn(ASTORE, 0);
mv.visitLabel(l0);
mv.visitLineNumber(56, l0);
mv.visitMethodInsn(INVOKESTATIC, "examples/ThrowExamples", "methodThatThrowsIOException", "()Ljava/lang/String;", false);
mv.visitVarInsn(ASTORE, 0);
mv.visitLabel(l1);
mv.visitLineNumber(59, l1);
Label l4 = new Label();
mv.visitJumpInsn(GOTO, l4);
mv.visitLabel(l2);
mv.visitLineNumber(57, l2);
mv.visitFrame(Opcodes.F_FULL, 1, new Object[] {"java/lang/String"}, 1, new Object[] {"java/io/IOException"});
mv.visitVarInsn(ASTORE, 1);
Label l5 = new Label();
mv.visitLabel(l5);
mv.visitLineNumber(58, l5);
mv.visitVarInsn(ALOAD, 1);
mv.visitMethodInsn(INVOKESTATIC, "examples/ThrowExamples", "methodThatHandlesIOException", "(Ljava/io/IOException;)Ljava/lang/String;", false);
mv.visitVarInsn(ASTORE, 0);
mv.visitLabel(l4);
mv.visitLineNumber(60, l4);
mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
mv.visitVarInsn(ALOAD, 0);
mv.visitInsn(ARETURN);
Label l6 = new Label();
mv.visitLabel(l6);
mv.visitLocalVariable("e", "Ljava/io/IOException;", null, l5, l4, 1);
mv.visitLocalVariable("result", "Ljava/lang/String;", null, l0, l6, 0);
mv.visitMaxs(1, 2);
mv.visitEnd();
}
     */
    try {
      result = methodThatThrowsIOException();
    } catch (IOException e) {
      result = methodThatHandlesIOException(e);
    }
    return result;
//    finally {
//      methodThatHandlesFinally();
//    }
  }

  @Test
  void printByteCode() {
    Debugger.trace(getClass());
  }
}
