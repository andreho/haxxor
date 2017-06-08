package net.andreho.haxxor.spec.visitors.generics;

import net.andreho.asm.org.objectweb.asm.signature.SignatureVisitor;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxGeneric;
import net.andreho.haxxor.spec.api.HxTypeVariable;
import net.andreho.haxxor.spec.impl.HxTypeVariableImpl;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <br/>Created by a.hofmann on 08.06.2017 at 00:46.
 */
public class TypeVariableSignatureVisitor
    extends TargetedSignatureVisitor<HxTypeVariable> {

  /**
   * Constructs a new {@link SignatureVisitor}.
   *  @param haxxor
   * @param consumer
   */
  public TypeVariableSignatureVisitor(final Haxxor haxxor,
                                      final String name,
                                      final Consumer<HxTypeVariable> consumer,
                                      final Function<String, HxGeneric<?>> variableResolver) {
    super(haxxor, consumer, variableResolver);
    this.target = new HxTypeVariableImpl().setName(name);
  }

  @Override
  public SignatureVisitor visitClassBound() {
    final HxTypeVariableImpl variable = getTarget();
    return new ParameterizedTypeSignatureVisitor(getHaxxor(), variable::setClassBound, getVariableResolver());
  }

  @Override
  public SignatureVisitor visitInterfaceBound() {
    final HxTypeVariableImpl variable = getTarget();
    return new ParameterizedTypeSignatureVisitor(getHaxxor(), variable::addInterfaceBound, getVariableResolver());
  }
}
