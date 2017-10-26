package net.andreho.haxxor.spi.impl;

import net.andreho.asm.org.objectweb.asm.ClassReader;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.spi.HxTypeDeserializer;
import net.andreho.haxxor.spi.impl.visitors.HxTypeVisitor;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 13.10.2017 at 16:43.
 */
public class DefaultHxTypeDeserializer implements HxTypeDeserializer {
  private final Hx haxxor;

  public DefaultHxTypeDeserializer(final Hx haxxor) {
    this.haxxor = Objects.requireNonNull(haxxor);
  }

  /**
   * @return
   */
  protected HxTypeVisitor createTypeVisitor() {
    return new HxTypeVisitor(haxxor);
  }

  @Override
  public HxType deserialize(final String classname,
                            final byte[] byteCode,
                            final int flags) {
    final ClassReader classReader = new ClassReader(byteCode);
    final HxTypeVisitor visitor = createTypeVisitor();
    classReader.accept(visitor, flags);
    return visitor.getType();
  }
}
