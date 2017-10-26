package net.andreho.haxxor.spi.impl.visitors.generics;

import net.andreho.asm.org.objectweb.asm.signature.SignatureVisitor;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.api.HxTypeVariable;
import net.andreho.haxxor.api.impl.HxTypeVariableImpl;

import java.util.function.Function;

/**
 * <br/>Created by a.hofmann on 08.06.2017 at 18:15.
 */
public class FormalTypeParameterVisitor
    extends VariableControlledVisitor {

/*
ClassSignature = ( visitFormalTypeParameter visitClassBound? visitInterfaceBound* )* ( visitSuperclass visitInterface* )
*/
  private final HxTypeVariableImpl typeVariable;

  /**
   * Constructs a new {@link SignatureVisitor}.
   *
   * @param typeVariable
   * @param variableResolver
   */
  public FormalTypeParameterVisitor(final Haxxor haxxor,
                                    final HxTypeVariableImpl typeVariable,
                                    final Function<String, HxTypeVariable> variableResolver) {
    super(haxxor, variableResolver);
    this.typeVariable = typeVariable;
  }

  @Override
  public SignatureVisitor visitClassBound() {
    return new ClassBoundVisitor(getHaxxor(), typeVariable::setClassBound, getVariableResolver());
  }

  @Override
  public SignatureVisitor visitInterfaceBound() {
    return new InterfaceBoundVisitor(getHaxxor(), typeVariable::addInterfaceBound, getVariableResolver());
  }
}
