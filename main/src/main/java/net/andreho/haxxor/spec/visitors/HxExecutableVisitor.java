package net.andreho.haxxor.spec.visitors;

import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.cgen.impl.AsmExecutableMethodVisitor;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 16.03.2016.<br/>
 */
public class HxExecutableVisitor
    extends AsmExecutableMethodVisitor {

  protected final HxType declaringType;

  public HxExecutableVisitor(final Haxxor haxxor,
                             final HxType declaringType,
                             final HxMethod method) {
    this(haxxor, declaringType, method, null);
  }

  public HxExecutableVisitor(final Haxxor haxxor,
                             final HxType declaringType,
                             final HxMethod method,
                             final MethodVisitor mv) {
    super(haxxor, method, mv);
    this.declaringType = Objects.requireNonNull(declaringType, "Declaring type can't be null.");
  }

  @Override
  public void visitEnd() {
    super.visitEnd();

    final HxType declaringType = this.declaringType;
    declaringType.initialize(HxType.Part.METHODS).addMethod(this.method);
  }
}
