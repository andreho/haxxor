package net.andreho.haxxor.cgen;

import net.andreho.haxxor.cgen.impl.AbstractClassMatchingInstructionProperty;
import net.andreho.haxxor.cgen.impl.AbstractTypeMatchingInstructionProperty;
import net.andreho.haxxor.cgen.instr.abstr.BasicJumpInstruction;
import net.andreho.haxxor.cgen.instr.abstr.FieldInstruction;
import net.andreho.haxxor.cgen.instr.abstr.InvokeInstruction;
import net.andreho.haxxor.cgen.instr.abstr.SingleOperandInstruction;
import net.andreho.haxxor.cgen.instr.abstr.StringOperandInstruction;
import net.andreho.haxxor.cgen.instr.abstr.SwitchJumpInstruction;
import net.andreho.haxxor.cgen.instr.alloc.MULTIANEWARRAY;
import net.andreho.haxxor.cgen.instr.constants.LDC;
import net.andreho.haxxor.cgen.instr.invokes.INVOKEDYNAMIC;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

/**
 * <br/>Created by a.hofmann on 19.03.2016.<br/>
 * @see Field
 * @see Allocation
 * @see Conversion
 * @see Constant
 * @see Invocation
 */
public interface HxInstructionProperties {
  /**
   * A set of properties that could be extracted from a instruction related to a field
   */
  class JumpsAndSwitches {
    public static final HxInstruction.Property<LABEL> JUMP_LABEL =
      new AbstractClassMatchingInstructionProperty<BasicJumpInstruction, LABEL>("label", BasicJumpInstruction.class) {
        @Override
        protected LABEL readCasted(final BasicJumpInstruction instruction) {
          return instruction.getLabel();
        }
      };

    public static final HxInstruction.Property<LABEL> DEFAULT_JUMP_LABEL =
      new AbstractClassMatchingInstructionProperty<SwitchJumpInstruction, LABEL>("defaultLabel", SwitchJumpInstruction.class) {
        @Override
        protected LABEL readCasted(final SwitchJumpInstruction instruction) {
          return instruction.getDefaultLabel();
        }
      };

    public static final HxInstruction.Property<LABEL[]> SWITCH_LABELS =
      new AbstractClassMatchingInstructionProperty<SwitchJumpInstruction, LABEL[]>("labels", SwitchJumpInstruction.class) {
        @Override
        protected LABEL[] readCasted(final SwitchJumpInstruction instruction) {
          return instruction.getLabels();
        }
      };
  }


  /**
   * A set of properties that could be extracted from a instruction related to a field
   */
  class Field {
    public static final HxInstruction.Property<String> OWNER =
      new AbstractClassMatchingInstructionProperty<FieldInstruction, String>("owner", FieldInstruction.class) {
        @Override
        protected String readCasted(final FieldInstruction instruction) {
          return instruction.getOwner();
        }
      };
    public static final HxInstruction.Property<String> NAME =
      new AbstractClassMatchingInstructionProperty<FieldInstruction, String>("name", FieldInstruction.class) {
        @Override
        protected String readCasted(final FieldInstruction instruction) {
          return instruction.getName();
        }
      };
    public static final HxInstruction.Property<String> DESCRIPTOR =
      new AbstractClassMatchingInstructionProperty<FieldInstruction, String>("descriptor", FieldInstruction.class) {
        @Override
        protected String readCasted(final FieldInstruction instruction) {
          return instruction.getDescriptor();
        }
      };
  }

  /**
   * A set of properties that could be extracted from a instruction related to a allocation
   */
  class Allocation {
    public static final HxInstruction.Property<String> TYPE_OF_NEW =
      new AbstractTypeMatchingInstructionProperty<StringOperandInstruction, String>
        ("type", StringOperandInstruction.class, HxInstructionTypes.Allocation.NEW) {
        @Override
        protected String readCasted(final StringOperandInstruction instruction) {
          return instruction.getOperand();
        }
      };

    public static final HxInstruction.Property<HxArrayType> TYPE_OF_NEWARRAY =
      new AbstractTypeMatchingInstructionProperty<SingleOperandInstruction, HxArrayType>
        ("arrayType", SingleOperandInstruction.class, HxInstructionTypes.Allocation.NEWARRAY) {
        @Override
        protected HxArrayType readCasted(final SingleOperandInstruction instruction) {
          return HxArrayType.fromCode(instruction.getOperand());
        }
      };

    public static final HxInstruction.Property<String> TYPE_OF_ANEWARRAY =
      new AbstractTypeMatchingInstructionProperty<StringOperandInstruction, String>
        ("arrayType", StringOperandInstruction.class, HxInstructionTypes.Allocation.ANEWARRAY) {
        @Override
        protected String readCasted(final StringOperandInstruction instruction) {
          return instruction.getOperand();
        }
      };

    public static final HxInstruction.Property<String> TYPE_OF_MULTIANEWARRAY =
      new AbstractTypeMatchingInstructionProperty<StringOperandInstruction, String>
        ("arrayType", StringOperandInstruction.class, HxInstructionTypes.Allocation.MULTIANEWARRAY) {
        @Override
        protected String readCasted(final StringOperandInstruction instruction) {
          return instruction.getOperand();
        }
      };

    public static final HxInstruction.Property<Integer> DIMESIONS_OF_MULTIANEWARRAY =
      new AbstractTypeMatchingInstructionProperty<MULTIANEWARRAY, Integer>
        ("dimensions", MULTIANEWARRAY.class, HxInstructionTypes.Allocation.MULTIANEWARRAY) {
        @Override
        protected Integer readCasted(final MULTIANEWARRAY instruction) {
          return instruction.getDimensions();
        }
      };
  }

  /**
   * A set of properties that could be extracted from a instruction related to a conversion
   */
  class Conversion {
    public static final HxInstruction.Property<String> TYPE_OF_CHECKCAST =
      new AbstractTypeMatchingInstructionProperty<StringOperandInstruction, String>
        ("type", StringOperandInstruction.class, HxInstructionTypes.Conversion.CHECKCAST) {
        @Override
        protected String readCasted(final StringOperandInstruction instruction) {
          return instruction.getOperand();
        }
      };

    public static final HxInstruction.Property<String> TYPE_OF_INSTANCEOF =
      new AbstractTypeMatchingInstructionProperty<StringOperandInstruction, String>
        ("type", StringOperandInstruction.class, HxInstructionTypes.Conversion.INSTANCEOF) {
        @Override
        protected String readCasted(final StringOperandInstruction instruction) {
          return instruction.getOperand();
        }
      };
  }

  /**
   * A set of properties that could be extracted from a instruction related to a constant loading
   */
  class Constant {
    private static final HxInstruction.Property<Object> VALUE_OF_LDC =
      new AbstractTypeMatchingInstructionProperty<LDC, Object>
        ("value", LDC.class, HxInstructionTypes.Constants.LDC) {
        @Override
        protected Object readCasted(final LDC instruction) {
          return instruction.getValue();
        }
      };

    public static <V> HxInstruction.Property<V> VALUE_OF_LDC() {
      return (HxInstruction.Property<V>) VALUE_OF_LDC;
    }
  }

  /**
   * A set of properties that could be extracted from a instruction related to a invocation
   */
  class Invocation {
    public static final HxInstruction.Property<String> OWNER =
      new AbstractClassMatchingInstructionProperty<InvokeInstruction, String>("owner", InvokeInstruction.class) {
        @Override
        protected String readCasted(final InvokeInstruction instruction) {
          return instruction.getOwner();
        }
      };

    public static final HxInstruction.Property<String> NAME =
      new AbstractClassMatchingInstructionProperty<InvokeInstruction, String>("name", InvokeInstruction.class) {
        @Override
        protected String readCasted(final InvokeInstruction instruction) {
          return instruction.getName();
        }
      };

    public static final HxInstruction.Property<String> DESCRIPTOR =
      new AbstractClassMatchingInstructionProperty<InvokeInstruction, String>("descriptor", InvokeInstruction.class) {
        @Override
        protected String readCasted(final InvokeInstruction instruction) {
          return instruction.getDescriptor();
        }
      };

    public static final HxInstruction.Property<Boolean> IS_INTERFACE =
      new AbstractClassMatchingInstructionProperty<InvokeInstruction, Boolean>("isInterface", InvokeInstruction.class) {
        @Override
        protected Boolean readCasted(final InvokeInstruction instruction) {
          return instruction.isInterface();
        }
      };

    public static final HxInstruction.Property<String> NAME_OF_INVOKEDYNAMIC =
      new AbstractTypeMatchingInstructionProperty<INVOKEDYNAMIC, String>
        ("name", INVOKEDYNAMIC.class, HxInstructionTypes.Invocation.INVOKEDYNAMIC) {
        @Override
        protected String readCasted(final INVOKEDYNAMIC instruction) {
          return instruction.getName();
        }
      };

    public static final HxInstruction.Property<String> DESCRIPTOR_OF_INVOKEDYNAMIC =
      new AbstractTypeMatchingInstructionProperty<INVOKEDYNAMIC, String>
        ("descriptor", INVOKEDYNAMIC.class, HxInstructionTypes.Invocation.INVOKEDYNAMIC) {
        @Override
        protected String readCasted(final INVOKEDYNAMIC instruction) {
          return instruction.getDescriptor();
        }
      };

    public static final HxInstruction.Property<HxMethodHandle> BOOTSTRAP_OF_INVOKEDYNAMIC =
      new AbstractTypeMatchingInstructionProperty<INVOKEDYNAMIC, HxMethodHandle>
        ("bootstrapMethod", INVOKEDYNAMIC.class, HxInstructionTypes.Invocation.INVOKEDYNAMIC) {
        @Override
        protected HxMethodHandle readCasted(final INVOKEDYNAMIC instruction) {
          return instruction.getBootstrapMethod();
        }
      };

    public static final HxInstruction.Property<HxArguments> BOOTSTRAP_ARGUMENTS_OF_INVOKEDYNAMIC =
      new AbstractTypeMatchingInstructionProperty<INVOKEDYNAMIC, HxArguments>
        ("bootstrapMethodArguments", INVOKEDYNAMIC.class, HxInstructionTypes.Invocation.INVOKEDYNAMIC) {
        @Override
        protected HxArguments readCasted(final INVOKEDYNAMIC instruction) {
          return instruction.getBootstrapMethodArguments();
        }
      };
  }
}
