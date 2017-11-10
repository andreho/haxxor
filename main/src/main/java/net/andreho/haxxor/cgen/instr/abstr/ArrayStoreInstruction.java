package net.andreho.haxxor.cgen.instr.abstr;

/**
 * <br/>Created by a.hofmann on 13.03.2016.<br/>
 */
public abstract class ArrayStoreInstruction
  extends ZeroOperandInstruction {

  public ArrayStoreInstruction() {
    super();
  }

//  protected void checkArrayType(Object arrayType,
//                                int depth) {
//    if (arrayType.getClass() != String.class || arrayType.toString()
//                                                         .charAt(0) != '[') {
//      throw new IllegalStateException(
//          "An array type is expected at stack[stack.length-" + (depth + 1) + "]: " + arrayType);
//    }
//  }
//
//  @Override
//  public void compute(final HxComputationContext context,
//                      final HxStack stack,
//                      final HxLocals locals) {
//    int depth = getStackPopSize();
//    Object arrayType = context.getStack().peek(depth);
//    checkArrayType(arrayType, depth);
//    stack.pop(depth);
//  }
}
