package net.andreho.haxxor.spec.visitors;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.signature.SignatureVisitor;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxGeneric;
import net.andreho.haxxor.spec.api.HxTypeVariable;
import net.andreho.haxxor.spec.impl.HxGenericTypeImpl;
import net.andreho.haxxor.spec.impl.HxTypeVariableImpl;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <br/>Created by a.hofmann on 07.06.2017 at 16:46.
 */
public class HxGenericSignatureVisitor
    extends SignatureVisitor {

  private static final int FORMAL_TYPE = 1;
  private static final int CLASS_TYPE = 2;
  private static final int PARAMETERIZED_TYPE = 3;
  private static final int WILDCARD_TYPE = 4;

  private static final int FORMAL_PARAMETER_CATEGORY = 1;
  private static final int SUPER_TYPE_CATEGORY = 2;
  private static final int INTERFACE_TYPE_CATEGORY = 3;

  private final Haxxor haxxor;
  private final Deque<HxGeneric> stack = new ArrayDeque<>();
  private int dimension;
  private int category;
  private int type;

  private final HxGenericTypeImpl genericType;

  private int depth;

  /*
ClassSignature = ( visitFormalTypeParameter visitClassBound? visitInterfaceBound* )* ( visitSuperclass visitInterface* )
   */

  /**
   * Constructs a new {@link SignatureVisitor}.
   * @param haxxor
   * @param genericType
   */
  public HxGenericSignatureVisitor(final Haxxor haxxor,
                                   final HxGenericTypeImpl genericType) {
    super(Opcodes.ASM5);
    this.haxxor = haxxor;
    this.genericType = genericType.initialize();
  }

  private void print(String s) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0, depth = depth(); i < depth; i++) {
      builder.append("| ");
    }
    System.out.println(builder.append(s));
  }
  private int depth() {
    return
        stack.size();
//        depth;
  }
  private void descend(String s) {
    print(s);
    depth++;
  }
  private void ascend(String s) {
    depth--;
    print(s);
  }
  private void ascend() {
    depth--;
  }
  private void descend() {
    depth++;
  }
  private void end() {
    depth = 0;
    if(!stack.isEmpty()) {
      stack.clear();
    }
  }
  private void push(HxGeneric generic) {
    stack.push(generic);
  }
  private HxGeneric pop() {
    return stack.pop();
  }

  private void consumeTypeVariable(HxTypeVariable typeVariable) {

  }

  private void consumeSuperType(HxGeneric<?> superType) {

  }

  private void consumeInterface(HxGeneric<?> superType) {

  }

  @Override
  public void visitFormalTypeParameter(final String name) {
    end();
    print("visitFormalTypeParameter(\"" + name + "\")");
    descend();
    push(addFormalTypeParameter(name));


    super.visitFormalTypeParameter(name);
  }

  private HxTypeVariableImpl addFormalTypeParameter(final String name) {
    HxTypeVariableImpl value = new HxTypeVariableImpl().setName(name);
    genericType.getTypeVariables().add(value);
    category = FORMAL_PARAMETER_CATEGORY;
    return value;
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
    end();
    descend("visitSuperclass() ->");
    return super.visitSuperclass();
  }

  @Override
  public SignatureVisitor visitInterface() {
    end();
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
    print("visitBaseType('" + descriptor + "')");
    super.visitBaseType(descriptor);
  }

  @Override
  public void visitTypeVariable(final String name) {
    print("visitTypeVariable(\"" + name + "\")");
    ascend();
    super.visitTypeVariable(name);
  }

  @Override
  public SignatureVisitor visitArrayType() {
    dimension++;
    print("visitArrayType() ->");
    return super.visitArrayType();
  }

  @Override
  public void visitClassType(final String name) {
    push(haxxor.reference(name));
    print("visitClassType(\"" + name + "\")");
    super.visitClassType(name);
  }

  @Override
  public void visitInnerClassType(final String name) {
    push(haxxor.reference(name));
    print("visitInnerClassType(\"" + name + "\")");
    super.visitInnerClassType(name);
  }

  @Override
  public void visitTypeArgument() {
    print("visitTypeArgument()");
    super.visitTypeArgument();
  }

  @Override
  public SignatureVisitor visitTypeArgument(final char wildcard) {
    descend("visitTypeArgument('" + wildcard + "') ->");
    return super.visitTypeArgument(wildcard);
  }

  @Override
  public void visitEnd() {
    pop();
    ascend("visitEnd() <-");
    dimension = 0;
    super.visitEnd();
  }
}
