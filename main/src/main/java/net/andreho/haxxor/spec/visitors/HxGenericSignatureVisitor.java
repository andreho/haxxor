package net.andreho.haxxor.spec.visitors;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.signature.SignatureVisitor;

/**
 * <br/>Created by a.hofmann on 07.06.2017 at 16:46.
 */
public class HxGenericSignatureVisitor
    extends SignatureVisitor {

  private int depth;

  /**
   * Constructs a new {@link SignatureVisitor}.
   */
  public HxGenericSignatureVisitor() {
    super(Opcodes.ASM5);
  }

  private void print(String s) {
    StringBuilder builder = new StringBuilder();
    for(int i = 0; i<depth; i++) {
      builder.append("| ");
    }
    System.out.println(builder.append(s));
  }
  private void ascend(String s) {
    depth--;
    print(s);
  }
  private void descend(String s) {
    print(s);
    depth++;
  }

  @Override
  public void visitFormalTypeParameter(final String name) {
    print("visitFormalTypeParameter(" + name + ")");
    super.visitFormalTypeParameter(name);
  }

  @Override
  public SignatureVisitor visitClassBound() {
    descend("visitClassBound() ->");
    return super.visitClassBound();
  }

  @Override
  public SignatureVisitor visitInterfaceBound() {
    descend("visitInterfaceBound() ->");
    return super.visitInterfaceBound();
  }

  @Override
  public SignatureVisitor visitSuperclass() {
    descend("visitSuperclass() ->");
    return super.visitSuperclass();
  }

  @Override
  public SignatureVisitor visitInterface() {
    descend("visitInterface() ->");
    return super.visitInterface();
  }

  @Override
  public SignatureVisitor visitParameterType() {
    descend("visitParameterType() ->");
    return super.visitParameterType();
  }

  @Override
  public SignatureVisitor visitReturnType() {
    descend("visitReturnType() ->");
    return super.visitReturnType();
  }

  @Override
  public SignatureVisitor visitExceptionType() {
    descend("visitExceptionType() ->");
    return super.visitExceptionType();
  }

  @Override
  public void visitBaseType(final char descriptor) {
    print("visitBaseType(" + descriptor + ")");
    super.visitBaseType(descriptor);
  }

  @Override
  public void visitTypeVariable(final String name) {
    print("visitTypeVariable(" + name + ")");
    super.visitTypeVariable(name);
  }

  @Override
  public SignatureVisitor visitArrayType() {
    descend("visitArrayType() ->");
    return super.visitArrayType();
  }

  @Override
  public void visitClassType(final String name) {
    print("visitClassType("+name+")");
    super.visitClassType(name);
  }

  @Override
  public void visitInnerClassType(final String name) {
    print("visitInnerClassType("+name+")");
    super.visitInnerClassType(name);
  }

  @Override
  public void visitTypeArgument() {
    print("visitTypeArgument()");
    super.visitTypeArgument();
  }

  @Override
  public SignatureVisitor visitTypeArgument(final char wildcard) {
    descend("visitTypeArgument("+wildcard+") ->");
    return super.visitTypeArgument(wildcard);
  }

  @Override
  public void visitEnd() {
    ascend("visitEnd() <-");
    super.visitEnd();
  }
}
