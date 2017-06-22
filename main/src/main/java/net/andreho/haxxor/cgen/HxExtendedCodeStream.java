package net.andreho.haxxor.cgen;


import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 16.06.2015.<br/>
 */
public interface HxExtendedCodeStream<Stream extends HxExtendedCodeStream<Stream>>
  extends HxCodeStream<Stream> {

  default Stream INVOKEVIRTUAL(HxMethod method) {
    final HxType declaring = method.getDeclaringMember();
    return INVOKEVIRTUAL(declaring.toInternalName(), method.getName(), method.toDescriptor());
  }

  default Stream INVOKESTATIC(HxMethod method) {
    if(!method.isStatic()) {
      throw new IllegalStateException("Not a static method: "+method);
    }
    final HxType declaring = method.getDeclaringMember();
    return INVOKESTATIC(declaring.toInternalName(), method.getName(), method.toDescriptor(), declaring.isInterface());
  }

  default Stream INVOKESPECIAL(HxMethod method) {
    final HxType declaring = method.getDeclaringMember();
    return INVOKESPECIAL(declaring.toInternalName(), method.getName(), method.toDescriptor());
  }

  default Stream INVOKEINTERFACE(HxMethod method) {
    final HxType declaring = method.getDeclaringMember();
    return INVOKEINTERFACE(declaring.toInternalName(), method.getName(), method.toDescriptor());
  }

  /**
   * Pushes a class reference from constant pool on the operand stack.<br/>
   * Code: <code>0x12</code><br/>
   *
   * @param type to push
   * @return this
   * @implNote by specification this command called <b>LDC</b> too.
   */
  default Stream TYPE(HxType type) {
    return TYPE(type.toInternalName());
  }
}
