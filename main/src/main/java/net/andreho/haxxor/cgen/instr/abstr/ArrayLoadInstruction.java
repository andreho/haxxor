package net.andreho.haxxor.cgen.instr.abstr;

/**
 * <br/>Created by a.hofmann on 13.03.2016.<br/>
 */
public abstract class ArrayLoadInstruction
  extends ZeroOperandInstruction {

  public ArrayLoadInstruction() {
    super();
  }

//  protected void checkArrayType(Object arrayType) {
//    if (arrayType.getClass() != String.class ||
//        arrayType.toString()
//                 .charAt(0) != '[') {
//      throw new IllegalStateException("An array type is expected at stack[stack.length-2]: " + arrayType);
//    }
//  }

//  @Override
//  public void compute(final HxComputationContext context, final HxStack stack, final HxLocals locals) {
//    final Object arrayType = stack.peek(1);
//
//    checkArrayType(arrayType);
//
//    final String type = arrayType.toString();
//    final String componentType = type.substring(1);
//
//    if (type.length() == 2) {
//      //[Z, [B, [S, [C, [I, [F, [J or [D
//      char c = componentType.charAt(1);
//      switch (c) {
//        case 'Z':
//        case 'B':
//        case 'S':
//        case 'C':
//        case 'I':
//          stack.push(HxComputationContext.INTEGER);
//        case 'F':
//          stack.push(HxComputationContext.FLOAT);
//        case 'J':
//          stack.push(HxComputationContext.LONG);
//        case 'D':
//          stack.push(HxComputationContext.DOUBLE);
//        default:
//          throw new IllegalStateException("Invalid component type: " + componentType);
//      }
//    } else {
//      //[L...
//      stack.push(componentType);
//    }
//  }
}
