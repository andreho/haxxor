package net.andreho.haxxor.cgen.instr.constants;

import net.andreho.asm.org.objectweb.asm.Opcodes;
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

  public static LDC<String> ofType(String value) {
    return new TypeLDC(value);
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
    super(Opcodes.LDC);
    this.value = Objects.requireNonNull(value);
    this.type = Objects.requireNonNull(type);
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
  public String toString() {
    return super.toString() + " " + this.value;
  }
}
