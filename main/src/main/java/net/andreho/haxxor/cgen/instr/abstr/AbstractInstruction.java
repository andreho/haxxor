package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.api.impl.HxAnnotatedDelegate;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionSort;
import net.andreho.haxxor.cgen.HxInstructionType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class AbstractInstruction extends HxAnnotatedDelegate<HxInstruction>
    implements HxInstruction {

  public static final Predicate<HxInstruction> ANY_INSTRUCTION_PREDICATE = ins -> true;
  public static final List<Object> NO_STACK_PUSH = Collections.emptyList();
  protected static final int SINGLE_SLOT_SIZE = 1;
  protected static final int DOUBLE_SLOT_SIZE = SINGLE_SLOT_SIZE + SINGLE_SLOT_SIZE;
  //----------------------------------------------------------------------------------------------------------------
  protected static final List<Object> PUSH_NULL = Collections.singletonList(Opcodes.NULL);
  protected static final List<Object> PUSH_UNINITIALIZED_THIS = Collections.singletonList(Opcodes.UNINITIALIZED_THIS);
  protected static final List<Object> PUSH_STRING = Collections.singletonList("java/lang/String");
  protected static final List<Object> PUSH_METHOD = Collections.singletonList("java/lang/invoke/MethodType");
  protected static final List<Object> PUSH_HANDLE = Collections.singletonList("java/lang/invoke/MethodHandle");
  protected static final List<Object> PUSH_TYPE = Collections.singletonList("java/lang/Class");
  protected static final List<Object> PUSH_ADDRESS = Collections.singletonList(Opcodes.INTEGER);
  protected static final List<Object> PUSH_INT = PUSH_ADDRESS;
  protected static final List<Object> PUSH_FLOAT = Collections.singletonList(Opcodes.FLOAT);
  protected static final List<Object> PUSH_LONG = Collections.unmodifiableList(
      Arrays.asList(Opcodes.LONG, Opcodes.TOP));
  protected static final List<Object> PUSH_DOUBLE = Collections.unmodifiableList(
      Arrays.asList(Opcodes.DOUBLE, Opcodes.TOP));

  protected HxInstruction next;
  protected HxInstruction previous;

  public AbstractInstruction() {
  }

  @Override
  public int getOpcode() {
    return getInstructionType().getOpcode();
  }

  @Override
  public int getStackPopSize() {
    return getInstructionType().getPopSize();
  }

  @Override
  public int getStackPushSize() {
    return getInstructionType().getPushSize();
  }

  @Override
  public HxInstruction getNext() {
    return next;
  }

  @Override
  public void setNext(HxInstruction next) {
    this.next = next;
  }

  @Override
  public HxInstruction getPrevious() {
    return previous;
  }

  @Override
  public void setPrevious(HxInstruction previous) {
    this.previous = previous;
  }

  @Override
  public Optional<HxInstruction> findFirstWithType(final HxInstructionType instructionType) {
    return findFirst(i -> i.hasType(instructionType));
  }

  @Override
  public Optional<HxInstruction> findLastWithType(final HxInstructionType instructionType) {
    return findLast(i -> i.hasType(instructionType));
  }

  @Override
  public Optional<HxInstruction> findFirstWithSort(final HxInstructionSort instructionSort) {
    return findFirst(i -> i.hasSort(instructionSort));
  }

  @Override
  public Optional<HxInstruction> findLastWithSort(final HxInstructionSort instructionSort) {
    return findLast(i -> i.hasSort(instructionSort));
  }

  @Override
  public Optional<HxInstruction> findFirst(final Predicate<HxInstruction> predicate) {
    Objects.requireNonNull(predicate, "Predicate can't be null.");
    for(HxInstruction instruction = this;
        !instruction.isEnd();
        instruction = instruction.getNext()) {
      if(predicate.test(instruction)) {
        return Optional.of(instruction);
      }
    }
    return Optional.empty();
  }

  @Override
  public Optional<HxInstruction> findLast(final Predicate<HxInstruction> predicate) {
    Objects.requireNonNull(predicate, "Predicate can't be null.");
    for(HxInstruction instruction = this;
        !instruction.isBegin();
        instruction = instruction.getPrevious()) {
      if(predicate.test(instruction)) {
        return Optional.of(instruction);
      }
    }
    return Optional.empty();
  }

  @Override
  public void forEachNext(final Consumer<HxInstruction> consumer) {
    forNext(ANY_INSTRUCTION_PREDICATE, consumer, Integer.MAX_VALUE);
  }

  @Override
  public void forEachNext(final Predicate<HxInstruction> predicate,
                          final Consumer<HxInstruction> consumer) {
    forNext(predicate, consumer, Integer.MAX_VALUE);
  }

  @Override
  public void forEachPrevious(final Consumer<HxInstruction> consumer) {
    forPrevious(ANY_INSTRUCTION_PREDICATE, consumer, Integer.MAX_VALUE);
  }

  @Override
  public void forEachPrevious(final Predicate<HxInstruction> predicate,
                              final Consumer<HxInstruction> consumer) {
    forPrevious(predicate, consumer, Integer.MAX_VALUE);
  }

  @Override
  public void forNext(final Consumer<HxInstruction> consumer) {
    forNext(ANY_INSTRUCTION_PREDICATE, consumer, 1);
  }

  @Override
  public void forNext(final Predicate<HxInstruction> predicate,
                      final Consumer<HxInstruction> consumer) {
    forNext(predicate, consumer, 1);
  }

  @Override
  public void forPrevious(final Consumer<HxInstruction> consumer) {
    forPrevious(ANY_INSTRUCTION_PREDICATE, consumer, 1);
  }

  @Override
  public void forPrevious(final Predicate<HxInstruction> predicate,
                          final Consumer<HxInstruction> consumer) {
    forPrevious(predicate, consumer, 1);
  }

  private void forNext(final Predicate<HxInstruction> predicate,
                      final Consumer<HxInstruction> consumer,
                      int count) {
    Objects.requireNonNull(predicate, "Predicate can't be null.");
    Objects.requireNonNull(consumer, "Consumer can't be null.");

    for(HxInstruction instruction = this;
        !instruction.isEnd();
        instruction = instruction.getNext()) {

      if(predicate.test(instruction)) {
        consumer.accept(instruction);
        if(count-- <= 0) {
          break;
        }
      }
    }
  }

  private void forPrevious(final Predicate<HxInstruction> predicate,
                          final Consumer<HxInstruction> consumer,
                          int count) {
    Objects.requireNonNull(predicate, "Predicate can't be null.");
    Objects.requireNonNull(consumer, "Consumer can't be null.");

    for(HxInstruction instruction = this;
        !instruction.isBegin();
        instruction = instruction.getPrevious()) {

      if(predicate.test(instruction)) {
        consumer.accept(instruction);
        if(count-- <= 0) {
          break;
        }
      }
    }
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

  protected static class Utils {

    /**
     * Checks given method name depending on provided opcode
     *
     * @param opcode to test
     * @param name   to test
     */
    public static void checkMethodName(int opcode, String name) {
      if ("<clinit>".equals(name) || ("<init>".equals(name) && opcode != Opcodes.INVOKESPECIAL)) {
        throw new IllegalArgumentException("Invalid method name for an interface method call: " + name);
      }
    }

    /**
     * Transforms a type descriptor to an internal name
     *
     * @param desc to transform
     * @return an internal type name
     */
    private static String transformDesc(String desc) {
      //for example: [Ljava/lang/String;
      if (desc.endsWith(";")) {
        String value = desc;
        int index = value.indexOf('L');
        if (index > -1) {
          //array case with, e.g.: [[[L...
          value = desc.substring(0, index);
          //add rest of class name without last character
          value += desc.substring(index + 1, desc.length() - 1);
        }
        desc = value;
      }
      return desc;
    }

//    /**
//     * Retrieves a type of given element descriptor (descriptor is either of a method or of a field)
//     *
//     * @param context to use
//     * @param desc    to analyse (either of a method or a field)
//     * @return a type name in an internal type form (or special constant)
//     */
//    public static void pushTypeFromDescriptor(HxComputationContext context, String desc) {
//      int off;
//      if (desc.charAt(0) != '(' || (off = desc.lastIndexOf(')')) < 0) {
//        throw new IllegalArgumentException("Invalid descriptor: " + desc);
//      }
//
//      switch (desc.charAt(off + 1)) {
//        case 'Z':
//        case 'B':
//        case 'C':
//        case 'S':
//        case 'I':
//          context.getStack().push(PUSH_INT); break;
//        case 'F':
//          context.getStack().push(PUSH_FLOAT); break;
//        case 'J':
//          context.getStack().push(PUSH_LONG); break;
//        case 'D':
//          context.getStack().push(PUSH_DOUBLE); break;
//        case 'V':
//          context.getStack().push(NO_STACK_PUSH); break;
//        default: {
//          context.getStack().push(CommonUtils.transformDesc(desc.substring(off + 1)));
//        }
//      }
//    }
  }
}
