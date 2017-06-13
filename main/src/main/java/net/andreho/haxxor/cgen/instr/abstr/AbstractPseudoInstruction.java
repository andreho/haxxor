package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxAnnotation;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * <br/>Created by a.hofmann on 19.03.2016.<br/>
 */
public abstract class AbstractPseudoInstruction
    implements HxInstruction {

  private int index;

  @Override
  public int getOpcode() {
    return -1;
  }

  @Override
  public int getIndex() {
    return index;
  }

  @Override
  public void setIndex(final int index) {
    this.index = index;
  }

  @Override
  public List<Object> apply(final HxComputingContext context) {
    return Collections.emptyList();
  }

  @Override
  public int getPushSize() {
    return 0;
  }

  @Override
  public HxInstruction getPrevious() {
    return null;
  }

  @Override
  public void setPrevious(final HxInstruction previous) {

  }

  @Override
  public HxInstruction getNext() {
    return null;
  }

  @Override
  public void setNext(final HxInstruction next) {

  }

  @Override
  public int getPopSize() {
    return 0;
  }

  @Override
  public void visit(final HxCodeStream codeStream) {
  }

  /**
   * @return
   */
  public boolean hasAnnotations() {
    return false;
  }

  @Override
  public HxInstruction setAnnotations(final Collection<HxAnnotation> annotations) {
    return this;
  }

  @Override
  public Collection<HxAnnotation> getAnnotations() {
    return HxAnnotated.DEFAULT_ANNOTATION_COLLECTION;
  }

  @Override
  public Collection<HxAnnotated> getSuperAnnotated() {
    return HxAnnotated.DEFAULT_SUPER_ANNOTATED_COLLECTION;
  }

  @Override
  public Collection<HxAnnotation> getAnnotationsByType(final String type) {
    return HxAnnotated.DEFAULT_ANNOTATION_COLLECTION;
  }

  @Override
  public Collection<HxAnnotation> annotations(final Predicate<HxAnnotation> predicate, final boolean recursive) {
    return HxAnnotated.DEFAULT_ANNOTATION_COLLECTION;
  }
}
