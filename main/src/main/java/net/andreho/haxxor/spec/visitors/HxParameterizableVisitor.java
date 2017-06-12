package net.andreho.haxxor.spec.visitors;

import net.andreho.asm.org.objectweb.asm.Attribute;
import net.andreho.asm.org.objectweb.asm.MethodVisitor;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.cgen.impl.AsmMethodVisitor;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxExecutable;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.api.HxType.Part;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 16.03.2016.<br/>
 */
public class HxParameterizableVisitor
    extends AsmMethodVisitor {

  protected final HxType declaringType;

  public HxParameterizableVisitor(final Haxxor haxxor,
                                  final HxType declaringType,
                                  final HxExecutable executable) {
    this(haxxor, declaringType, executable, null);
  }

  public HxParameterizableVisitor(final Haxxor haxxor,
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
      owner.initialize(Part.METHODS)
           .addMethod((HxMethod) this.executable);
    } else {
      owner.initialize(Part.CONSTRUCTORS)
           .addConstructor((HxConstructor) this.executable);
    }
  }
}
