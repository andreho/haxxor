package net.andreho.haxxor.spec.visitors.generics;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.signature.SignatureVisitor;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxGeneric;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <br/>Created by a.hofmann on 08.06.2017 at 01:00.
 */
public class TargetedSignatureVisitor<T extends HxGeneric> extends SignatureVisitor {

  private final Haxxor haxxor;
  private final Function<String, HxGeneric<?>> variableResolver;
  private final Consumer<T> consumer;
  protected T target;

  /**
   * Constructs a new {@link SignatureVisitor}.
   *  @param haxxor
   * @param consumer
   * @param variableResolver
   */
  public TargetedSignatureVisitor(final Haxxor haxxor,
                                  final Consumer<T> consumer,
                                  final Function<String, HxGeneric<?>> variableResolver) {
    super(Opcodes.ASM5);

    this.haxxor = haxxor;
    this.consumer = consumer;
    this.variableResolver = variableResolver;
  }

  protected final Haxxor getHaxxor() {
    return haxxor;
  }

  protected final Function<String, HxGeneric<?>> getVariableResolver() {
    return variableResolver;
  }

  protected final void consume(T target) {
    consumer.accept(target);
  }

  protected final HxGeneric<?> variable(String name) {
    return variableResolver.apply(name);
  }

  protected <V extends T> V getTarget() {
    return (V) target;
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
    consume(target);
  }
}
