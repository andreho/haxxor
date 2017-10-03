package net.andreho.examples.one.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.Modifier;
import net.andreho.examples.one.ExampleBase;

/**
 * <br/>Created by a.hofmann on 28.09.2017 at 02:40.
 */
public class JavassistExample extends ExampleBase {
  public static void main(String[] args) throws Exception {

    ClassPool cp = ClassPool.getDefault();
    CtClass ctClass = cp.makeClass("examples.Main");

    ctClass.setModifiers(Modifier.PUBLIC);
    ctClass.setSuperclass(cp.get("java.lang.Object"));
    ctClass.addConstructor(CtNewConstructor.defaultConstructor(ctClass));

    CtMethod mainMethod =
      CtMethod.make(
        "public static void main(String[] args) {" +
                "System.out.println(\"Hello World!\");" +
            "}"
        , ctClass);
    ctClass.addMethod(mainMethod);

    invokeMainMethod(args, ctClass.toClass());
  }
}
