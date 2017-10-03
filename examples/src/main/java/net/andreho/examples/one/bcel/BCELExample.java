package net.andreho.examples.one.bcel;

import net.andreho.examples.one.ExampleBase;
import org.apache.bcel.Const;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.GETSTATIC;
import org.apache.bcel.generic.INVOKEVIRTUAL;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.RETURN;
import org.apache.bcel.generic.Type;

public class BCELExample extends ExampleBase {
  public static void main(String[] args) throws Exception {
    ClassGen classGen = new ClassGen(
      "examples.Main",
      "java.lang.Object",
      "Main.java",
      Const.ACC_PUBLIC,
      null
    );

    ConstantPoolGen cp = classGen.getConstantPool();
    InstructionList il = generateByteCode(cp);

    MethodGen methodGen = new MethodGen(
      Const.ACC_PUBLIC | Const.ACC_STATIC, Type.VOID,
      new Type[]{new ArrayType(Type.STRING, 1)},
      new String[]{"args"}, "main", "examples.Main",
      il, cp);
    methodGen.setMaxLocals();
    methodGen.setMaxStack();

    classGen.addMethod(methodGen.getMethod());

    JavaClass javaClass = classGen.getJavaClass();
    Class aClass = injectClassIntoClassLoader(javaClass.getClassName(),
                                              javaClass.getBytes(),
                                              BCELExample.class.getClassLoader(),
                                              BCELExample.class.getProtectionDomain());

    invokeMainMethod(args, aClass);
  }

  private static InstructionList generateByteCode(final ConstantPoolGen cp) {
    InstructionList instructionList = new InstructionList();
    instructionList.append(new GETSTATIC(
      cp.addFieldref(
        "java.lang.System",
        "out",
        "Ljava/io/PrintStream;")));
    instructionList.append(new LDC(
      cp.addString("Hello World!"))
    );
    instructionList.append(new INVOKEVIRTUAL(
      cp.addMethodref(
        "java.io.PrintStream",
        "println",
        "(Ljava/lang/String;)V"))
    );
    instructionList.append(new RETURN());
    return instructionList;
  }
}
