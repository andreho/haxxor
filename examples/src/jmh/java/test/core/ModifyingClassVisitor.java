package test.core;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import test.LoggableInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class ModifyingClassVisitor extends ClassVisitor implements Opcodes {
  private static final boolean WITH_RETURN = true;
  private static final boolean WITHOUT_RETURN = false;
  private static final String INTERFACE_CLASS = Type.getInternalName(LoggableInterface.class);
  private static final String LOGGER_DESC = Type.getDescriptor(Logger.class);
  private static final String LOG_GETTER_DESC = "()" + LOGGER_DESC;
  private static final String[] EMPTY_ARRAY = new String[0];
  private static final String CLINIT_METHOD = "<clinit>";

  private String className;
  private boolean fieldAdded;
  private boolean clinitAddedOrModified;
  private boolean logMethodAdded;
  private boolean isClass;

  public ModifyingClassVisitor() {
    super(Opcodes.ASM5);
  }
  public ModifyingClassVisitor(final ClassVisitor cv) {
    super(Opcodes.ASM5, cv);
  }

  @Override
  public void visit(final int version,
                    final int access,
                    final String name,
                    final String signature,
                    final String superName,
                    String[] interfaces) {
    this.isClass = (ACC_INTERFACE & access) == 0;
    this.className = name;
    if(isClass) {
      interfaces = extendInterfaceList(interfaces);
    }
    super.visit(version, access, name, signature, superName, interfaces);
  }

  private String[] extendInterfaceList(final String[] interfaces) {
    final List<String> list = new ArrayList<>(
      Arrays.asList(interfaces == null ? EMPTY_ARRAY : interfaces));
    list.add(INTERFACE_CLASS);
    return list.toArray(EMPTY_ARRAY);
  }

  private void tryToAddLogField() {
    if(!this.fieldAdded) {
      this.fieldAdded = true;
      visitField(
        ACC_PRIVATE | ACC_STATIC | ACC_FINAL,
        "$LOG",
        LOGGER_DESC,
        null,
        null
      );
    }
  }

  private void tryToAddClinitMethod() {
    if(!this.clinitAddedOrModified) {
      this.clinitAddedOrModified = true;
      final MethodVisitor clinit = new ClinitExtendingMethodVisitor(
        super.visitMethod(
          ACC_STATIC,
          CLINIT_METHOD,
          "()V",
          null,
          null
        ),
        className,
        WITH_RETURN
      );
      clinit.visitCode();
      clinit.visitMaxs(0,0);
      clinit.visitEnd();
    }
  }

  private void tryToAddLogMethod() {
    if(!this.logMethodAdded) {
      this.logMethodAdded = true;
      MethodVisitor logGetter =
        visitMethod(
          ACC_PUBLIC,
          "log",
          LOG_GETTER_DESC,
          null,
          null
        );
      logGetter.visitCode();
      logGetter.visitFieldInsn(GETSTATIC, className, "$LOG", LOGGER_DESC);
      logGetter.visitInsn(ARETURN);
      logGetter.visitMaxs(0,0);
      logGetter.visitEnd();
    }
  }

  @Override
  public MethodVisitor visitMethod(final int access,
                                   final String name,
                                   final String desc,
                                   final String signature,
                                   final String[] exceptions) {
    if(isClass && CLINIT_METHOD.equals(name)) {
      this.clinitAddedOrModified = true;
      return new ClinitExtendingMethodVisitor(
        super.visitMethod(
          access,
          name,
          desc,
          signature,
          exceptions
        ),
        className,
        WITHOUT_RETURN
      );
    }
    return super.visitMethod(access, name, desc, signature, exceptions);
  }

  @Override
  public void visitEnd() {
    if(isClass) {
      tryToAddLogField();
      tryToAddClinitMethod();
      tryToAddLogMethod();
    }
    super.visitEnd();
  }
}
