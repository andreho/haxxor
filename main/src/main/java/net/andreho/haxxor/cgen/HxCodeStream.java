package net.andreho.haxxor.cgen;


import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 16.06.2015.<br/>
 */
public interface HxCodeStream {

  /**
   * Begins code creation
   *
   * @return this
   */
  HxCodeStream BEGIN();

  /**
   * A do nothing operation or <i>"no operation"</i><br/>
   * Code: <code>0x00</code><br/>
   *
   * @return this
   */
  HxCodeStream NOP(); //0

  /**
   * Pushes a <b>NULL</b> value on the operand stack.<br/>
   * Code: <code>0x01</code><br/>
   *
   * @return this
   */
  HxCodeStream ACONST_NULL(); //1

  /**
   * Pushes an integer <b>-1</b> value on the operand stack.<br/>
   * Code: <code>0x02</code><br/>
   *
   * @return this
   */
  HxCodeStream ICONST_M1(); //2

  /**
   * Pushes an integer <b>0</b> value on the operand stack.<br/>
   * Code: <code>0x03</code><br/>
   *
   * @return this
   */
  HxCodeStream ICONST_0(); //3

  /**
   * Pushes an integer <b>1</b> value on the operand stack.<br/>
   * Code: <code>0x04</code><br/>
   *
   * @return this
   */
  HxCodeStream ICONST_1(); //4

  /**
   * Pushes an integer <b>2</b> value on the operand stack.<br/>
   * Code: <code>0x05</code><br/>
   *
   * @return this
   */
  HxCodeStream ICONST_2(); //5

  /**
   * Pushes an integer <b>3</b> value on the operand stack.<br/>
   * Code: <code>0x06</code><br/>
   *
   * @return this
   */
  HxCodeStream ICONST_3(); //6

  /**
   * Pushes an integer <b>4</b> value on the operand stack.<br/>
   * Code: <code>0x07</code><br/>
   *
   * @return this
   */
  HxCodeStream ICONST_4(); //7

  /**
   * Pushes an integer <b>5</b> value on the operand stack.<br/>
   * Code: <code>0x08</code><br/>
   *
   * @return this
   */
  HxCodeStream ICONST_5(); //8

  /**
   * Pushes a long <b>0</b> value on the operand stack and reserves <b>two stack slots</b>.<br/>
   * Code: <code>0x09</code><br/>
   *
   * @return this
   */
  HxCodeStream LCONST_0(); //9

  /**
   * Pushes a long <b>1</b> value on the operand stack and reserves <b>two stack slots</b>.<br/>
   * Code: <code>0x0A</code><br/>
   *
   * @return this
   */
  HxCodeStream LCONST_1(); //10

  /**
   * Pushes a float <b>0.0f</b> value on the operand stack.<br/>
   * Code: <code>0x0B</code><br/>
   *
   * @return this
   */
  HxCodeStream FCONST_0(); //11

  /**
   * Pushes a float <b>1.0f</b> value on the operand stack.<br/>
   * Code: <code>0x0C</code><br/>
   *
   * @return this
   */
  HxCodeStream FCONST_1(); //12

  /**
   * Pushes a float <b>2.0f</b> value on the operand stack.<br/>
   * Code: <code>0x0D</code><br/>
   *
   * @return this
   */
  HxCodeStream FCONST_2(); //13

  /**
   * Pushes a double <b>0.0d</b> value on the operand stack and reserves <b>two stack slots</b>.<br/>
   * Code: <code>0x0E</code><br/>
   *
   * @return this
   */
  HxCodeStream DCONST_0(); //14

  /**
   * Pushes a double <b>1.0d</b> value on the operand stack and reserves <b>two stack slots</b>.<br/>
   * Code: <code>0x0F</code><br/>
   *
   * @return this
   */
  HxCodeStream DCONST_1(); //15

  /**
   * Pushes an integer value between <b>-128</b> and <b>127</b> on the operand stack.<br/>
   * Code: <code>0x10</code><br/>
   *
   * @param value to push
   * @return this
   */
  HxCodeStream BIPUSH(byte value); //16

  /**
   * Pushes an integer value between <b>-32768</b> and <b>32767</b> on the operand stack.<br/>
   * Code: <code>0x11</code><br/>
   *
   * @param value to push
   * @return this
   */
  HxCodeStream SIPUSH(short value); //17

  /**
   * Pushes an integer value from constant pool on the operand stack.<br/>
   * Code: <code>0x12</code><br/>
   *
   * @param value to push
   * @return this
   */
  HxCodeStream LDC(int value); //18

  /**
   * Pushes a float value from constant pool on the operand stack.<br/>
   * Code: <code>0x12</code><br/>
   *
   * @param value to push
   * @return this
   */
  HxCodeStream LDC(float value); //18

  /**
   * Pushes a long value from constant pool on the operand stack and reserves <b>two stack slots</b>.<br/>
   * Code: <code>0x12</code><br/>
   *
   * @param value to push
   * @return this
   */
  HxCodeStream LDC(long value); //18

  /**
   * Pushes a double value from constant pool on the operand stack and reserves <b>two stack slots</b>.<br/>
   * Code: <code>0x12</code><br/>
   *
   * @param value to push
   * @return this
   */
  HxCodeStream LDC(double value); //18

  /**
   * Pushes a string value from constant pool on the operand stack.<br/>
   * Code: <code>0x12</code><br/>
   *
   * @param value to push
   * @return this
   */
  HxCodeStream LDC(String value); //18

  /**
   * Pushes a {@link HxMethodHandle handle} value from constant pool on the operand stack.<br/>
   * Code: <code>0x12</code><br/>
   *
   * @param handle to push
   * @return this
   * @implNote by specification this command called <b>LDC</b> too.
   */
  HxCodeStream HANDLE(HxMethodHandle handle); //18

  /**
   * Pushes a string value from constant pool on the operand stack.<br/>
   * Code: <code>0x12</code><br/>
   *
   * @param methodType to push
   * @return this
   * @implNote by specification this command called <b>LDC</b> too.
   */
  HxCodeStream METHOD(HxMethodType methodType); //18

  /**
   * Pushes a class reference from constant pool on the operand stack.<br/>
   * Code: <code>0x12</code><br/>
   *
   * @param internalType to push, represents an internal classname (e.g.: <code>java/lang/String</code>)
   * @return this
   * @implNote by specification this command called <b>LDC</b> too.
   */
  HxCodeStream TYPE(String internalType); //18

  /**
   * Pushes a class reference from constant pool on the operand stack.<br/>
   * Code: <code>0x12</code><br/>
   *
   * @param type to push
   * @return this
   * @implNote by specification this command called <b>LDC</b> too.
   */
  HxCodeStream TYPE(HxType type); //18

  /**
   * Pushes an integer value from the slot with given index on the operand stack.<br/>
   * Code: <code>0x15</code><br/>
   *
   * @param idx of the variable slot (local variable)
   * @return this
   */
  HxCodeStream ILOAD(int idx); //21

  /**
   * Pushes a long value from the slot with given index on the operand stack.<br/>
   * The following slot must contain the second part (LSB) of the long value.<br/>
   * Code: <code>0x16</code><br/>
   *
   * @param idx of the variable slot containing first 4 bytes (MSB) the long value
   * @return this
   */
  HxCodeStream LLOAD(int idx); //22

  /**
   * Pushes a float value from the slot with given index on the operand stack.<br/>
   * Code: <code>0x17</code><br/>
   *
   * @param idx of the variable slot (local variable)
   * @return this
   */
  HxCodeStream FLOAD(int idx); //23

  /**
   * Pushes a double value from the slot with given index on the operand stack.<br/>
   * The following slot must contain the second part (LSB) of the double value.<br/>
   * Code: <code>0x18</code><br/>
   *
   * @param idx of the variable slot containing first 4 bytes (MSB) the double value
   * @return this
   */
  HxCodeStream DLOAD(int idx); //24

  /**
   * Pushes an object reference from the slot with given index on the operand stack..<br/>
   * Code: <code>0x19</code><br/> and also short versions (aload_0 = 0x2a, aload_1 = 0x2b, aload_2 = 0x2c,  aload_3
   * = 0x2d)
   *
   * @param idx of the variable slot (local variable)
   * @return this
   */
  HxCodeStream ALOAD(int idx); //25

  /**
   * Pushes the <b>this</b> reference from the slot with index <b>0</b> on the operand stack.<br/>
   * This method is a shortcut for {@link #ALOAD(int) ALOAD(0)} and available for (not-static) member methods only
   * .<br/>
   * Code: see <code>{@link #ALOAD(int)}</code><br/>
   *
   * @return this
   */
  HxCodeStream THIS(); //25

  /**
   * Reads from a provided int[] array reference at the given index an integer value and
   * pushes it on the operand stack.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:int[], index:int -> ...]</code><br/>
   * Code: <code>0x2e</code><br/>
   *
   * @return this
   */
  HxCodeStream IALOAD(); //46

  /**
   * Reads from a provided long[] array reference at the given index a long value,
   * then pushes it on the operand stack and reserves <b>two stack slots</b>.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:long[], index:int -> ...]</code><br/>
   * Code: <code>0x2f</code><br/>
   *
   * @return this
   */
  HxCodeStream LALOAD(); //47

  /**
   * Reads from a provided float[] array reference at the given index a float value and
   * pushes it on the operand stack.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:float[], index:int -> ...]</code><br/>
   * Code: <code>0x30</code><br/>
   *
   * @return this
   */
  HxCodeStream FALOAD(); //48

  /**
   * Reads from a provided double[] array reference at the given index a double value,
   * then pushes it on the operand stack and reserves <b>two stack slots</b>.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:double[], index:int -> ...]</code><br/>
   * Code: <code>0x31</code><br/>
   *
   * @return this
   */
  HxCodeStream DALOAD(); //49

  /**
   * Reads from a provided T[] array reference at the given index an object and
   * pushes it on the operand stack.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:T[], index:int -> ...]</code><br/>
   * Code: <code>0x32</code><br/>
   *
   * @return this
   */
  HxCodeStream AALOAD(); //50

  /**
   * Reads from a provided byte[] array reference at the given index a byte value and
   * pushes it on the operand stack reserving one stack slot.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:byte[], index:int -> ...]</code><br/>
   * Code: <code>0x33</code><br/>
   *
   * @return this
   */
  HxCodeStream BALOAD(); //51

  /**
   * Reads from a provided char[] array reference at the given index a character value and
   * pushes it on the operand stack reserving one stack slot.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:char[], index:int -> ...]</code><br/>
   * Code: <code>0x34</code><br/>
   *
   * @return this
   */
  HxCodeStream CALOAD(); //52

  /**
   * Reads from a provided short[] array reference at the given index a short value and
   * pushes it on the operand stack reserving one stack slot.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:short[], index:int -> ...]</code><br/>
   * Code: <code>0x35</code><br/>
   *
   * @return this
   */
  HxCodeStream SALOAD(); //53

  /**
   * Pops an integer value from stack and stores it into a variable slot with given <code>index</code>.<br/>
   * This call expects to have on the stack: <code>[..., value:int]</code><br/>
   * Code: <code>0x36</code><br/>
   *
   * @param idx of variable slot that receives value
   * @return this
   */
  HxCodeStream ISTORE(int idx); //54

  /**
   * Pops a long value from stack (two slots) and stores it into a variable slot with given
   * index reserving two slots at <code>index</code> and <code>index+1</code>.<br/>
   * This call expects to have on the stack: <code>[..., value:long[0], value:long[1]]</code><br/>
   * Code: <code>0x37</code><br/>
   *
   * @param idx of variable slot that receives value
   * @return this
   */
  HxCodeStream LSTORE(int idx); //55

  /**
   * Pops a float value from stack and stores it into a variable slot with given <code>index</code><br/>
   * This call expects to have on the stack: <code>[..., value:float]</code><br/>
   * Code: <code>0x38</code><br/>
   *
   * @param idx of variable slot that receives value
   * @return this
   */
  HxCodeStream FSTORE(int idx); //56

  /**
   * Pops a double value from stack (two slots) and stores it into a variable slot with given
   * index reserving two slots at <code>index</code> and <code>index+1</code>.<br/>
   * This call expects to have on the stack: <code>[..., value:double[0], value:double[1]]</code><br/>
   * Code: <code>0x39</code><br/>
   *
   * @param idx of variable slot that receives value
   * @return this
   */
  HxCodeStream DSTORE(int idx); //57

  /**
   * Pops a reference value from stack and stores it into a variable slot with given <code>index</code><br/>
   * This call expects to have on the stack: <code>[..., value:T]</code><br/>
   * Code: <code>0x3a</code><br/>
   *
   * @param idx of variable slot that receives value
   * @return this
   */
  HxCodeStream ASTORE(int idx); //58

  /**
   * Stores an integer at given index into the provided integer array reference.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:int[], index:int, value:int]</code><br/>
   * Code: <code>0x4f</code><br/>
   *
   * @return this
   */
  HxCodeStream IASTORE(); //79

  /**
   * Stores a long value at given index into the provided long array reference.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:long[], index:int, value:long[0],
   * value:long[1]]</code><br/>
   * Code: <code>0x50</code><br/>
   *
   * @return this
   */
  HxCodeStream LASTORE(); //80

  /**
   * Stores a float at given index into the provided float array reference.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:float[], index:int, value:float]</code><br/>
   * Code: <code>0x51</code><br/>
   *
   * @return this
   */
  HxCodeStream FASTORE(); //81

  /**
   * Stores a double value at given index into the provided double array reference.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:double[], index:int, value:double[0],
   * value:double[1]]</code><br/>
   * Code: <code>0x52</code><br/>
   *
   * @return this
   */
  HxCodeStream DASTORE(); //82

  /**
   * Stores an object reference value at given index into the provided object array reference.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:T[], index:int, value:T]</code><br/>
   * Code: <code>0x53</code><br/>
   *
   * @return this
   */
  HxCodeStream AASTORE(); //83

  /**
   * Stores a byte at given index into the provided byte array reference.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:byte[], index:int, value:byte]</code><br/>
   * Code: <code>0x54</code><br/>
   *
   * @return this
   */
  HxCodeStream BASTORE(); //84

  /**
   * Stores a char at given index into the provided char array reference.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:char[], index:int, value:char]</code><br/>
   * Code: <code>0x55</code><br/>
   *
   * @return this
   */
  HxCodeStream CASTORE(); //85

  /**
   * Stores a short at given index into the provided short array reference.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:short[], index:int, value:short]</code><br/>
   * Code: <code>0x56</code><br/>
   *
   * @return this
   */
  HxCodeStream SASTORE(); //86

  /**
   * Pops the top stack value from the stack
   * (like: int, float, array or reference, but not double or long).<br/>
   * This call expects to have on the stack: <code>[..., value:*]</code><br/>
   * Code: <code>0x57</code><br/>
   *
   * @return this
   */
  HxCodeStream POP(); //87

  /**
   * Pop the top one or two operand stack values.<br/>
   * This call expects to have on the stack: <code>[..., value2, value1 -> ...]</code><br/>
   * Code: <code>0x58</code><br/>
   * @return this
   */
  HxCodeStream POP2(); //88

  /**
   * Duplicate the top operand stack value.<br/>
   * This call expects to have on the stack: <code>[..., value -> ..., value, value]</code><br/>
   * Code: <code>0x59</code><br/>
   * @return this
   */
  HxCodeStream DUP(); //89

  /**
   * Duplicate the top operand stack value and insert two values down.<br/>
   * This call expects to have on the stack: <code>[..., value2, value1 -> ..., value1, value2, value1]</code><br/>
   * Code: <code>0x5a</code><br/>
   * @return this
   */
  HxCodeStream DUP_X1(); //90

  /**
   * Duplicate the top operand stack value and insert two or three values down.<br/>
   * This call expects to have on the stack: <code>[..., value3, value2, value1 -> ..., value1, value3, value2, value1]</code><br/>
   * Code: <code>0x5b</code><br/>
   * @return this
   */
  HxCodeStream DUP_X2(); //91

  /**
   * Duplicate the top one or two operand stack values.<br/>
   * This call expects to have on the stack: <code>[..., value2, value1 -> ..., value2, value1, value2, value1]</code><br/>
   * Code: <code>0x5c</code><br/>
   * @return this
   */
  HxCodeStream DUP2(); //92

  /**
   * Duplicate the top one or two operand stack values and insert two or three values down. <br/>
   * This call expects to have on the stack: <code>[..., value3, value2, value1 -> ..., value2, value1, value3, value2, value1]</code><br/>
   * Code: <code>0x5d</code><br/>
   * @return this
   */
  HxCodeStream DUP2_X1(); //93

  /**
   * Duplicate the top one or two operand stack values and insert two, three, or four values down.<br/>
   * This call expects to have on the stack: <code>[..., value4, value3, value2, value1 -> ..., value2, value1, value4, value3, value2, value1]</code><br/>
   * Code: <code>0x5e</code><br/>
   * @return this
   */
  HxCodeStream DUP2_X2(); //94

  /**
   * Swap the top two operand stack values.<br/>
   * This call expects to have on the stack: <code>[..., value2, value1 -> ..., value1, value2]</code><br/>
   * Code: <code>0x5f</code><br/>
   * @return this
   */
  HxCodeStream SWAP(); //95

  /**
   * Calculates an addition of two top integers on the stack.<br/>
   * This call expects to have on the stack: <code>[..., value1, value2 -> ..., result:int]</code><br/>
   * Code: <code>0x60</code><br/>
   * @return this
   */
  HxCodeStream IADD(); //96

  /**
   * Calculates an addition of two top longs on the stack.<br/>
   * This call expects to have on the stack: <code>[..., value1, value2 -> ..., result:long]</code><br/>
   * Code: <code>0x61</code><br/>
   * @return this
   */
  HxCodeStream LADD(); //97

  /**
   * Calculates an addition of two top floats on the stack.<br/>
   * This call expects to have on the stack: <code>[..., value1, value2 -> ..., result:float]</code><br/>
   * Code: <code>0x62</code><br/>
   * @return this
   */
  HxCodeStream FADD(); //98

  /**
   * Calculates an addition of two top doubles on the stack.<br/>
   * This call expects to have on the stack: <code>[..., value1, value2 -> ..., result:double]</code><br/>
   * Code: <code>0x63</code><br/>
   * @return this
   */
  HxCodeStream DADD(); //99

  HxCodeStream ISUB(); //100

  HxCodeStream LSUB(); //101

  HxCodeStream FSUB(); //102

  HxCodeStream DSUB(); //103

  HxCodeStream IMUL(); //104

  HxCodeStream LMUL(); //105

  HxCodeStream FMUL(); //106

  HxCodeStream DMUL(); //107

  HxCodeStream IDIV(); //108

  HxCodeStream LDIV(); //109

  HxCodeStream FDIV(); //110

  HxCodeStream DDIV(); //111

  HxCodeStream IREM(); //112

  HxCodeStream LREM(); //113

  HxCodeStream FREM(); //114

  HxCodeStream DREM(); //115

  HxCodeStream INEG(); //116

  HxCodeStream LNEG(); //117

  HxCodeStream FNEG(); //118

  HxCodeStream DNEG(); //119

  HxCodeStream ISHL(); //120

  HxCodeStream LSHL(); //121

  HxCodeStream ISHR(); //122

  HxCodeStream LSHR(); //123

  HxCodeStream IUSHR(); //124

  HxCodeStream LUSHR(); //125

  HxCodeStream IAND(); //126

  HxCodeStream LAND(); //127

  HxCodeStream IOR(); //128

  HxCodeStream LOR(); //129

  HxCodeStream IXOR(); //130

  HxCodeStream LXOR(); //131

  //----------------------------------------------------------------------------------------------------------------

  HxCodeStream IINC(int var, int increment); //132

  //----------------------------------------------------------------------------------------------------------------

  HxCodeStream I2L(); //133

  HxCodeStream I2F(); //134

  HxCodeStream I2D(); //135

  HxCodeStream L2I(); //136

  HxCodeStream L2F(); //137

  HxCodeStream L2D(); //138

  HxCodeStream F2I(); //139

  HxCodeStream F2L(); //140

  HxCodeStream F2D(); //141

  HxCodeStream D2I(); //142

  HxCodeStream D2L(); //143

  HxCodeStream D2F(); //144

  HxCodeStream I2B(); //145

  HxCodeStream I2C(); //146

  HxCodeStream I2S(); //147

  //----------------------------------------------------------------------------------------------------------------

  HxCodeStream LCMP(); //148

  HxCodeStream FCMPL(); //149

  HxCodeStream FCMPG(); //150

  HxCodeStream DCMPL(); //151

  HxCodeStream DCMPG(); //152

  //----------------------------------------------------------------------------------------------------------------

  HxCodeStream IFEQ(LABEL label); //153

  HxCodeStream IFNE(LABEL label); //154

  HxCodeStream IFLT(LABEL label); //155

  HxCodeStream IFGE(LABEL label); //156

  HxCodeStream IFGT(LABEL label); //157

  HxCodeStream IFLE(LABEL label); //158

  HxCodeStream IF_ICMPEQ(LABEL label); //159

  HxCodeStream IF_ICMPNE(LABEL label); //160

  HxCodeStream IF_ICMPLT(LABEL label); //161

  HxCodeStream IF_ICMPGE(LABEL label); //162

  HxCodeStream IF_ICMPGT(LABEL label); //163

  HxCodeStream IF_ICMPLE(LABEL label); //164

  HxCodeStream IF_ACMPEQ(LABEL label); //165

  HxCodeStream IF_ACMPNE(LABEL label); //166

  //----------------------------------------------------------------------------------------------------------------

  HxCodeStream GOTO(LABEL label); //167

  HxCodeStream JSR(LABEL label); //168

  HxCodeStream RET(int var); //169

  //----------------------------------------------------------------------------------------------------------------

  HxCodeStream TABLESWITCH(int min, int max, LABEL defaultLabel, LABEL... labels); //170

  HxCodeStream LOOKUPSWITCH(LABEL defaultLabel, int[] keys, LABEL[] labels); //171

  //----------------------------------------------------------------------------------------------------------------

  HxCodeStream IRETURN(); //172

  HxCodeStream LRETURN(); //173

  HxCodeStream FRETURN(); //174

  HxCodeStream DRETURN(); //175

  HxCodeStream ARETURN(); //176

  HxCodeStream RETURN(); //177

  //----------------------------------------------------------------------------------------------------------------

  /**
   * Gets static field from class.<br/>
   * This call expects to have on the stack: <code>[..., -> ..., value]</code><br/>
   * Code: <code>0xb2</code><br/>
   * @param owner the internal typename of the owning class
   * @param name of the static field
   * @param desc is the descriptor of the field's type
   *
   * @return this
   */
  HxCodeStream GETSTATIC(String owner, String name, String desc); //178

  /**
   * Set static field in class.<br/>
   * This call expects to have on the stack: <code>[..., value -> ...]</code><br/>
   * Code: <code>0xb3</code><br/>
   * @param owner the internal typename of the owning class
   * @param name of the static field
   * @param desc is the descriptor of the field's type
   *
   * @return this
   */
  HxCodeStream PUTSTATIC(String owner, String name, String desc); //179

  /**
   * Fetch field from object.<br/>
   * This call expects to have on the stack: <code>[..., objectref -> ..., value]</code><br/>
   * Code: <code>0xb4</code><br/>
   * @param owner the internal typename of the owning class
   * @param name of the member field
   * @param desc is the descriptor of the field's type
   *
   * @return this
   */
  HxCodeStream GETFIELD(String owner, String name, String desc); //180

  /**
   * Set field in object.<br/>
   * This call expects to have on the stack: <code>[..., objectref, value -> ...]</code><br/>
   * Code: <code>0xb5</code><br/>
   * @param owner the internal typename of the owning class
   * @param name of the member field
   * @param desc is the descriptor of the field's type
   *
   * @return this
   */
  HxCodeStream PUTFIELD(String owner, String name, String desc); //181

  /**
   * Invoke instance method; dispatch based on class (public/protected/package-private access).<br/>
   * This call expects to have on the stack: <code>[..., objectref,  [arg1, [arg2 ...]] -> ]</code><br/>
   * Code: <code>0xb6</code><br/>
   * @param owner is the internal typename of the owning class
   * @param name of the virtual method
   * @param desc is the descriptor of the method's signature
   * @return this
   */
  HxCodeStream INVOKEVIRTUAL(String owner, String name, String desc); //182

  /**
   * Invoke instance method; special handling for superclass, private, and instance initialization method invocations.<br/>
   * This call expects to have on the stack: <code>[..., objectref,  [arg1, [arg2 ...]] -> ]</code><br/>
   * Code: <code>0xb7</code><br/>
   * @param owner is the internal typename of the owning class
   * @param name of the private method or <code>&lt;init&gt;</code> for a constructor call
   * @param desc is the descriptor of the method's signature
   * @return this
   */
  HxCodeStream INVOKESPECIAL(String owner, String name, String desc); //183

  /**
   * Invoke a class (static) method.<br/>
   * This call expects to have on the stack: <code>[..., [arg1, [arg2 ...]] -> ]</code><br/>
   * Code: <code>0xb8</code><br/>
   * @param owner is the internal typename of the owning class
   * @param name of the static method
   * @param desc is the descriptor of the method's signature
   * @param isInterface whether the owning-class is an interface or not
   * @return this
   */
  HxCodeStream INVOKESTATIC(String owner, String name, String desc, boolean isInterface); //184

  /**
   * Invoke interface method.<br/>
   * This call expects to have on the stack: <code>[..., objectref,  [arg1, [arg2 ...]] -> ]</code><br/>
   * Code: <code>0xb9</code><br/>
   * @param owner is the internal typename of the owning interface
   * @param name of the interface method
   * @param desc is the descriptor of the method's signature
   * @return this
   */
  HxCodeStream INVOKEINTERFACE(String owner, String name, String desc); //185

  /**
   * Invoke dynamic method.<br/>
   * Code: <code>0xba</code><br/>
   * @param name of the target method
   * @param desc is the descriptor of the target method
   * @param bsm is the handle that references a bootstrap method for dynamic method-linking
   * @param bsmArgs are the arguments for the referenced bootstrap method
   * @return this
   */
  HxCodeStream INVOKEDYNAMIC(String name, String desc, HxMethodHandle bsm, HxArguments bsmArgs); //186

  /**
   * Create new object. <br/>
   * This call expects to have on the stack: <code>[... -> ..., objectref]</code><br/>
   * Code: <code>0xbb</code><br/>
   * @param internalType is the internal classname of the referenced class that should be allocated on heap
   *                     (but without any call to any constructor)
   * @return this
   */
  HxCodeStream NEW(String internalType); //187

  /**
   * Create new array of referenced primitives.<br/>
   * This call expects to have on the stack: <code>[..., length:int -> ..., arrayref]</code><br/>
   * Code: <code>0xbc</code><br/>
   * @param type of a primitive array-type
   * @return this
   */
  HxCodeStream NEWARRAY(HxArrayType type); //188

  /**
   * Create new array of reference.<br/>
   * This call expects to have on the stack: <code>[..., length:int -> ..., arrayref]</code><br/>
   * Code: <code>0xbd</code><br/>
   * @param internalType is the internal classname of the component's type of constructed array
   * @return this
   */
  HxCodeStream ANEWARRAY(String internalType); //189

  /**
   * Get length of array.<br/>
   * This call expects to have on the stack: <code>[..., arrayref -> ..., length:int]</code><br/>
   * Code: <code>0xbe</code><br/>
   * @return this
   */
  HxCodeStream ARRAYLENGTH(); //190

  /**
   * Throw exception or error.<br/>
   * This call expects to have on the stack: <code>[..., throwable -> throwable]</code><br/>
   * Code: <code>0xbf</code><br/>
   * @return this
   */
  HxCodeStream ATHROW(); //191

  /**
   * Check whether object is of given type.<br/>
   * This call expects to have on the stack: <code>[..., objectref -> ..., objectref]</code><br/>
   * Code: <code>0xc0</code><br/>
   * @param internalType is the internal classname of a type to check for or
   *                     if it's an array then a descriptor of this array-type
   * @return this
   */
  HxCodeStream CHECKCAST(String internalType); //192

  /**
   * Determine if object is of given type.<br/>
   * This call expects to have on the stack: <code>[..., objectref -> ..., result]</code><br/>
   * Code: <code>0xc1</code><br/>
   * @param internalType is the internal classname of a type to check for or
   *                     if it's an array then a descriptor of this array-type
   * @return this
   */
  HxCodeStream INSTANCEOF(String internalType); //193

  HxCodeStream MONITORENTER(); //194

  HxCodeStream MONITOREXIT(); //195

  //----------------------------------------------------------------------------------------------------------------

  HxCodeStream MULTIANEWARRAY(String internalType, int dims); //197

  //----------------------------------------------------------------------------------------------------------------

  HxCodeStream IFNULL(LABEL label); //198

  HxCodeStream IFNONNULL(LABEL label); //199

  //----------------------------------------------------------------------------------------------------------------

  HxCodeStream LABEL(LABEL label);

  HxCodeStream TRY_CATCH(LABEL startLabel, LABEL endLabel, LABEL handler, String type);

  HxCodeStream FRAME(HxFrames type, int nLocal, Object[] local, int nStack, Object[] stack);

  HxCodeStream LOCAL_VARIABLE(String name, String desc, String signature, LABEL start, LABEL end, int index);

  HxCodeStream LINE_NUMBER(int line, LABEL start);

  HxCodeStream MAXS(int maxStack, int maxLocals);

  void END();

  //#################################################################################################
}