package examples.log;

import net.andreho.aop.api.Aspect;
import net.andreho.aop.api.Modify;
import net.andreho.aop.api.Profile;
import net.andreho.aop.api.injectable.Attribute;
import net.andreho.aop.api.spec.Annotated;
import net.andreho.aop.api.spec.ClassWith;
import net.andreho.aop.api.spec.Classes;
import net.andreho.aop.api.spec.Modifier;
import net.andreho.aop.api.spec.Modifiers;
import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxMethodBody;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionFactory;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static examples.log.LogAspect.ANNOTATED_WITH_LOG;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 19:34.
 */
@Profile(
  name = ANNOTATED_WITH_LOG,
  classes = @Classes(
    @ClassWith(
      modifiers = @Modifier(value = Modifiers.ABSTRACT | Modifiers.INTERFACE, negate = true),
      annotated = @Annotated(Log.class)
    )
  )
)
@Aspect(ANNOTATED_WITH_LOG)
public class LogAspect {

  protected static final String ANNOTATED_WITH_LOG = "ANNOTATED_WITH_LOG";
  public static final String LOGGER_ATTRIBUTE = "Log";

  @Modify.Type
  public static boolean setupLog(final HxType hxType) {
    Optional<HxAnnotation> optional = hxType.getAnnotation(Log.class);
    if (!optional.isPresent()) {
      return false;
    }

    HxAnnotation annotation = optional.get();
    String logFieldName = annotation.getAttribute("value");

    if (hxType.hasField(logFieldName)) {
      return false;
    }

    final Hx haxxor = hxType.getHaxxor();
    final HxField field = haxxor.createField(Logger.class, logFieldName)
                                .setModifiers(HxField.Modifiers.PUBLIC, HxField.Modifiers.STATIC, HxType.Modifiers.FINAL);

    HxAnnotation hxAnnotation = haxxor.createAnnotation(Attribute.class, true);
    hxAnnotation.setAttribute("value", LOGGER_ATTRIBUTE);
    field.addAnnotation(hxAnnotation);
    hxType.addField(field);

    Optional<HxMethod> methodOptional = hxType.findMethod("<clinit>");
    HxMethod clinit;

    if(methodOptional.isPresent()) {
      clinit = methodOptional.get();
    } else {
      clinit = haxxor.createMethod("void", "<clinit>");
      clinit.getBody().build().RETURN();
      hxType.addMethod(clinit);
    }

    HxMethodBody code = clinit.getBody();
    HxInstructionFactory factory = code.getInstructionFactory();
    Optional<HxInstruction> returnOptional =
        code.getLast().findLastWithType(HxInstructionTypes.Exit.RETURN);

    HxInstruction returnInst = returnOptional.get();
    returnInst.getPrevious()
              .append(factory.LDC(hxType.getName()))
              .append(factory.INVOKESTATIC(
                  Type.getInternalName(LoggerFactory.class),
                  "getLogger",
                  "("+Type.getDescriptor(String.class)+")"+Type.getDescriptor(Logger.class), false))
              .append(factory.PUTSTATIC(hxType.toInternalName(), field.getName(), field.toDescriptor()));

    return true;
  }

//  @Forward(@Methods(declaredBy = @Classes(@ClassWith(Logging.class)), named = @Named("info")))
//  public static void info(@Arg(0) String message, @Attribute(LOGGER_ATTRIBUTE) Logger logger) {
//    logger.info(message);
//  }
//
//  @Forward(@Methods(declaredBy = @Classes(@ClassWith(Logging.class)), named = @Named("trace")))
//  public static void trace(@Arg(0) String message, @Attribute(LOGGER_ATTRIBUTE) Logger logger) {
//    logger.trace(message);
//  }
//
//  @Forward(@Methods(declaredBy = @Classes(@ClassWith(Logging.class)), named = @Named("debug")))
//  public static void debug(@Arg(0) String message, @Attribute(LOGGER_ATTRIBUTE) Logger logger) {
//    logger.debug(message);
//  }
//
//  @Forward(@Methods(declaredBy = @Classes(@ClassWith(Logging.class)), named = @Named("warn")))
//  public static void warn(@Arg(0) String message, @Attribute(LOGGER_ATTRIBUTE) Logger logger) {
//    logger.warn(message);
//  }
//
//  @Forward(@Methods(declaredBy = @Classes(@ClassWith(Logging.class)), named = @Named("error")))
//  public static void error(@Arg(0) String message, @Attribute(LOGGER_ATTRIBUTE) Logger logger) {
//    logger.error(message);
//  }
//
//  @Forward(@Methods(declaredBy = @Classes(@ClassWith(Logging.class)), named = @Named("fatal")))
//  public static void fatal(@Arg(0) String message, @Attribute(LOGGER_ATTRIBUTE) Logger logger) {
//    logger.error(message);
//  }
}
