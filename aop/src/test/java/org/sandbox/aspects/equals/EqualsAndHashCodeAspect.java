package org.sandbox.aspects.equals;

import net.andreho.aop.api.Aspect;
import net.andreho.aop.api.Modify;
import net.andreho.aop.api.Profile;
import net.andreho.aop.api.spec.Annotated;
import net.andreho.aop.api.spec.ClassWith;
import net.andreho.aop.api.spec.Classes;
import net.andreho.aop.api.spec.Modifier;
import net.andreho.aop.api.spec.Modifiers;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

import java.util.Collection;

import static org.sandbox.aspects.equals.EqualsAndHashCodeAspect.ANNOTATED_CLASSES;

/**
 * <br/>Created by a.hofmann on 05.07.2017 at 19:00.
 */
@Profile(
  name = ANNOTATED_CLASSES,
  classes = @Classes(@ClassWith(
    modifiers = @Modifier(value = Modifiers.ABSTRACT | Modifiers.INTERFACE, negate = true),
    annotated = @Annotated(EqualsAndHashCode.class)
  ))
)
@Aspect(ANNOTATED_CLASSES)
public class EqualsAndHashCodeAspect {

  protected static final String HASH_CODE_UTILS = HashCodeUtils.class.getName().replace('.', '/');
  protected static final String EQUALS_UTILS = EqualsUtils.class.getName().replace('.', '/');
  public static final String ANNOTATED_CLASSES = "annotated_with_equals_and_hashCode";

  @Modify.Type
  public static boolean createEqualsAndHashCode(HxType type) {
    if(!type.hasMethod("hashCode") &&
       !type.hasMethod("equals", Object.class)) {

      final Hx haxxor = type.getHaxxor();
      final Collection<HxField> fields =
        type.fields(field -> !field.isStatic() &&
                             field.isAnnotationPresent(EqualityAttribute.class));

      createEqualsMethod(type, haxxor, fields);
      createHashCodeMethod(type, haxxor, fields);
      return true;
    }
    return false;
  }

  private static void createEqualsMethod(final HxType type,
                                           final Hx haxxor,
                                           final Collection<HxField> fields) {
    final HxMethod equals =
      haxxor.createMethod(Boolean.TYPE, "equals", Object.class);

    type.addMethod(equals);

    final HxExtendedCodeStream stream =
      equals.getBody().getFirst().asStream();

    if(fields.isEmpty()) {
      Collection<HxMethod> equalsMethods = type.getSupertype().get().methods(
        method ->
          !method.isStatic() &&
          method.hasName("equals") &&
          method.hasReturnType("boolean") &&
          method.hasParametersCount(1) &&
          method.hasParameterWithTypeAt(0, "java.lang.Object")
      );
      stream.THIS()
            .ALOAD(1)
            .INVOKESPECIAL(equalsMethods.iterator().next())
            .IRETURN();
    } else {
      LABEL isNotTheSameInstance = new LABEL();
      stream
        .THIS()
        .ALOAD(1)
        .IF_ACMPNE(isNotTheSameInstance)
        .ICONST_1()
        .IRETURN()
        .LABEL(isNotTheSameInstance);

      LABEL isInstanceOf = new LABEL();
      stream
        .ALOAD(1)
        .INSTANCEOF(type)
        .IFNE(isInstanceOf)
        .ICONST_0()
        .IRETURN()
        .LABEL(isInstanceOf)
        .ALOAD(1)
        .CHECKCAST(type)
        .ASTORE(2);

      LABEL notEqual = new LABEL();

      for(HxField field : fields) {
        if(field.isStatic()) {
          continue;
        }
        stream.THIS();
        switch (field.getType().getSort()) {
          case BOOLEAN:
          case BYTE:
          case SHORT:
          case CHAR:
          case INT:
          case FLOAT:
          case LONG:
          case DOUBLE:
            stream
              .GETFIELD(field)
              .ALOAD(2)
              .GETFIELD(field)
              .INVOKESTATIC(EQUALS_UTILS, "equal", "("+field.getType().toDescriptor()+field.getType().toDescriptor()+")Z", false);
            break;
          default: {
            stream
              .GETFIELD(field)
              .ALOAD(2)
              .GETFIELD(field)
              .INVOKESTATIC(EQUALS_UTILS,
                            "equal",
                            "(Ljava/lang/Object;Ljava/lang/Object;)Z", false);
            break;
          }
        }
        stream
          .IFEQ(notEqual);
      }

      stream
        .ICONST_1()
        .IRETURN()
        .LABEL(notEqual)
        .ICONST_0()
        .IRETURN();
    }
  }

  private static void createHashCodeMethod(final HxType type,
                                           final Hx haxxor,
                                           final Collection<HxField> fields) {
    final HxMethod hashCode =
      haxxor.createMethod(int.class, "hashCode");

    type.addMethod(hashCode);

    final HxExtendedCodeStream stream =
      hashCode.getBody().getFirst().asStream();

    if(fields.isEmpty()) {
      Collection<HxMethod> hashCodeMethods = type.getSupertype().get().methods(
        method ->
          !method.isStatic() &&
          method.hasName("hashCode") &&
          method.hasReturnType("int") &&
          method.hasParametersCount(0)
      );
      stream.THIS().INVOKESPECIAL(hashCodeMethods.iterator().next());
    } else {
      boolean first = true;
      for(HxField field : fields) {
        if(field.isStatic()) {
          continue;
        }
        stream.THIS();
        switch (field.getType().getSort()) {
          case BOOLEAN:
          case BYTE:
          case SHORT:
          case CHAR:
          case INT:
          case FLOAT:
          case LONG:
          case DOUBLE:
            stream
              .GETFIELD(field)
              .INVOKESTATIC(HASH_CODE_UTILS, "hashCode", "("+field.getType().toDescriptor()+")I", false);
            break;
          default: {
            stream
              .GETFIELD(field)
              .INVOKESTATIC(HASH_CODE_UTILS, "hashCode", "(Ljava/lang/Object;)I", false);
            break;
          }
        }
        if(!first) {
          stream
            .LDC(31)
            .IMUL()
            .IADD();
        }
        first = false;
      }
    }
    stream.IRETURN();
  }
}
