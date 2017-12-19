package net.andreho.haxxor.spi.impl.visitors.generics;

import net.andreho.asm.org.objectweb.asm.signature.SignatureVisitor;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxGenericElement;
import net.andreho.haxxor.api.HxTypeVariable;

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
  public ConsumingVisitor(final Hx haxxor,
                          final Consumer<HxGenericElement<?>> consumer,
                          final Function<String, HxTypeVariable> variableResolver) {
    super(haxxor, variableResolver);
    this.consumer = consumer;
  }

  protected void consume(HxGenericElement<?> generic) {
    consumer.accept(generic);
  }
}
