package net.andreho.examples.one.asm;

import net.andreho.examples.one.ExampleBase;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * <br/>Created by a.hofmann on 01.10.2017 at 06:47.
 */
public class TreeAPIExample extends ExampleBase implements Opcodes {
  public static void main(String[] args) throws Exception {
    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
    ClassNode cn = new ClassNode();

    cn.version = V1_8;
    cn.access = ACC_PUBLIC | ACC_SUPER;
    cn.name = "examples/Main";
    cn.superName = "java/lang/Object";

    MethodNode mainMethod =
      new MethodNode(ACC_PUBLIC + ACC_STATIC,
                     "main",
                     "([Ljava/lang/String;)V",
                     null,
                     null);

    InsnList il = mainMethod.instructions;
    il.add(new FieldInsnNode(
      GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
    il.add(new LdcInsnNode("Hello World!"));
    il.add(new MethodInsnNode(
      INVOKEVIRTUAL,
      "java/io/PrintStream","println","(Ljava/lang/String;)V", false));
    il.add(new InsnNode(RETURN));

    cn.methods.add(mainMethod);
    cn.accept(cw);

    final Class injectedClass =
      injectClassIntoClassLoader("examples.Main", cw.toByteArray());

    invokeMainMethod(args, injectedClass);
  }
}
