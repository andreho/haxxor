package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.haxxor.cgen.Context;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 13.03.2016.<br/>
 */
public abstract class AbstractArrayLoadInstruction extends AbstractZeroOperandInstruction {
   public AbstractArrayLoadInstruction(int opcode) {
      super(opcode);
   }

   protected void checkArrayType(Object arrayType) {
      if (arrayType.getClass() != String.class ||
          arrayType.toString().charAt(0) != '[') {
         throw new IllegalStateException("An array type is expected at stack[stack.length-2]: " + arrayType);
      }
   }

   @Override
   public List<Object> apply(Context context) {
      Object arrayType = context.getStack().peek(1);

      checkArrayType(arrayType);

      String componentType = arrayType.toString().substring(1);

      if (componentType.length() == 1) {
         switch (componentType.charAt(0)) {
            case 'Z':
            case 'B':
            case 'S':
            case 'C':
            case 'I':
               return PUSH_INT;
            case 'F':
               return PUSH_FLOAT;
            case 'J':
               return PUSH_LONG;
            case 'D':
               return PUSH_DOUBLE;
            default:
               throw new IllegalStateException("Invalid component type: " + componentType);
         }
      }
      return context.getPush().prepare().push(componentType).get();
   }

   @Override
   public int getStackPopCount() {
      return 1 + 1;
   }
}
