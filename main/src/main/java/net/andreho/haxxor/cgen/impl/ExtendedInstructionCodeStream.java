package net.andreho.haxxor.cgen.impl;

import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstructionFactory;
import net.andreho.haxxor.cgen.HxLocalVariable;
import net.andreho.haxxor.cgen.HxTryCatch;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.spec.api.HxCode;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public class ExtendedInstructionCodeStream<Stream extends HxExtendedCodeStream<Stream>>
  extends InstructionCodeStream<Stream> {

  private final HxCode code;

  public ExtendedInstructionCodeStream(final HxCode code) {
    this(code, code.getInstructionFactory());
  }

  public ExtendedInstructionCodeStream(final HxCode code,
                                       final HxInstructionFactory factory) {
    super(code.getCurrent(), factory);
    this.code = code;
  }

  private Stream asStream() {
    return (Stream) this;
  }

  @Override
  public Stream TRY_CATCH(final LABEL startLabel,
                          final LABEL endLabel,
                          final LABEL handler,
                          final String type) {
    this.code.addTryCatch(new HxTryCatch(startLabel, endLabel, handler, type));
    return asStream();
  }


  @Override
  public Stream LOCAL_VARIABLE(final String name,
                               final String desc,
                               final String signature,
                               final LABEL start,
                               final LABEL end,
                               final int index) {
    this.code.addLocalVariable(new HxLocalVariable(name, desc, signature, start, end, index));
    return asStream();
  }

  @Override
  public Stream MAXS(final int maxStack,
                     final int maxLocals) {
    this.code.setMaxStack(maxStack);
    this.code.setMaxLocals(maxLocals);
    return asStream();
  }
}
