package net.andreho.aop.spi.impl.advices.results;

import net.andreho.aop.api.Redefine;
import net.andreho.aop.api.redefine.Redefinition;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.ResultPostProcessor;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxSort;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;

import static net.andreho.haxxor.utils.NamingUtils.toInternalClassname;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 01:45.
 */
public class RedefinePostProcessor
  implements ResultPostProcessor {

  private static final String REDEFINITION_CLASS_NAME = toInternalClassname(Redefinition.class.getName());
  public static final RedefinePostProcessor INSTANCE = new RedefinePostProcessor();

  @Override
  public boolean handle(final AspectAdvice<?> aspectAdvice,
                        final AspectContext context,
                        final HxMethod interceptor,
                        final HxMethod original,
                        final HxMethod shadow,
                        final HxInstruction anchor) {

    if(interceptor.isAnnotationPresent(Redefine.class)) {

      final HxExtendedCodeStream stream = anchor.asAnchoredStream();
      final HxType returnType = original.getReturnType();
      final HxSort returnSort = returnType.getSort();

      final int resultSlotIndex =
        context.getAspectMethodContext().getResultLocalAttribute().getIndex();

      if(interceptor.hasReturnType(Redefinition.class.getName())) {
        handleRedefinition(resultSlotIndex, stream, returnType, returnSort);
        return true;
      }
//      else if(!returnType.isVoid()) {
//        HxType interceptorReturnType = interceptor.getReturnType();
//
//        if(returnType.isPrimitive()) {
//          if(interceptorReturnType.isPrimitive()) {
//            stream.CONVERT(interceptorReturnType, returnType);
//          } else {
//            stream.UNBOXING(returnSort, true);
//          }
//        } else {
//          if(interceptorReturnType.isPrimitive()) {
//            stream.AUTOBOXING(interceptorReturnType);
//          }
//          stream.CHECKCAST(returnType);
//        }
//        stream.GENERIC_RETURN(returnType);
//      }
//        return true;
    }
    return false;
  }

  private void handleRedefinition(final int resultSlotIndex,
                                  final HxExtendedCodeStream stream,
                                  final HxType returnType,
                                  final HxSort returnSort) {

    switch (returnSort) {
      case VOID:
        break;
      case BOOLEAN:
        stream
          .ILOAD(resultSlotIndex)
          .INVOKEVIRTUAL(
            REDEFINITION_CLASS_NAME,
            "asBoolean", "(Z)Z");
        break;
      case BYTE:
        stream
          .ILOAD(resultSlotIndex)
          .INVOKEVIRTUAL(
            REDEFINITION_CLASS_NAME,
            "asByte", "(B)B");
        break;
      case SHORT:
        stream
          .ILOAD(resultSlotIndex)
          .INVOKEVIRTUAL(
            REDEFINITION_CLASS_NAME,
            "asShort", "(S)S");
        break;
      case CHAR:
        stream
          .ILOAD(resultSlotIndex)
          .INVOKEVIRTUAL(
            REDEFINITION_CLASS_NAME,
            "asChar", "(C)C");
        break;
      case INT:
        stream
          .ILOAD(resultSlotIndex)
          .INVOKEVIRTUAL(
            REDEFINITION_CLASS_NAME,
            "asInt", "(I)I");
        break;
      case FLOAT:
        stream
          .FLOAD(resultSlotIndex)
          .INVOKEVIRTUAL(
            REDEFINITION_CLASS_NAME,
            "asFloat", "(F)F");
        break;
      case LONG:
        stream
          .LLOAD(resultSlotIndex)
          .INVOKEVIRTUAL(
            REDEFINITION_CLASS_NAME,
            "asLong", "(J)J");
        break;
      case DOUBLE:
        stream
          .DLOAD(resultSlotIndex)
          .INVOKEVIRTUAL(
            REDEFINITION_CLASS_NAME,
            "asDouble", "(D)D");
        break;
      default:
        stream
          .ALOAD(resultSlotIndex)
          .INVOKEVIRTUAL(
          REDEFINITION_CLASS_NAME,
          "asObject", "(Ljava/lang/Object;)Ljava/lang/Object;")
              .CHECKCAST(returnType);
    }

    stream
      .GENERIC_STORE(returnSort, resultSlotIndex);
  }
}
