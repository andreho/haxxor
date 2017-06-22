package net.andreho.aop.spi.impl.steps.injectors;

import net.andreho.aop.api.injectable.Line;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectStep;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionsType;
import net.andreho.haxxor.cgen.instr.misc.LINE_NUMBER;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;

import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class LineInjector
  extends AbstractAnnotatedInjector {
  private static final String LINE_ANNOTATION_TYPE_NAME = Line.class.getName();
  public static final LineInjector INSTANCE = new LineInjector();

  public LineInjector() {
    super(Line.class.getName());
  }

  @Override
  protected boolean checkedParameterInjection(final AspectStep<?> aspectStep,
                                              final AspectContext context,
                                              final HxMethod interceptor,
                                              final HxMethod original,
                                              final HxMethod shadow,
                                              final HxParameter parameter,
                                              final HxInstruction instruction) {

    if (parameter.isAnnotationPresent(LINE_ANNOTATION_TYPE_NAME)) {
      Optional<HxInstruction> lineNumber = instruction.findFirst(ins -> ins.hasType(HxInstructionsType.Special.LINE_NUMBER));
      if(lineNumber.isPresent()) {
        LINE_NUMBER ln = (LINE_NUMBER) lineNumber.get();
        instruction.asStream().LDC(ln.getLine());
        return true;
      }
    }

    return false;
  }
}