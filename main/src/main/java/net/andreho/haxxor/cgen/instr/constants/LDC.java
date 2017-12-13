package net.andreho.haxxor.cgen.instr.constants;

import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.HxMethodHandle;
import net.andreho.haxxor.cgen.HxMethodType;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;
import net.andreho.haxxor.cgen.instr.constants.ldc.DoubleLDC;
import net.andreho.haxxor.cgen.instr.constants.ldc.FloatLDC;
import net.andreho.haxxor.cgen.instr.constants.ldc.IntegerLDC;
import net.andreho.haxxor.cgen.instr.constants.ldc.LongLDC;
import net.andreho.haxxor.cgen.instr.constants.ldc.MethodHandleLDC;
import net.andreho.haxxor.cgen.instr.constants.ldc.MethodTypeLDC;
import net.andreho.haxxor.cgen.instr.constants.ldc.StringLDC;
import net.andreho.haxxor.cgen.instr.constants.ldc.TypeLDC;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class LDC<T>
    extends AbstractInstruction {

  public static LDC<Integer> of(int value) {
    return new IntegerLDC(value);
  }

  public static LDC<Float> of(float value) {
    return new FloatLDC(value);
  }

  public static LDC<Long> of(long value) {
    return new LongLDC(value);
  }

  public static LDC<Double> of(double value) {
    return new DoubleLDC(value);
  }

  public static LDC<String> of(String value) {
    return new StringLDC(value);
  }

  public static LDC<HxMethodHandle> of(HxMethodHandle value) {
    return new MethodHandleLDC(value);
  }

  public static LDC<HxMethodType> of(HxMethodType value) {
    return new MethodTypeLDC(value);
  }

  public static LDC<String> ofType(String typeDescriptor) {
    return new TypeLDC(typeDescriptor);
  }

  public enum ConstantType {
    INT,
    FLOAT,
    LONG,
    DOUBLE,
    STRING,
    TYPE,
    METHOD_TYPE,
    METHOD_HANDLE
  }

  private final ConstantType type;
  private final T value;

  protected LDC(T value, ConstantType type) {
    super();
    this.value = Objects.requireNonNull(value);
    this.type = Objects.requireNonNull(type);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Constants.LDC;
  }

  public ConstantType getType() {
    return type;
  }

  public T getValue() {
    return value;
  }

  @Override
  public int getStackPopSize() {
    return 0;
  }

  @Override
  public int getStackPushSize() {
    return getType() == ConstantType.LONG || getType() == ConstantType.DOUBLE?
           DOUBLE_SLOT_SIZE :
           SINGLE_SLOT_SIZE;
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  protected String print() {
    return "LDC ("+this.value+")";
  }
}
