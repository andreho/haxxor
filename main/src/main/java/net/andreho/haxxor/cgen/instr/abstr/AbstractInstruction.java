package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionSort;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.impl.HxAnnotatedImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class AbstractInstruction
    implements HxInstruction {

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

  protected final int opcode;
  protected HxInstruction next;
  protected HxInstruction previous;
  protected HxAnnotated annotated;
  private int index = -1;

  public AbstractInstruction(int opcode) {
    this.opcode = opcode;
  }

  @Override
  public boolean hasAnnotations() {
    return annotated != null &&
           annotated.getAnnotations()
                    .isEmpty();
  }

  @Override
  public int getOpcode() {
    return opcode;
  }

  @Override
  public int getIndex() {
    return index;
  }

  @Override
  public void setIndex(final int index) {
    this.index = index;
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
    return findFirst(i -> i.has(instructionType));
  }

  @Override
  public Optional<HxInstruction> findLastWithType(final HxInstructionType instructionType) {
    return findLast(i -> i.has(instructionType));
  }

  @Override
  public Optional<HxInstruction> findFirstWithKind(final HxInstructionSort instructionSort) {
    return findFirst(i -> i.has(instructionSort));
  }

  @Override
  public Optional<HxInstruction> findLastWithKind(final HxInstructionSort instructionSort) {
    return findLast(i -> i.has(instructionSort));
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
    Objects.requireNonNull(consumer, "Consumer can't be null.");
    for(HxInstruction instruction = this;
        !instruction.isEnd();
        instruction = instruction.getNext()) {
      consumer.accept(instruction);
    }
  }

  @Override
  public void forEachNext(final Predicate<HxInstruction> predicate,
                          final Consumer<HxInstruction> consumer) {
    Objects.requireNonNull(predicate, "Predicate can't be null.");
    Objects.requireNonNull(consumer, "Consumer can't be null.");
    for(HxInstruction instruction = this;
        !instruction.isEnd();
        instruction = instruction.getNext()) {
      if(predicate.test(instruction)) {
        consumer.accept(instruction);
      }
    }
  }

  @Override
  public void forEachPrevious(final Consumer<HxInstruction> consumer) {
    Objects.requireNonNull(consumer, "Consumer can't be null.");
    for(HxInstruction instruction = this;
        !instruction.isBegin();
        instruction = instruction.getPrevious()) {
      consumer.accept(instruction);
    }
  }

  @Override
  public void forEachPrevious(final Predicate<HxInstruction> predicate,
                              final Consumer<HxInstruction> consumer) {
    Objects.requireNonNull(predicate, "Predicate can't be null.");
    Objects.requireNonNull(consumer, "Consumer can't be null.");
    for(HxInstruction instruction = this;
        !instruction.isBegin();
        instruction = instruction.getPrevious()) {
      if(predicate.test(instruction)) {
        consumer.accept(instruction);
      }
    }
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

  private HxAnnotated initAnnotated() {
    if (this.annotated == null) {
      this.annotated = new HxAnnotatedImpl();
    }
    return this.annotated;
  }

  @Override
  public HxInstruction setAnnotations(final Collection<HxAnnotation> annotations) {
    initAnnotated().setAnnotations(annotations);
    return this;
  }

  @Override
  public Map<String, HxAnnotation> getAnnotations() {
    return initAnnotated().getAnnotations();
  }

  @Override
  public Collection<HxAnnotated> getSuperAnnotated() {
    return initAnnotated().getSuperAnnotated();
  }

  @Override
  public Collection<HxAnnotation> getAnnotationsByType(final String type) {
    return initAnnotated().getAnnotationsByType(type);
  }

  @Override
  public Collection<HxAnnotation> annotations(final Predicate<HxAnnotation> predicate, final boolean recursive) {
    return initAnnotated().annotations(predicate, recursive);
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

    /**
     * Retrieves a type of given element descriptor (descriptor is either of a method or of a field)
     *
     * @param context to use
     * @param desc    to analyse (either of a method or a field)
     * @return a type name in an internal type form (or special constant)
     */
    public static List<Object> retrieveType(HxComputingContext context, String desc) {
      int off;
      if (desc.charAt(0) != '(' || (off = desc.lastIndexOf(')')) < 0) {
        throw new IllegalArgumentException("Invalid descriptor: " + desc);
      }

      switch (desc.charAt(off + 1)) {
        case 'Z':
        case 'B':
        case 'C':
        case 'S':
        case 'I':
          return PUSH_INT;
        case 'F':
          return PUSH_FLOAT;
        case 'J':
          return PUSH_LONG;
        case 'D':
          return PUSH_DOUBLE;
        case 'V':
          return NO_STACK_PUSH;
        default: {
          return context.getStackPush()
                        .prepare()
                        .push(Utils.transformDesc(desc.substring(off + 1)))
                        .get();
        }
      }
    }
  }
}
