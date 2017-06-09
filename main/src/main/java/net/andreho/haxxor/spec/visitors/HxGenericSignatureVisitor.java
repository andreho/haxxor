package net.andreho.haxxor.spec.visitors;

import net.andreho.asm.org.objectweb.asm.signature.SignatureVisitor;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxGeneric;
import net.andreho.haxxor.spec.api.HxTypeVariable;
import net.andreho.haxxor.spec.impl.HxGenericTypeImpl;
import net.andreho.haxxor.spec.impl.HxTypeVariableImpl;
import net.andreho.haxxor.spec.visitors.generics.x.ClassBoundVisitor;
import net.andreho.haxxor.spec.visitors.generics.x.ControlledVisitor;
import net.andreho.haxxor.spec.visitors.generics.x.InterfaceBoundVisitor;
import net.andreho.haxxor.spec.visitors.generics.x.InterfaceVisitor;
import net.andreho.haxxor.spec.visitors.generics.x.SuperclassVisitor;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 07.06.2017 at 16:46.
 */
public class HxGenericSignatureVisitor
    extends ControlledVisitor {

  private final HxGenericTypeImpl genericType;

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
    super(haxxor);
    this.genericType = genericType.initialize();
  }

  private void print(String s) {
    System.out.println(s);
  }

  private void consumeTypeVariable(HxTypeVariable typeVariable) {
    genericType.getTypeVariables().add(typeVariable);
  }

  private void consumeSuperType(HxGeneric<?> superType) {
    genericType.setSuperType(superType);
  }

  private void consumeInterface(HxGeneric<?> interfaceType) {
    genericType.getInterfaces().add(interfaceType);
  }

  private HxTypeVariable variable(String name) {
    for(HxTypeVariable variable : genericType.getTypeVariables()) {
      if(name.equals(variable.getName())) {
        return variable;
      }
    }
    throw new IllegalStateException("Variable not found: "+name);
  }

  @Override
  public void visitFormalTypeParameter(final String name) {
    print("visitFormalTypeParameter(\"" + name + "\")");
    consumeTypeVariable(new HxTypeVariableImpl().setName(name));
  }

  private HxTypeVariable getLastTypeVariable() {
    List<HxTypeVariable> variableList = genericType.getTypeVariables();
    return variableList.get(variableList.size() - 1);
  }

  @Override
  public SignatureVisitor visitClassBound() {
    print("visitClassBound() ->");
    return new ClassBoundVisitor(getHaxxor(), getLastTypeVariable()::setClassBound, this::variable);
  }

  @Override
  public SignatureVisitor visitInterfaceBound() {
    print("visitInterfaceBound() ->");
    return new InterfaceBoundVisitor(getHaxxor(), getLastTypeVariable()::addInterfaceBound, this::variable);
  }

  @Override
  public SignatureVisitor visitSuperclass() {
    print("visitSuperclass() ->");
    return new SuperclassVisitor(getHaxxor(), this::consumeSuperType, this::variable);
  }

  @Override
  public SignatureVisitor visitInterface() {
    print("visitInterface() ->");
    return new InterfaceVisitor(getHaxxor(), this::consumeInterface, this::variable);
  }

  @Override
  public void visitEnd() {
    print("visitEnd() <-");
  }
}
