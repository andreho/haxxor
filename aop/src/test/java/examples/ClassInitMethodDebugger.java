package examples;

import net.andreho.haxxor.Debugger;
import org.junit.jupiter.api.Test;

/**
 * <br/>Created by a.hofmann on 02.10.2017 at 15:11.
 */
public class ClassInitMethodDebugger {
  static {
    {
      double a = 1;
      String s = "Jo: ";
      System.out.println(s + a);
    }
    {
      Integer b = 2;
      System.out.println(b);
    }
  }

  /*
{
mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
mv.visitCode();
mv.visitInsn(DCONST_1);
mv.visitVarInsn(DSTORE, 0);
mv.visitInsn(ICONST_2);
mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
mv.visitVarInsn(ASTORE, 0);
mv.visitInsn(RETURN);
mv.visitMaxs(2, 2);
mv.visitEnd();
}
   */

  @Test
  void print() {
    Debugger.trace(ClassInitMethodDebugger.class, Debugger.SKIP_DEBUG);
  }
}
