package net.andreho.haxxor.spec.visitors;

import net.andreho.asm.org.objectweb.asm.Attribute;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.cgen.impl.AsmExecutableMethodVisitor;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxExecutable;
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
                             final HxExecutable executable) {
    this(haxxor, declaringType, executable, null);
  }

  public HxExecutableVisitor(final Haxxor haxxor,
                             final HxType declaringType,
                             final HxExecutable executable,
                             final MethodVisitor mv) {
    super(haxxor, executable, mv);
    this.declaringType = Objects.requireNonNull(declaringType, "Declaring type can't be null.");
  }

  @Override
  public void visitAttribute(final Attribute attr) {
    super.visitAttribute(attr);
  }

  @Override
  public void visitCode() {
    super.visitCode();
  }

  @Override
  public void visitEnd() {
    super.visitEnd();

    final HxType owner = this.declaringType;

    if (this.executable instanceof HxMethod) {
      owner.initialize(HxType.Part.METHODS)
           .addMethod((HxMethod) this.executable);
    } else {
      owner.initialize(HxType.Part.CONSTRUCTORS)
           .addConstructor((HxConstructor) this.executable);
    }
  }
}
