package net.andreho.haxxor.cgen.instr.misc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.andreho.haxxor.utils.CommonUtils.isUninitialized;

/**
 * <br/>Created by a.hofmann on 11.03.2016.<br/>
 */
public class LABEL
    extends AbstractInstruction
    implements HxInstruction {

  public Object info;
  private List<HxInstruction> references = Collections.emptyList();

  public LABEL() {
  }

  protected String prefix() {
    return "L";
  }

  @Override
  public boolean isPseudoInstruction() {
    return true;
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Special.LABEL;
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.visit(this);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.LABEL(this);
  }

  public <I extends HxInstruction> I addReference(I instruction) {
    if(isUninitialized(references)) {
      references = new ArrayList<>(1);
    }
    references.add(instruction);
    return instruction;
  }

  public List<HxInstruction> getReferences() {
    return references;
  }

  public String toFormattedString() {
    int count = 0;
    HxInstruction instruction = getPrevious();
    while (instruction != null && !instruction.isBegin()) {
      if (instruction.isLabel()) {
        count++;
      }
      instruction = instruction.getPrevious();
    }
    final String prefix = prefix();
    return ("L".equals(prefix)? "L" : prefix + "#") + count;
  }

  @Override
  public LABEL clone() {
    return new LABEL();
  }

  @Override
  protected String print() {
    return "  " + toFormattedString();
  }
}
