package net.andreho.haxxor.cgen;

import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.impl.HxAnnotatedImpl;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class HxTryCatch
    implements HxVisitable,
               HxAnnotated<HxTryCatch> {

  private final LABEL start;
  private final LABEL end;
  private final LABEL handler;
  private final String type;
  private volatile HxAnnotated annotated;

  public HxTryCatch(LABEL start,
                    LABEL end,
                    LABEL handler,
                    String type) {
    this.start = Objects.requireNonNull(start);
    this.end = Objects.requireNonNull(end);
    this.handler = Objects.requireNonNull(handler);
    this.type = type;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.TRY_CATCH(this.start, this.end, this.handler, this.type);
  }

  public LABEL getStart() {
    return this.start;
  }

  public LABEL getEnd() {
    return this.end;
  }

  public LABEL getHandler() {
    return this.handler;
  }

  public String getType() {
    return this.type;
  }

  private HxAnnotated<HxTryCatch> initAnnotated() {
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
  public Map<String, HxAnnotation> getAnnotations() {
    return initAnnotated().getAnnotations();
  }

  @Override
  public HxTryCatch setAnnotations(final Collection<HxAnnotation> annotations) {
    initAnnotated().setAnnotations(annotations);
    return this;
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
  public Collection<HxAnnotation> annotations(final Predicate<HxAnnotation> predicate,
                                              final boolean recursive) {
    return initAnnotated().annotations(predicate, recursive);
  }
}
