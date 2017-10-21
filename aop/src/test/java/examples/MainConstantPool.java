package examples;

import net.andreho.asm.org.objectweb.asm.ClassReader;
import net.andreho.asm.org.objectweb.asm.Type;

import java.io.IOException;

public class MainConstantPool {
  static final int CONSTANT_Class = 7;
  static final int CONSTANT_Fieldref = 9;
  static final int CONSTANT_Methodref = 10;
  static final int CONSTANT_InterfaceMethodref = 11;
  static final int CONSTANT_String = 8;
  static final int CONSTANT_Integer = 3;
  static final int CONSTANT_Float = 4;
  static final int CONSTANT_Long = 5;
  static final int CONSTANT_Double = 6;
  static final int CONSTANT_NameAndType = 12;
  static final int CONSTANT_Utf8 = 1;
  static final int CONSTANT_MethodHandle = 15;
  static final int CONSTANT_MethodType = 16;
  static final int CONSTANT_InvokeDynamic = 18;

  public static void main(String[] args)
  throws IOException {
    ClassReader cr = new ClassReader(Main.class.getName());
    char[] buffer = new char[cr.getMaxStringLength()];
    byte[] bytecode = cr.b;
    for(int i = 0, count = cr.getItemCount(); i<count; i++) {
      int offset = cr.getItem(i);
      if(offset > 0) {
        int tag = bytecode[offset-1];
        String name = "";
        Object value = null;

        switch (tag) {
          case CONSTANT_Fieldref:
            name = "CONSTANT_Fieldref";
            value = new CONSTANT_Fieldref_info(cr, offset, buffer);
            break;
          case CONSTANT_Methodref:
            name = "CONSTANT_Methodref";
            value = new CONSTANT_Methodref_info(cr, offset, buffer);
            break;
          case CONSTANT_InterfaceMethodref:
            name = "CONSTANT_InterfaceMethodref";
            value = new CONSTANT_InterfaceMethodref_info(cr, offset, buffer);
            break;
          case CONSTANT_Integer:
            name = "CONSTANT_Integer";
            value = cr.readConst(i, buffer);
            break;
          case CONSTANT_Float:
            name = "CONSTANT_Float";
            value = cr.readConst(i, buffer);
            break;
          case CONSTANT_NameAndType:
            name = "CONSTANT_NameAndType";
            value = new CONSTANT_NameAndType_info(cr, offset, buffer);
            break;
          case CONSTANT_InvokeDynamic:
            name = "CONSTANT_InvokeDynamic";
            break;
          case CONSTANT_Long:
            name = "CONSTANT_Long";
            value = cr.readConst(i, buffer);
            break;
          case CONSTANT_Double:
            name = "CONSTANT_Double";
            value = cr.readConst(i, buffer);
            break;
          case CONSTANT_Utf8:
            name = "CONSTANT_Utf8";
            value = readUTF(cr.b, offset + 2, cr.readUnsignedShort(offset), buffer);//cr.readUTF8(i, buffer);
            break;
          case CONSTANT_MethodHandle:
            name = "CONSTANT_MethodHandle";
            value = cr.readConst(i, buffer);
            break;
          case CONSTANT_Class:
            name = "CONSTANT_Class";
            value = ((Type) cr.readConst(i, buffer)).getInternalName();
            break;
          case CONSTANT_String:
            name = "CONSTANT_String";
            value = "\"" + cr.readConst(i, buffer) + "\"";
            break;
          case CONSTANT_MethodType:
            name = "CONSTANT_MethodType";
            value = cr.readConst(i, buffer);
            break;
          default:
            throw new IllegalStateException("Unsupported: "+tag);
        }
        System.out.printf("%d\t%d\t%s\t%s\n", i, offset-1, name, value);
      }
    }
  }

  private static String readUTF(byte[] b, int index, final int utfLen, final char[] buf) {
    int endIndex = index + utfLen;
    int strLen = 0;
    int c;
    int st = 0;
    char cc = 0;
    while (index < endIndex) {
      c = b[index++];
      switch (st) {
        case 0:
          c = c & 0xFF;
          if (c < 0x80) { // 0xxxxxxx
            buf[strLen++] = (char) c;
          } else if (c < 0xE0 && c > 0xBF) { // 110x xxxx 10xx xxxx
            cc = (char) (c & 0x1F);
            st = 1;
          } else { // 1110 xxxx 10xx xxxx 10xx xxxx
            cc = (char) (c & 0x0F);
            st = 2;
          }
          break;

        case 1: // byte 2 of 2-byte char or byte 3 of 3-byte char
          buf[strLen++] = (char) ((cc << 6) | (c & 0x3F));
          st = 0;
          break;

        case 2: // byte 2 of 3-byte char
          cc = (char) ((cc << 6) | (c & 0x3F));
          st = 1;
          break;
      }
    }
    return new String(buf, 0, strLen);
  }

  static abstract class CONSTANT_Ref {
    final String type;
    final CONSTANT_NameAndType_info nameAndType;
    public CONSTANT_Ref(ClassReader cr, int index, char[] buffer) {
      type = cr.readClass(index, buffer);
      index = cr.getItem(cr.readUnsignedShort(index + 2));
      nameAndType = new CONSTANT_NameAndType_info(cr, index, buffer);
//      name = cr.readUTF8(index, buffer);
//      desc = cr.readUTF8(index + 2, buffer);
    }
    @Override
    public String toString() {
      return type + " " + nameAndType;
    }
  }

  static class CONSTANT_Fieldref_info extends CONSTANT_Ref {
    public CONSTANT_Fieldref_info(ClassReader cr, int index, char[] buffer) {
      super(cr, index, buffer);
    }
  }

  static class CONSTANT_Methodref_info extends CONSTANT_Ref {
    public CONSTANT_Methodref_info(ClassReader cr, int index, char[] buffer) {
      super(cr, index, buffer);
    }
  }

  static class CONSTANT_InterfaceMethodref_info extends CONSTANT_Ref {
    public CONSTANT_InterfaceMethodref_info(ClassReader cr, int index, char[] buffer) {
      super(cr, index, buffer);
    }
  }

  static class CONSTANT_NameAndType_info {
    final String name;
    final String desc;
    public CONSTANT_NameAndType_info(ClassReader cr, int index, char[] buffer) {
      name = cr.readUTF8(index, buffer);
      desc = cr.readUTF8(index + 2, buffer);
    }
    @Override
    public String toString() {
      return name + " " + desc;
    }
  }
}
