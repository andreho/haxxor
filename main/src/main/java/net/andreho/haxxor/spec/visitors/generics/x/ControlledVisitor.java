package net.andreho.haxxor.spec.visitors.generics.x;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.signature.SignatureVisitor;
import net.andreho.haxxor.Haxxor;

/**
 * <br/>Created by a.hofmann on 08.06.2017 at 18:20.
 */
public class ControlledVisitor
    extends SignatureVisitor {

  private final Haxxor haxxor;

  /**
   * Constructs a new {@link SignatureVisitor}.
   * @param haxxor
   */
  public ControlledVisitor(final Haxxor haxxor) {
    super(Opcodes.ASM5);
    this.haxxor = haxxor;
  }

  protected Haxxor getHaxxor() {
    return haxxor;
  }

  @Override
  public void visitFormalTypeParameter(final String name) {
    throw new UnsupportedOperationException();
  }

  @Override
  public SignatureVisitor visitClassBound() {
    throw new UnsupportedOperationException();
  }

  @Override
  public SignatureVisitor visitInterfaceBound() {
    throw new UnsupportedOperationException();
  }

  @Override
  public SignatureVisitor visitSuperclass() {
    throw new UnsupportedOperationException();
  }

  @Override
  public SignatureVisitor visitInterface() {
    throw new UnsupportedOperationException();
  }

  @Override
  public SignatureVisitor visitParameterType() {
    throw new UnsupportedOperationException();
  }

  @Override
  public SignatureVisitor visitReturnType() {
    throw new UnsupportedOperationException();
  }

  @Override
  public SignatureVisitor visitExceptionType() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void visitBaseType(final char descriptor) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void visitTypeVariable(final String name) {
    throw new UnsupportedOperationException();
  }

  @Override
  public SignatureVisitor visitArrayType() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void visitClassType(final String name) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void visitInnerClassType(final String name) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void visitTypeArgument() {
    throw new UnsupportedOperationException();
  }

  @Override
  public SignatureVisitor visitTypeArgument(final char wildcard) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void visitEnd() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
}
