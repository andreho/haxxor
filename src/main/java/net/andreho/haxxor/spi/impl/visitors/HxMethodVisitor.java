package net.andreho.haxxor.spi.impl.visitors;

import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxLazyType;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.impl.AsmExecutableMethodVisitor;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 16.03.2016.<br/>
 */
public class HxMethodVisitor
    extends AsmExecutableMethodVisitor {

  protected final HxType declaringType;

  public HxMethodVisitor(final Hx haxxor,
                         final HxType declaringType,
                         final HxMethod method) {
    this(haxxor, declaringType, method, null);
  }

  public HxMethodVisitor(final Hx haxxor,
                         final HxType declaringType,
                         final HxMethod method,
                         final MethodVisitor mv) {
    super(haxxor, method, mv);
    this.declaringType = Objects.requireNonNull(declaringType, "Declaring type can't be null.");
  }

  @Override
  public void visitEnd() {
    super.visitEnd();
    ((HxLazyType) this.declaringType).addMethodInternally(method);
  }
}
