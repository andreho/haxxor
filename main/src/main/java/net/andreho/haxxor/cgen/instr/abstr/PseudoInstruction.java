package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.haxxor.api.HxAnnotated;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstruction;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

/**
 * <br/>Created by a.hofmann on 19.03.2016.<br/>
 */
public abstract class PseudoInstruction
    implements HxInstruction {

  @Override
  public int getOpcode() {
    return -1;
  }

  @Override
  public void compute(final HxComputationContext context,
                      final HxFrame frame) {
  }

  @Override
  public int getStackPopSize() {
    return 0;
  }

  @Override
  public int getStackPushSize() {
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
  public Map<String, HxAnnotation> getAnnotations() {
    return HxAnnotated.DEFAULT_ANNOTATION_MAP;
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
