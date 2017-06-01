package net.andreho.haxxor.cgen.instr.constants;

import net.andreho.asm.org.objectweb.asm.Handle;
import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;
import net.andreho.haxxor.spec.api.HxType;

import java.util.List;
import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class LDC
    extends AbstractInstruction {

  public static final int METHOD = 5;
  public static final int TYPE = 7;
  //    private static final int BOOLEAN = -4;
//    private static final int BYTE = -3;
//    private static final int CHAR = -2;
//    private static final int SHORT = -1;
  protected static final int INT = 0;
  protected static final int FLOAT = 1;
  protected static final int LONG = 2;
  protected static final int DOUBLE = 3;
  protected static final int STRING = 4;
  protected static final int HANDLE = 6;

  //----------------------------------------------------------------------------------------------------------------
  private final int type;
  private final Object value;

  protected LDC(Object value, int type) {
    super(Opcodes.LDC);
    this.value = Objects.requireNonNull(value);
    this.type = type;
  }

  public LDC(int value) {
    this(Integer.valueOf(value), INT);
  }

  public LDC(float value) {
    this(Float.valueOf(value), FLOAT);
  }

  public LDC(long value) {
    this(Long.valueOf(value), LONG);
  }

  public LDC(double value) {
    this(Double.valueOf(value), DOUBLE);
  }

  public LDC(String value) {
    this(value, STRING);
  }

  public LDC(Handle value) {
    this(value, HANDLE);
  }

  public LDC(Type value) {
    this(value, TYPE);
  }

  //----------------------------------------------------------------------------------------------------------------

  public LDC(Type value, int type) {
    super(Opcodes.LDC);
    Objects.requireNonNull(value);
    this.type = type;
    switch (type) {
      case METHOD:
        this.value = value.getDescriptor();
        break;
      case TYPE:
        this.value = value.getInternalName();
      default:
        throw new IllegalArgumentException("Unsupported type in conjunction with a Type instance: " + type);
    }
  }

  public LDC(HxType value) {
    this(value.getInternalName(), TYPE);
  }

  //----------------------------------------------------------------------------------------------------------------

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    switch (type) {
      case INT:
        codeStream.LDC((Integer) value);
        break;
      case FLOAT:
        codeStream.LDC((Float) value);
        break;
      case LONG:
        codeStream.LDC((Long) value);
        break;
      case DOUBLE:
        codeStream.LDC((Double) value);
        break;
      case STRING:
        codeStream.LDC(value.toString());
        break;
      case METHOD:
        codeStream.METHOD(value.toString());
        break;
      case HANDLE:
        codeStream.HANDLE((Handle) value);
        break;
      default: {
        if (value instanceof String) {
          codeStream.TYPE(value.toString());
        } else {
          throw new IllegalStateException("Unreachable.");
        }
      }
    }
  }

  @Override
  public List<Object> apply(final Context context) {
    switch (type) {
      case INT:
        return PUSH_INT;
      case FLOAT:
        return PUSH_FLOAT;
      case LONG:
        return PUSH_LONG;
      case DOUBLE:
        return PUSH_DOUBLE;
      case STRING:
        return PUSH_STRING;
      case METHOD:
        return PUSH_METHOD;
      case HANDLE:
        return PUSH_HANDLE;
      case TYPE:
        return PUSH_TYPE;
      default: {
        throw new IllegalStateException("Unreachable.");
      }
    }
  }

  @Override
  public int getStackPopCount() {
    return 0;
  }

  @Override
  public String toString() {
    return super.toString() + " " + this.value;
  }
}
