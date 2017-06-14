package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.haxxor.cgen.HxComputingContext;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 13.03.2016.<br/>
 */
public abstract class AbstractArrayStoreInstruction
    extends AbstractZeroOperandInstruction {

  public AbstractArrayStoreInstruction(int opcode) {
    super(opcode);
  }

  protected void checkArrayType(Object arrayType,
                                int depth) {
    if (arrayType.getClass() != String.class || arrayType.toString()
                                                         .charAt(0) != '[') {
      throw new IllegalStateException(
          "An array type is expected at stack[stack.length-" + (depth + 1) + "]: " + arrayType);
    }
  }

  @Override
  public List<Object> getStackPushList(HxComputingContext context) {
    int depth = getInstructionType().getPopSize();

    Object arrayType = context.getStack()
                              .peek(depth);

    checkArrayType(arrayType, depth);

    return NO_STACK_PUSH;
  }
}
