package net.andreho.haxxor.spi.impl;

import net.andreho.asm.org.objectweb.asm.ClassReader;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.visitors.HxTypeVisitor;
import net.andreho.haxxor.spi.HxTypeDeserializer;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 13.10.2017 at 16:43.
 */
public class DefaultHxTypeDeserializer implements HxTypeDeserializer {
  private final Haxxor haxxor;

  public DefaultHxTypeDeserializer(final Haxxor haxxor) {
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
