package net.andreho.aop.spi.impl.advices.injectors;

import net.andreho.aop.api.injectable.Line;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.misc.LINE_NUMBER;

import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class LineParameterInjector
  extends AbstractAnnotatedParameterInjector {

  private static final String LINE_ANNOTATION_TYPE_NAME = Line.class.getName();
  public static final LineParameterInjector INSTANCE = new LineParameterInjector();

  public LineParameterInjector() {
    super(LINE_ANNOTATION_TYPE_NAME);
  }

  @Override
  protected boolean checkedParameterInjection(final AspectAdvice<?> aspectAdvice,
                                              final AspectContext context,
                                              final HxMethod interceptor,
                                              final HxMethod original,
                                              final HxMethod shadow,
                                              final HxParameter parameter,
                                              final HxInstruction anchor) {

    if (parameter.isAnnotationPresent(LINE_ANNOTATION_TYPE_NAME)) {
      Optional<HxInstruction> lineNumber = anchor.findFirst(
        ins -> ins.hasType(HxInstructionTypes.Special.LINE_NUMBER));

      if (lineNumber.isPresent()) {
        LINE_NUMBER ln = (LINE_NUMBER) lineNumber.get();
        anchor.asAnchoredStream()
              .LDC(ln.getLine());

        return true;
      }
    }

    return false;
  }
}
