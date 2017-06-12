package net.andreho.haxxor.cgen;

import net.andreho.asm.org.objectweb.asm.ClassReader;
import net.andreho.asm.org.objectweb.asm.ClassVisitor;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.cgen.code_fragments.ArithmeticOperationsFragment;
import net.andreho.haxxor.cgen.code_fragments.ArrayOperationsFragments;
import net.andreho.haxxor.cgen.code_fragments.BinaryOperationsFragment;
import net.andreho.haxxor.cgen.code_fragments.NewOperationsFragment;
import net.andreho.haxxor.spec.api.HxCode;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 04:07.
 */
class CodeTest {

  static List<Class<?>> classes() {
    return Arrays.asList(
        ArithmeticOperationsFragment.class,
        ArrayOperationsFragments.class, BinaryOperationsFragment.class, NewOperationsFragment.class
    );
  }

  @ParameterizedTest
  @MethodSource(names = "classes")
  @DisplayName("Check the stored byte-code for correctness.")
  void codeTest()
  throws IOException {
    final String classname = ArrayOperationsFragments.class.getName();
    final Haxxor haxxor = new Haxxor();
    final HxType type = haxxor.resolve(classname);

    ClassReader classReader = new ClassReader(classname);
    classReader.accept(new ClassVisitor(Opcodes.ASM5) {
      @Override
      public MethodVisitor visitMethod(final int access,
                                       final String name,
                                       final String desc,
                                       final String signature,
                                       final String[] exceptions) {
        HxCode code;
        if("<init>".equals(name)) {
          HxConstructor hxConstructor = type.findConstructorDirectly(desc)
                                            .orElseThrow(IllegalStateException::new);
          code = hxConstructor.getCode();
        } else {
          HxMethod hxMethod = type.findMethodDirectly(name, desc).orElseThrow(IllegalStateException::new);
          code = hxMethod.getCode();
        }

        return new CodeStreamMatcher(code, super.visitMethod(access, name, desc, signature, exceptions));
      }
    }, 0);
  }
}