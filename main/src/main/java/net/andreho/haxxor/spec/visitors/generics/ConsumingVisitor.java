package net.andreho.haxxor.spec.visitors.generics;

import net.andreho.asm.org.objectweb.asm.signature.SignatureVisitor;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxGenericElement;
import net.andreho.haxxor.spec.api.HxTypeVariable;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <br/>Created by a.hofmann on 08.06.2017 at 18:18.
 */
public class ConsumingVisitor
    extends VariableControlledVisitor {

  private final Consumer<HxGenericElement<?>> consumer;

  /**
   * Constructs a new {@link SignatureVisitor}.
   *
   * @param consumer
   */
  public ConsumingVisitor(final Haxxor haxxor,
                          final Consumer<HxGenericElement<?>> consumer,
                          final Function<String, HxTypeVariable> variableResolver) {
    super(haxxor, variableResolver);
    this.consumer = consumer;
  }

  protected void consume(HxGenericElement<?> generic) {
    consumer.accept(generic);
  }
}
