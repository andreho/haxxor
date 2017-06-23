package net.andreho.haxxor.cgen;


import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 16.06.2015.<br/>
 */
public interface HxExtendedCodeStream
  extends HxCodeStream<HxExtendedCodeStream> {

  default HxExtendedCodeStream GETSTATIC(HxField field) {
    if(!field.isStatic()) {
      throw new IllegalStateException("Invalid GETSTATIC operation on not-static field: "+field);
    }
    final HxType declaring = field.getDeclaringMember();
    return GETSTATIC(declaring.toInternalName(), field.getName(), field.toDescriptor());
  }

  default HxExtendedCodeStream GETFIELD(HxField field) {
    if(!field.isStatic()) {
      throw new IllegalStateException("Invalid GETFIELD operation on static field: "+field);
    }
    final HxType declaring = field.getDeclaringMember();
    return GETFIELD(declaring.toInternalName(), field.getName(), field.toDescriptor());
  }

  default HxExtendedCodeStream PUTSTATIC(HxField field) {
    if(!field.isStatic()) {
      throw new IllegalStateException("Invalid PUTSTATIC operation on not-static field: "+field);
    }
    final HxType declaring = field.getDeclaringMember();
    return PUTSTATIC(declaring.toInternalName(), field.getName(), field.toDescriptor());
  }

  default HxExtendedCodeStream PUTFIELD(HxField field) {
    if(field.isStatic()) {
      throw new IllegalStateException("Invalid PUTFIELD operation on static field: "+field);
    }
    final HxType declaring = field.getDeclaringMember();
    return PUTFIELD(declaring.toInternalName(), field.getName(), field.toDescriptor());
  }

  default HxExtendedCodeStream INVOKEVIRTUAL(HxMethod method) {
    if(method.isStatic()) {
      throw new IllegalStateException("Invalid invoke operation of static method: "+method);
    }
    final HxType declaring = method.getDeclaringMember();
    return INVOKEVIRTUAL(declaring.toInternalName(), method.getName(), method.toDescriptor());
  }

  default HxExtendedCodeStream INVOKESTATIC(HxMethod method) {
    if(!method.isStatic()) {
      throw new IllegalStateException("Not a static method: "+method);
    }
    final HxType declaring = method.getDeclaringMember();
    return INVOKESTATIC(declaring.toInternalName(), method.getName(), method.toDescriptor(), declaring.isInterface());
  }

  default HxExtendedCodeStream INVOKESPECIAL(HxMethod method) {
    if(method.isStatic()) {
      throw new IllegalStateException("Invalid invoke operation of static method: "+method);
    }
    final HxType declaring = method.getDeclaringMember();
    return INVOKESPECIAL(declaring.toInternalName(), method.getName(), method.toDescriptor());
  }

  default HxExtendedCodeStream INVOKEINTERFACE(HxMethod method) {
    if(method.isStatic()) {
      throw new IllegalStateException("Invalid invoke operation of static method: "+method);
    }
    final HxType declaring = method.getDeclaringMember();
    return INVOKEINTERFACE(declaring.toInternalName(), method.getName(), method.toDescriptor());
  }

  default HxExtendedCodeStream INSTANCEOF(HxType type) {
    return INSTANCEOF(type.toInternalName());
  }

  default HxExtendedCodeStream CHECKCAST(HxType type) {
    return CHECKCAST(type.toInternalName());
  }

  /**
   * Pushes a class reference from constant pool on the operand stack.<br/>
   * Code: <code>0x12</code><br/>
   *
   * @param type to push
   * @return this
   * @implNote by specification this command called <b>LDC</b> too.
   */
  default HxExtendedCodeStream TYPE(HxType type) {
    return TYPE(type.toInternalName());
  }
}
