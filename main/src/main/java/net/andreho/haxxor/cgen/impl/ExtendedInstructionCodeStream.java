package net.andreho.haxxor.cgen.impl;

import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstructionFactory;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.spec.api.HxMethodBody;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public class ExtendedInstructionCodeStream
  extends InstructionCodeStream {

  private final HxMethodBody methodBody;

  public ExtendedInstructionCodeStream(final HxMethodBody methodBody) {
    this(methodBody, methodBody.getInstructionFactory());
  }

  public ExtendedInstructionCodeStream(final HxMethodBody methodBody,
                                       final HxInstructionFactory factory) {
    super(methodBody.getCurrent(), factory);
    this.methodBody = methodBody;
  }


  @Override
  public HxExtendedCodeStream TRY_CATCH(final LABEL startLabel,
                          final LABEL endLabel,
                          final LABEL handler,
                          final String type) {
    this.methodBody.addTryCatch(new HxTryCatchImpl(startLabel, endLabel, handler, type));
    return this;
  }


  @Override
  public HxExtendedCodeStream LOCAL_VARIABLE(final String name,
                               final String desc,
                               final String signature,
                               final LABEL start,
                               final LABEL end,
                               final int index) {
    this.methodBody.addLocalVariable(new HxLocalVariableImpl(index, name, desc, signature, start, end));
    return this;
  }

  @Override
  public HxExtendedCodeStream MAXS(final int maxStack,
                     final int maxLocals) {
    this.methodBody.setMaxStack(maxStack);
    this.methodBody.setMaxLocals(maxLocals);
    return this;
  }
}
