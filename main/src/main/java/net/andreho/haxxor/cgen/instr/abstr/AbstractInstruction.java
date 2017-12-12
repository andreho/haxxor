package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.api.impl.HxAnnotatedDelegate;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionSort;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.misc.COMPOUND;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class AbstractInstruction
  extends HxAnnotatedDelegate<HxInstruction>
  implements HxInstruction {

  protected static final int SINGLE_SLOT_SIZE = 1;
  protected static final int DOUBLE_SLOT_SIZE = SINGLE_SLOT_SIZE + SINGLE_SLOT_SIZE;
  public static final Predicate<HxInstruction> ANY_INSTRUCTION_PREDICATE = ins -> true;

  protected HxInstruction next;
  protected HxInstruction previous;

  private static boolean forNext(
    final HxInstruction instruction,
    final Predicate<HxInstruction> predicate,
    final Consumer<HxInstruction> consumer,
    int count) {
    Objects.requireNonNull(predicate, "Predicate can't be null.");
    Objects.requireNonNull(consumer, "Consumer can't be null.");

    boolean found = false;

    for (HxInstruction current = instruction; !current.isEnd(); current = current.getNext()) {
      if(current.hasType(HxInstructionTypes.Special.COMPOSITE)) {
        final COMPOUND compound = (COMPOUND) current;
        if(!compound.isEmpty() && forNext(compound.getFirst(), predicate, consumer, count)) {
          found = true;
        }
      }
      if (predicate.test(current)) {
        consumer.accept(current);
        found = true;
        if (count-- <= 0) {
          break;
        }
      }
    }
    return found;
  }

  private static boolean forPrevious(
    final HxInstruction instruction,
    final Predicate<HxInstruction> predicate,
    final Consumer<HxInstruction> consumer,
    int count) {
    Objects.requireNonNull(predicate, "Predicate can't be null.");
    Objects.requireNonNull(consumer, "Consumer can't be null.");

    boolean found = false;

    for (HxInstruction current = instruction; !current.isBegin(); current = current.getPrevious()) {
      if(current.hasType(HxInstructionTypes.Special.COMPOSITE)) {
        final COMPOUND compound = (COMPOUND) current;
        if(!compound.isEmpty() && forPrevious(compound.getFirst(), predicate, consumer, count)) {
          found = true;
        }
      }
      if (predicate.test(current)) {
        consumer.accept(current);
        found = true;
        if (count-- <= 0) {
          break;
        }
      }
    }
    return found;
  }

  public AbstractInstruction() {
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
    for (HxInstruction instruction = this;
         !instruction.isEnd();
         instruction = instruction.getNext()) {

      if (predicate.test(instruction)) {
        return Optional.of(instruction);
      }

      if(instruction.hasType(HxInstructionTypes.Special.COMPOSITE)) {
        final COMPOUND compound = (COMPOUND) instruction;
        if(!compound.isEmpty()) {
          final HxInstruction compositeBegin = compound.getFirst();
          final Optional<HxInstruction> found = compositeBegin.findFirst(predicate);
          if(found.isPresent()) {
            return found;
          }
        }
      }
    }
    return Optional.empty();
  }

  @Override
  public Optional<HxInstruction> findLast(final Predicate<HxInstruction> predicate) {
    Objects.requireNonNull(predicate, "Predicate can't be null.");
    for (HxInstruction instruction = this;
         !instruction.isBegin();
         instruction = instruction.getPrevious()) {

      if (predicate.test(instruction)) {
        return Optional.of(instruction);
      }

      if(instruction.hasType(HxInstructionTypes.Special.COMPOSITE)) {
        final COMPOUND compound = (COMPOUND) instruction;
        if(!compound.isEmpty()) {
          final HxInstruction compositeEnd = compound.getLast();
          final Optional<HxInstruction> found = compositeEnd.findLast(predicate);
          if(found.isPresent()) {
            return found;
          }
        }
      }
    }
    return Optional.empty();
  }

  @Override
  public void forEachNext(final Consumer<HxInstruction> consumer) {
    forNext(this, ANY_INSTRUCTION_PREDICATE, consumer, Integer.MAX_VALUE);
  }

  @Override
  public void forEachNext(final Predicate<HxInstruction> predicate,
                          final Consumer<HxInstruction> consumer) {
    forNext(this, predicate, consumer, Integer.MAX_VALUE);
  }

  @Override
  public void forEachPrevious(final Consumer<HxInstruction> consumer) {
    forPrevious(this, ANY_INSTRUCTION_PREDICATE, consumer, Integer.MAX_VALUE);
  }

  @Override
  public void forEachPrevious(final Predicate<HxInstruction> predicate,
                              final Consumer<HxInstruction> consumer) {
    forPrevious(this, predicate, consumer, Integer.MAX_VALUE);
  }

  @Override
  public boolean forNext(final Consumer<HxInstruction> consumer) {
    return forNext(this, ANY_INSTRUCTION_PREDICATE, consumer, 1);
  }

  @Override
  public boolean forNext(final Predicate<HxInstruction> predicate,
                         final Consumer<HxInstruction> consumer) {
    return forNext(this, predicate, consumer, 1);
  }

  @Override
  public boolean forPrevious(final Consumer<HxInstruction> consumer) {
    return forPrevious(this, ANY_INSTRUCTION_PREDICATE, consumer, 1);
  }

  @Override
  public boolean forPrevious(final Predicate<HxInstruction> predicate,
                             final Consumer<HxInstruction> consumer) {
    return forPrevious(this, predicate, consumer, 1);
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
    public static void checkMethodName(int opcode,
                                       String name) {
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
