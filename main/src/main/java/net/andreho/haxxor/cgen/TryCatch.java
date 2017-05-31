package net.andreho.haxxor.cgen;

import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.impl.HxAnnotatedImpl;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class TryCatch
    implements Dumpable,
               HxAnnotated<TryCatch> {

  private final LABEL startLabel;
  private final LABEL endLabel;
  private final LABEL handler;
  private final String type;
  private volatile HxAnnotated annotated;

  public TryCatch(LABEL startLabel, LABEL endLabel, LABEL handler, String type) {
    this.startLabel = Objects.requireNonNull(startLabel);
    this.endLabel = Objects.requireNonNull(endLabel);
    this.handler = Objects.requireNonNull(handler);
    this.type = Objects.requireNonNull(type);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.TRY_CATCH(this.startLabel, this.endLabel, this.handler, this.type);
  }

  public LABEL getStartLabel() {
    return this.startLabel;
  }

  public LABEL getEndLabel() {
    return this.endLabel;
  }

  public LABEL getHandler() {
    return this.handler;
  }

  public String getType() {
    return this.type;
  }

  private HxAnnotated<TryCatch> initAnnotated() {
    if (this.annotated == null) {
      this.annotated = new HxAnnotatedImpl<>();
    }
    return this.annotated;
  }

  public boolean hasAnnotations() {
    return this.annotated != null &&
           !this.annotated.getAnnotations()
                          .isEmpty();
  }

  @Override
  public TryCatch setAnnotations(final Collection<HxAnnotation> annotations) {
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
