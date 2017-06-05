package net.andreho.haxxor.cgen;


import net.andreho.asm.org.objectweb.asm.Handle;
import net.andreho.haxxor.cgen.instr.LABEL;

/**
 * <br/>Created by a.hofmann on 16.06.2015.<br/>
 */
public interface CodeStream {

  /**
   * Begins code creation
   *
   * @return this
   */
  CodeStream BEGIN();

  /**
   * A do nothing operation or <i>"no operation"</i><br/>
   * Code: <code>0x00</code><br/>
   *
   * @return this
   */
  CodeStream NOP(); //0

  /**
   * Pushes a <b>NULL</b> value on the operand stack.<br/>
   * Code: <code>0x01</code><br/>
   *
   * @return this
   */
  CodeStream ACONST_NULL(); //1

  /**
   * Pushes an integer <b>-1</b> value on the operand stack.<br/>
   * Code: <code>0x02</code><br/>
   *
   * @return this
   */
  CodeStream ICONST_M1(); //2

  /**
   * Pushes an integer <b>0</b> value on the operand stack.<br/>
   * Code: <code>0x03</code><br/>
   *
   * @return this
   */
  CodeStream ICONST_0(); //3

  /**
   * Pushes an integer <b>1</b> value on the operand stack.<br/>
   * Code: <code>0x04</code><br/>
   *
   * @return this
   */
  CodeStream ICONST_1(); //4

  /**
   * Pushes an integer <b>2</b> value on the operand stack.<br/>
   * Code: <code>0x05</code><br/>
   *
   * @return this
   */
  CodeStream ICONST_2(); //5

  /**
   * Pushes an integer <b>3</b> value on the operand stack.<br/>
   * Code: <code>0x06</code><br/>
   *
   * @return this
   */
  CodeStream ICONST_3(); //6

  /**
   * Pushes an integer <b>4</b> value on the operand stack.<br/>
   * Code: <code>0x07</code><br/>
   *
   * @return this
   */
  CodeStream ICONST_4(); //7

  /**
   * Pushes an integer <b>5</b> value on the operand stack.<br/>
   * Code: <code>0x08</code><br/>
   *
   * @return this
   */
  CodeStream ICONST_5(); //8

  /**
   * Pushes a long <b>0</b> value on the operand stack and reserves <b>two stack slots</b>.<br/>
   * Code: <code>0x09</code><br/>
   *
   * @return this
   */
  CodeStream LCONST_0(); //9

  /**
   * Pushes a long <b>1</b> value on the operand stack and reserves <b>two stack slots</b>.<br/>
   * Code: <code>0x0A</code><br/>
   *
   * @return this
   */
  CodeStream LCONST_1(); //10

  /**
   * Pushes a float <b>0.0f</b> value on the operand stack.<br/>
   * Code: <code>0x0B</code><br/>
   *
   * @return this
   */
  CodeStream FCONST_0(); //11

  /**
   * Pushes a float <b>1.0f</b> value on the operand stack.<br/>
   * Code: <code>0x0C</code><br/>
   *
   * @return this
   */
  CodeStream FCONST_1(); //12

  /**
   * Pushes a float <b>2.0f</b> value on the operand stack.<br/>
   * Code: <code>0x0D</code><br/>
   *
   * @return this
   */
  CodeStream FCONST_2(); //13

  /**
   * Pushes a double <b>0.0d</b> value on the operand stack and reserves <b>two stack slots</b>.<br/>
   * Code: <code>0x0E</code><br/>
   *
   * @return this
   */
  CodeStream DCONST_0(); //14

  /**
   * Pushes a double <b>1.0d</b> value on the operand stack and reserves <b>two stack slots</b>.<br/>
   * Code: <code>0x0F</code><br/>
   *
   * @return this
   */
  CodeStream DCONST_1(); //15

  /**
   * Pushes an integer value between <b>-128</b> and <b>127</b> on the operand stack.<br/>
   * Code: <code>0x10</code><br/>
   *
   * @param value to push
   * @return this
   */
  CodeStream BIPUSH(byte value); //16

  /**
   * Pushes an integer value between <b>-32768</b> and <b>32767</b> on the operand stack.<br/>
   * Code: <code>0x11</code><br/>
   *
   * @param value to push
   * @return this
   */
  CodeStream SIPUSH(short value); //17

  /**
   * Pushes an integer value from constant pool on the operand stack.<br/>
   * Code: <code>0x12</code><br/>
   *
   * @param value to push
   * @return this
   */
  CodeStream LDC(int value); //18

  /**
   * Pushes a float value from constant pool on the operand stack.<br/>
   * Code: <code>0x12</code><br/>
   *
   * @param value to push
   * @return this
   */
  CodeStream LDC(float value); //18

  /**
   * Pushes a long value from constant pool on the operand stack and reserves <b>two stack slots</b>.<br/>
   * Code: <code>0x12</code><br/>
   *
   * @param value to push
   * @return this
   */
  CodeStream LDC(long value); //18

  /**
   * Pushes a double value from constant pool on the operand stack and reserves <b>two stack slots</b>.<br/>
   * Code: <code>0x12</code><br/>
   *
   * @param value to push
   * @return this
   */
  CodeStream LDC(double value); //18

  /**
   * Pushes a string value from constant pool on the operand stack.<br/>
   * Code: <code>0x12</code><br/>
   *
   * @param value to push
   * @return this
   */
  CodeStream LDC(String value); //18

  /**
   * Pushes a {@link Handle handle} value from constant pool on the operand stack.<br/>
   * Code: <code>0x12</code><br/>
   *
   * @param handle to push
   * @return this
   * @implNote by specification this command called <b>LDC</b> too.
   */
  CodeStream HANDLE(Handle handle); //18

  /**
   * Pushes a string value from constant pool on the operand stack.<br/>
   * Code: <code>0x12</code><br/>
   *
   * @param methodDescriptor to push
   * @return this
   * @implNote by specification this command called <b>LDC</b> too.
   */
  CodeStream METHOD(String methodDescriptor); //18

  /**
   * Pushes a class reference from constant pool on the operand stack.<br/>
   * Code: <code>0x12</code><br/>
   *
   * @param internalType to push, represents an internal classname (e.g.: <code>java/lang/String</code>)
   * @return this
   * @implNote by specification this command called <b>LDC</b> too.
   */
  CodeStream TYPE(String internalType); //18

  /**
   * Pushes an integer value from the slot with given index on the operand stack.<br/>
   * Code: <code>0x15</code><br/>
   *
   * @param idx of the variable slot (local variable)
   * @return this
   */
  CodeStream ILOAD(int idx); //21

  /**
   * Pushes a long value from the slot with given index on the operand stack.<br/>
   * The following slot must contain the second part (LSB) of the long value.<br/>
   * Code: <code>0x16</code><br/>
   *
   * @param idx of the variable slot containing first 4 bytes (MSB) the long value
   * @return this
   */
  CodeStream LLOAD(int idx); //22

  /**
   * Pushes a float value from the slot with given index on the operand stack.<br/>
   * Code: <code>0x17</code><br/>
   *
   * @param idx of the variable slot (local variable)
   * @return this
   */
  CodeStream FLOAD(int idx); //23

  /**
   * Pushes a double value from the slot with given index on the operand stack.<br/>
   * The following slot must contain the second part (LSB) of the double value.<br/>
   * Code: <code>0x18</code><br/>
   *
   * @param idx of the variable slot containing first 4 bytes (MSB) the double value
   * @return this
   */
  CodeStream DLOAD(int idx); //24

  /**
   * Pushes an object reference from the slot with given index on the operand stack..<br/>
   * Code: <code>0x19</code><br/> and also short versions (aload_0 = 0x2a, aload_1 = 0x2b, aload_2 = 0x2c,  aload_3
   * = 0x2d)
   *
   * @param idx of the variable slot (local variable)
   * @return this
   */
  CodeStream ALOAD(int idx); //25

  /**
   * Pushes the <b>this</b> reference from the slot with index <b>0</b> on the operand stack.<br/>
   * This method is a shortcut for {@link #ALOAD(int) ALOAD(0)} and available for (not-static) member methods only
   * .<br/>
   * Code: see <code>{@link #ALOAD(int)}</code><br/>
   *
   * @return this
   */
  CodeStream THIS(); //25

  /**
   * Reads from a provided int[] array reference at the given index an integer value and
   * pushes it on the operand stack.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:int[], index:int -> ...]</code><br/>
   * Code: <code>0x2e</code><br/>
   *
   * @return this
   */
  CodeStream IALOAD(); //46

  /**
   * Reads from a provided long[] array reference at the given index a long value,
   * then pushes it on the operand stack and reserves <b>two stack slots</b>.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:long[], index:int -> ...]</code><br/>
   * Code: <code>0x2f</code><br/>
   *
   * @return this
   */
  CodeStream LALOAD(); //47

  /**
   * Reads from a provided float[] array reference at the given index a float value and
   * pushes it on the operand stack.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:float[], index:int -> ...]</code><br/>
   * Code: <code>0x30</code><br/>
   *
   * @return this
   */
  CodeStream FALOAD(); //48

  /**
   * Reads from a provided double[] array reference at the given index a double value,
   * then pushes it on the operand stack and reserves <b>two stack slots</b>.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:double[], index:int -> ...]</code><br/>
   * Code: <code>0x31</code><br/>
   *
   * @return this
   */
  CodeStream DALOAD(); //49

  /**
   * Reads from a provided T[] array reference at the given index an object and
   * pushes it on the operand stack.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:T[], index:int -> ...]</code><br/>
   * Code: <code>0x32</code><br/>
   *
   * @return this
   */
  CodeStream AALOAD(); //50

  /**
   * Reads from a provided byte[] array reference at the given index a byte value and
   * pushes it on the operand stack reserving one stack slot.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:byte[], index:int -> ...]</code><br/>
   * Code: <code>0x33</code><br/>
   *
   * @return this
   */
  CodeStream BALOAD(); //51

  /**
   * Reads from a provided char[] array reference at the given index a character value and
   * pushes it on the operand stack reserving one stack slot.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:char[], index:int -> ...]</code><br/>
   * Code: <code>0x34</code><br/>
   *
   * @return this
   */
  CodeStream CALOAD(); //52

  /**
   * Reads from a provided short[] array reference at the given index a short value and
   * pushes it on the operand stack reserving one stack slot.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:short[], index:int -> ...]</code><br/>
   * Code: <code>0x35</code><br/>
   *
   * @return this
   */
  CodeStream SALOAD(); //53

  /**
   * Pops an integer value from stack and stores it into a variable slot with given <code>index</code>.<br/>
   * This call expects to have on the stack: <code>[..., value:int]</code><br/>
   * Code: <code>0x36</code><br/>
   *
   * @param idx of variable slot that receives value
   * @return this
   */
  CodeStream ISTORE(int idx); //54

  /**
   * Pops a long value from stack (two slots) and stores it into a variable slot with given
   * index reserving two slots at <code>index</code> and <code>index+1</code>.<br/>
   * This call expects to have on the stack: <code>[..., value:long[0], value:long[1]]</code><br/>
   * Code: <code>0x37</code><br/>
   *
   * @param idx of variable slot that receives value
   * @return this
   */
  CodeStream LSTORE(int idx); //55

  //----------------------------------------------------------------------------------------------------------------

  /**
   * Pops a float value from stack and stores it into a variable slot with given <code>index</code><br/>
   * This call expects to have on the stack: <code>[..., value:float]</code><br/>
   * Code: <code>0x38</code><br/>
   *
   * @param idx of variable slot that receives value
   * @return this
   */
  CodeStream FSTORE(int idx); //56

  /**
   * Pops a double value from stack (two slots) and stores it into a variable slot with given
   * index reserving two slots at <code>index</code> and <code>index+1</code>.<br/>
   * This call expects to have on the stack: <code>[..., value:double[0], value:double[1]]</code><br/>
   * Code: <code>0x39</code><br/>
   *
   * @param idx of variable slot that receives value
   * @return this
   */
  CodeStream DSTORE(int idx); //57

  /**
   * Pops a reference value from stack and stores it into a variable slot with given <code>index</code><br/>
   * This call expects to have on the stack: <code>[..., value:T]</code><br/>
   * Code: <code>0x3a</code><br/>
   *
   * @param idx of variable slot that receives value
   * @return this
   */
  CodeStream ASTORE(int idx); //58

  /**
   * Stores an integer at given index into the provided integer array reference.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:int[], index:int, value:int]</code><br/>
   * Code: <code>0x4f</code><br/>
   *
   * @return this
   */
  CodeStream IASTORE(); //79

  /**
   * Stores a long value at given index into the provided long array reference.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:long[], index:int, value:long[0],
   * value:long[1]]</code><br/>
   * Code: <code>0x50</code><br/>
   *
   * @return this
   */
  CodeStream LASTORE(); //80

  //----------------------------------------------------------------------------------------------------------------

  /**
   * Stores a float at given index into the provided float array reference.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:float[], index:int, value:float]</code><br/>
   * Code: <code>0x51</code><br/>
   *
   * @return this
   */
  CodeStream FASTORE(); //81

  /**
   * Stores a double value at given index into the provided double array reference.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:double[], index:int, value:double[0],
   * value:double[1]]</code><br/>
   * Code: <code>0x52</code><br/>
   *
   * @return this
   */
  CodeStream DASTORE(); //82

  /**
   * Stores an object reference value at given index into the provided object array reference.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:T[], index:int, value:T]</code><br/>
   * Code: <code>0x53</code><br/>
   *
   * @return this
   */
  CodeStream AASTORE(); //83

  /**
   * Stores a byte at given index into the provided byte array reference.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:byte[], index:int, value:byte]</code><br/>
   * Code: <code>0x54</code><br/>
   *
   * @return this
   */
  CodeStream BASTORE(); //84

  /**
   * Stores a char at given index into the provided char array reference.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:char[], index:int, value:char]</code><br/>
   * Code: <code>0x55</code><br/>
   *
   * @return this
   */
  CodeStream CASTORE(); //85

  /**
   * Stores a short at given index into the provided short array reference.<br/>
   * This call expects to have on the stack: <code>[..., arrayref:short[], index:int, value:short]</code><br/>
   * Code: <code>0x56</code><br/>
   *
   * @return this
   */
  CodeStream SASTORE(); //86

  /**
   * Pops one stack slot value from the stack (like:).<br/>
   * This call expects to have on the stack: <code>[..., value:*]</code><br/>
   * Code: <code>0x57</code><br/>
   *
   * @return
   */
  CodeStream POP(); //87

  CodeStream POP2(); //88

  //----------------------------------------------------------------------------------------------------------------

  CodeStream DUP(); //89

  CodeStream DUP_X1(); //90

  CodeStream DUP_X2(); //91

  CodeStream DUP2(); //92

  CodeStream DUP2_X1(); //93

  CodeStream DUP2_X2(); //94

  CodeStream SWAP(); //95

  CodeStream IADD(); //96

  CodeStream LADD(); //97

  //----------------------------------------------------------------------------------------------------------------

  CodeStream FADD(); //98

  CodeStream DADD(); //99

  CodeStream ISUB(); //100

  CodeStream LSUB(); //101

  CodeStream FSUB(); //102

  CodeStream DSUB(); //103

  CodeStream IMUL(); //104

  CodeStream LMUL(); //105

  CodeStream FMUL(); //106

  CodeStream DMUL(); //107

  CodeStream IDIV(); //108

  CodeStream LDIV(); //109

  CodeStream FDIV(); //110

  CodeStream DDIV(); //111

  CodeStream IREM(); //112

  CodeStream LREM(); //113

  CodeStream FREM(); //114

  CodeStream DREM(); //115

  CodeStream INEG(); //116

  CodeStream LNEG(); //117

  CodeStream FNEG(); //118

  CodeStream DNEG(); //119

  CodeStream ISHL(); //120

  CodeStream LSHL(); //121

  //----------------------------------------------------------------------------------------------------------------

  CodeStream ISHR(); //122

  CodeStream LSHR(); //123

  CodeStream IUSHR(); //124

  CodeStream LUSHR(); //125

  CodeStream IAND(); //126

  CodeStream LAND(); //127

  CodeStream IOR(); //128

  CodeStream LOR(); //129

  CodeStream IXOR(); //130

  CodeStream LXOR(); //131

  CodeStream IINC(int var, int increment); //132

  //----------------------------------------------------------------------------------------------------------------

  CodeStream I2L(); //133

  CodeStream I2F(); //134

  CodeStream I2D(); //135

  CodeStream L2I(); //136

  CodeStream L2F(); //137

  CodeStream L2D(); //138

  CodeStream F2I(); //139

  CodeStream F2L(); //140

  CodeStream F2D(); //141

  CodeStream D2I(); //142

  CodeStream D2L(); //143

  CodeStream D2F(); //144

  CodeStream I2B(); //145

  CodeStream I2C(); //146

  CodeStream I2S(); //147

  //----------------------------------------------------------------------------------------------------------------

  CodeStream LCMP(); //148

  CodeStream FCMPL(); //149

  //----------------------------------------------------------------------------------------------------------------

  CodeStream FCMPG(); //150

  CodeStream DCMPL(); //151

  CodeStream DCMPG(); //152

  //----------------------------------------------------------------------------------------------------------------

  CodeStream IFEQ(LABEL label); //153

  CodeStream IFNE(LABEL label); //154

  CodeStream IFLT(LABEL label); //155

  CodeStream IFGE(LABEL label); //156

  CodeStream IFGT(LABEL label); //157

  CodeStream IFLE(LABEL label); //158

  CodeStream IF_ICMPEQ(LABEL label); //159

  CodeStream IF_ICMPNE(LABEL label); //160

  CodeStream IF_ICMPLT(LABEL label); //161

  CodeStream IF_ICMPGE(LABEL label); //162

  CodeStream IF_ICMPGT(LABEL label); //163

  CodeStream IF_ICMPLE(LABEL label); //164

  CodeStream IF_ACMPEQ(LABEL label); //165

  CodeStream IF_ACMPNE(LABEL label); //166

  //----------------------------------------------------------------------------------------------------------------

  CodeStream GOTO(LABEL label); //167

  CodeStream JSR(LABEL label); //168

  CodeStream RET(int var); //169

  //----------------------------------------------------------------------------------------------------------------

  CodeStream TABLESWITCH(int min, int max, LABEL defaultLabel, LABEL... labels); //170

  CodeStream LOOKUPSWITCH(LABEL defaultLabel, int[] keys, LABEL[] labels); //171

  //----------------------------------------------------------------------------------------------------------------

  CodeStream IRETURN(); //172

  CodeStream LRETURN(); //173

  CodeStream FRETURN(); //174

  CodeStream DRETURN(); //175

  CodeStream ARETURN(); //176

  CodeStream RETURN(); //177

  //----------------------------------------------------------------------------------------------------------------

  CodeStream GETSTATIC(String owner, String name, String desc); //178

  CodeStream PUTSTATIC(String owner, String name, String desc); //179

  CodeStream GETFIELD(String owner, String name, String desc); //180

  CodeStream PUTFIELD(String owner, String name, String desc); //181

  //----------------------------------------------------------------------------------------------------------------

  /**
   * @param owner
   * @param name
   * @param desc
   * @return
   */
  CodeStream INVOKEVIRTUAL(String owner, String name, String desc); //182

  /**
   * @param owner
   * @param name
   * @param desc
   * @return
   */
  CodeStream INVOKESPECIAL(String owner, String name, String desc); //183

  /**
   * @param owner       - internal name of a class.
   * @param name
   * @param desc
   * @param isInterface whether the owner is an interface or not
   */
  CodeStream INVOKESTATIC(String owner, String name, String desc, boolean isInterface); //184

  /**
   * @param owner
   * @param name
   * @param desc
   * @return
   */
  CodeStream INVOKEINTERFACE(String owner, String name, String desc); //185

  /**
   * @param name
   * @param desc
   * @param bsm
   * @param bsmArgs
   * @return
   */
  CodeStream INVOKEDYNAMIC(String name, String desc, Handle bsm, Object... bsmArgs); //186

  //----------------------------------------------------------------------------------------------------------------

  CodeStream NEW(String internalType); //187

  CodeStream NEWARRAY(ArrayType type); //188

  //----------------------------------------------------------------------------------------------------------------

  CodeStream ANEWARRAY(String internalType); //189

  CodeStream ARRAYLENGTH(); //190

  CodeStream ATHROW(); //191

  CodeStream CHECKCAST(String internalType); //192

  //----------------------------------------------------------------------------------------------------------------

  CodeStream INSTANCEOF(String internalType); //193

  CodeStream MONITORENTER(); //194

  CodeStream MONITOREXIT(); //195

  //----------------------------------------------------------------------------------------------------------------

  CodeStream MULTIANEWARRAY(String internalType, int dims); //197

  //----------------------------------------------------------------------------------------------------------------

  CodeStream IFNULL(LABEL label); //198

  CodeStream IFNONNULL(LABEL label); //199

  //----------------------------------------------------------------------------------------------------------------

  CodeStream LABEL(LABEL label);

  CodeStream TRY_CATCH(LABEL startLabel, LABEL endLabel, LABEL handler, String type);

  CodeStream FRAME(Frames type, int nLocal, Object[] local, int nStack, Object[] stack);

  CodeStream LOCAL_VARIABLE(String name, String desc, String signature, LABEL start, LABEL end, int index);

  CodeStream LINE_NUMBER(int line, LABEL start);

  CodeStream MAXS(int maxStack, int maxLocals);

  void END();

  //#################################################################################################
}
