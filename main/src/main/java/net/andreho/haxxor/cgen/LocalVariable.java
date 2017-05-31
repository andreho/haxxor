package net.andreho.haxxor.cgen;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.impl.HxAnnotatedImpl;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * <br/>Created by a.hofmann on 21.06.2015.<br/>
 */
public class LocalVariable
    implements HxAnnotated<LocalVariable> {

  private final int index;
  private final String type;
  private final String name;
  private final String signature;

  private final LABEL start;
  private final LABEL end;

  private HxAnnotated annotated; //lazy init

  public LocalVariable(final int index, final String type, final String name,
                       final String signature, final LABEL start, final LABEL end) {
    this.index = index;
    this.type = type;
    this.name = name;
    this.signature = signature;
    this.start = start;
    this.end = end;
  }

  /**
   * @return index of this local variable
   */
  public int getIndex() {
    return this.index;
  }

  /**
   * @return type of this local variable
   */
  public String getType() {
    return this.type;
  }

  /**
   * @return name of this local variable
   */
  public String getName() {
    return this.name;
  }

  /**
   * @return full signature of this local variable
   */
  public String getSignature() {
    return this.signature;
  }

  /**
   * @return start label of this local variable
   */
  public LABEL getStart() {
    return this.start;
  }

  /**
   * @return end label of this local variable
   */
  public LABEL getEnd() {
    return this.end;
  }

  /**
   * Checks whether this local variable is visible for given label or not.
   *
   * @return <b>true</b> if it's visible and accessible, <bfalse></b> otherwise.
   */
  public boolean isVisible(Instruction instruction) {
    return getStart().getIndex() < instruction.getIndex() && instruction.getIndex() < getEnd().getIndex();
  }

  /**
   * @return count of slots that reserved by this local variable (2 for long and double, otherwise 1 always)
   */
  public int size() {
    final Object type = getType();

    if (type == Opcodes.LONG || type == Opcodes.DOUBLE) {
      return 2;
    }

    return 1;
  }

  private HxAnnotated initAnnotated() {
    if (this.annotated == null) {
      this.annotated = new HxAnnotatedImpl();
    }
    return this.annotated;
  }

  public boolean hasAnnotations() {
    return this.annotated != null &&
           !this.annotated.getAnnotations()
                          .isEmpty();
  }

  @Override
  public LocalVariable setAnnotations(final Collection<HxAnnotation> annotations) {
    initAnnotated().setAnnotations(annotations);
    return this;
  }

  @Override
  public Collection<HxAnnotation> getAnnotations() {
    return initAnnotated().getAnnotations();
  }

  @Override
  public Collection<HxAnnotated> getSuperAnnotated() {
    return initAnnotated().getSuperAnnotated();
  }

  @Override
  public Collection<HxAnnotation> getAnnotationsByType(final String type) {
    return initAnnotated().getAnnotationsByType(type);
  }

  @Override
  public Collection<HxAnnotation> annotations(final Predicate<HxAnnotation> predicate, final boolean recursive) {
    return initAnnotated().annotations(predicate, recursive);
  }
}
