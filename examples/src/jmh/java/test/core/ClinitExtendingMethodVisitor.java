package test.core;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.logging.Logger;

/**
 * <br/>Created by a.hofmann on 25.11.2017 at 16:31.
 */
public class ClinitExtendingMethodVisitor extends MethodVisitor implements Opcodes {
  private static final String LOGGER_DESC = Type.getDescriptor(Logger.class);
  private static final String LOGGER_INTERNAL_CLASS = Type.getInternalName(Logger.class);
  private static final String GET_LOGGER_METHOD = "getLogger";
  private static final String GET_LOGGER_DESC =
    Type.getMethodDescriptor(Type.getType(Logger.class), Type.getType(String.class));

  private final String className;
  private final boolean withReturn;

  public ClinitExtendingMethodVisitor(String className, boolean withReturn) {
    super(ASM5);
    this.className = className;
    this.withReturn = withReturn;
  }
  public ClinitExtendingMethodVisitor(MethodVisitor mv, String className, boolean withReturn) {
    super(ASM5, mv);
    this.className = className;
    this.withReturn = withReturn;
  }

  @Override
  public void visitCode() {
    super.visitCode();
    visitLdcInsn(className.replace('/', '.'));
    visitMethodInsn(INVOKESTATIC, LOGGER_INTERNAL_CLASS, GET_LOGGER_METHOD, GET_LOGGER_DESC, false);
    visitFieldInsn(PUTSTATIC, className, "$LOG", LOGGER_DESC);
    if(withReturn) {
      visitInsn(RETURN);
    }
  }
}
