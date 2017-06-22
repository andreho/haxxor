package net.andreho.haxxor.cgen;

import net.andreho.asm.org.objectweb.asm.ClassReader;
import net.andreho.asm.org.objectweb.asm.ClassVisitor;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.cgen.code_fragments.ArithmeticOperationsFragment;
import net.andreho.haxxor.cgen.code_fragments.ArrayOperationsFragments;
import net.andreho.haxxor.cgen.code_fragments.BinaryOperationsFragment;
import net.andreho.haxxor.cgen.code_fragments.CompareFragment;
import net.andreho.haxxor.cgen.code_fragments.ConstantsFragment;
import net.andreho.haxxor.cgen.code_fragments.FieldAccessFragment;
import net.andreho.haxxor.cgen.code_fragments.InvokeFragment;
import net.andreho.haxxor.cgen.code_fragments.JumpOperationsFragement;
import net.andreho.haxxor.cgen.code_fragments.LoadStoreFragment;
import net.andreho.haxxor.cgen.code_fragments.NewOperationsFragment;
import net.andreho.haxxor.cgen.code_fragments.SyncFragment;
import net.andreho.haxxor.cgen.impl.PrintingCodeStream;
import net.andreho.haxxor.model.AnnotationA;
import net.andreho.haxxor.model.AnnotationB;
import net.andreho.haxxor.model.AnnotationC;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxMethodBody;
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
        ArrayOperationsFragments.class,
        BinaryOperationsFragment.class,
        NewOperationsFragment.class,
        ConstantsFragment.class,
        InvokeFragment.class,
        JumpOperationsFragement.class,
        LoadStoreFragment.class,
        FieldAccessFragment.class,
        CompareFragment.class,
        SyncFragment.class,
        AnnotationA.class,
        AnnotationB.class,
        AnnotationC.class
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
        HxMethod hxMethod = type.findMethodDirectly(name, desc).orElseThrow(IllegalStateException::new);
        HxMethodBody code = hxMethod.getBody();
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

        PrintingCodeStream stream = new PrintingCodeStream(System.out);
        for(HxInstruction instruction : code) {
          instruction.visit(stream);
        }

        return new CodeStreamMatcher(code, mv);
      }
    }, 0);
  }
}