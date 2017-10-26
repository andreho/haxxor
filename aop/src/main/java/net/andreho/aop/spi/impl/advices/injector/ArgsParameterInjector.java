package net.andreho.aop.spi.impl.advices.injector;

import net.andreho.aop.api.injectable.Args;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.cgen.HxInstruction;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class ArgsParameterInjector
  extends AbstractParameterInjector {

  private static final Class<Args> ARGS_ANNOTATION_TYPE = Args.class;
  private static final Class<Object[]> OBJECT_ARRAY_TYPE = Object[].class;
  private static final String ARGUMENTS_TYPE = "net.andreho.args.Arguments";
  private static final boolean ARGUMENTS_TYPE_AVAILABLE;

  public static final ArgsParameterInjector INSTANCE = new ArgsParameterInjector();

  static {
    ClassLoader classLoader = ArgsParameterInjector.class.getClassLoader();
    boolean result = false;
    try {
      result =
        classLoader.getResource(ARGUMENTS_TYPE.replace('.', '/') + ".class") != null;
    } catch (Throwable t) { /* INGNORED */ }
    ARGUMENTS_TYPE_AVAILABLE = result;
  }

  public ArgsParameterInjector() {
  }

  @Override
  public int score(final AspectContext context,
                   final HxMethod original,
                   final HxMethod interceptor,
                   final HxParameter parameter) {
    if(!parameter.isAnnotationPresent(ARGS_ANNOTATION_TYPE)) {
      return NOT_INJECTABLE;
    }

    if(!parameter.hasType(OBJECT_ARRAY_TYPE) || !(ARGUMENTS_TYPE_AVAILABLE && parameter.hasType(ARGUMENTS_TYPE))) {
      return NOT_INJECTABLE;
    }

    return MAX_SUITABLE;
  }

  @Override
  public void inject(final AspectAdvice<?> aspectAdvice,
                     final AspectContext context,
                     final HxMethod original,
                     final HxMethod shadow,
                     final HxMethod interceptor,
                     final HxParameter parameter,
                     final HxInstruction anchor) {
    if(parameter.hasType(OBJECT_ARRAY_TYPE)) {
      anchor.asAnchoredStream()
            .PACK_ARGUMENTS(original.getParameterTypes(), original.isStatic()? 0 : 1);
    } else if(ARGUMENTS_TYPE_AVAILABLE && parameter.hasType(ARGUMENTS_TYPE)) {
      throw new UnsupportedOperationException("Injection of Arguments isn't implemented.");
    } else {
      throw new UnsupportedOperationException();
    }
  }
}
