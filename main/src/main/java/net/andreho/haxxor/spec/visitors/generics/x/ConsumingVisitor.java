package net.andreho.haxxor.spec.visitors.generics.x;

import net.andreho.asm.org.objectweb.asm.signature.SignatureVisitor;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxGeneric;
import net.andreho.haxxor.spec.api.HxTypeVariable;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <br/>Created by a.hofmann on 08.06.2017 at 18:18.
 */
public class ConsumingVisitor
    extends VariableControlledVisitor {

  private final Consumer<HxGeneric<?>> consumer;

  /**
   * Constructs a new {@link SignatureVisitor}.
   *
   * @param consumer
   */
  public ConsumingVisitor(final Haxxor haxxor,
                          final Consumer<HxGeneric<?>> consumer,
                          final Function<String, HxTypeVariable> variableResolver) {
    super(haxxor, variableResolver);
    this.consumer = consumer;
  }

  protected void consume(HxGeneric<?> generic) {
    consumer.accept(generic);
  }
}
