package net.andreho.examples.one.asm;

import javassist.CannotCompileException;
import net.andreho.examples.one.ExampleBase;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.InvocationTargetException;

public class CoreAPIExample extends ExampleBase
  implements Opcodes {
  public static void main(String[] args)
  throws CannotCompileException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    ClassWriter cw = new ClassWriter(0);
    cw.visit(V1_8, ACC_PUBLIC | ACC_SUPER,
             "examples/Main", null, "java/lang/Object", null);
    MethodVisitor mv;
    {
      mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC,
                          "main", "([Ljava/lang/String;)V", null, null);
      mv.visitParameter("args", 0);
      mv.visitCode();
      mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
      mv.visitLdcInsn("Hello World!");
      mv.visitMethodInsn(INVOKEVIRTUAL,
                         "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
      mv.visitInsn(RETURN);
      mv.visitMaxs(2, 1);
      mv.visitEnd();
    }
    cw.visitEnd();

    final Class injectedClass =
      injectClassIntoClassLoader("examples.Main", cw.toByteArray());

    invokeMainMethod(args, injectedClass);
  }
}
