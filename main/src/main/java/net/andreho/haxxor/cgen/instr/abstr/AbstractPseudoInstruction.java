package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.Instruction;
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
    implements Instruction {

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
  public List<Object> apply(final Context context) {
    return Collections.emptyList();
  }

  @Override
  public int getStackPopCount() {
    return 0;
  }

  @Override
  public void dumpTo(final Context context, final CodeStream codeStream) {
  }

  /**
   * @return
   */
  public boolean hasAnnotations() {
    return false;
  }

  @Override
  public Instruction setAnnotations(final Collection<HxAnnotation> annotations) {
    return this;
  }

  @Override
  public Collection<HxAnnotation> getAnnotations() {
    return Collections.emptySet();
  }

  @Override
  public Collection<HxAnnotated> getSuperAnnotated() {
    return Collections.emptySet();
  }

  @Override
  public Collection<HxAnnotation> getAnnotationsByType(final String type) {
    return Collections.emptySet();
  }

  @Override
  public Collection<HxAnnotation> annotations(final Predicate<HxAnnotation> predicate, final boolean recursive) {
    return Collections.emptySet();
  }
}
