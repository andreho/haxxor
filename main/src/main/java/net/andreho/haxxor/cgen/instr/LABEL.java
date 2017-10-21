package net.andreho.haxxor.cgen.instr;

import net.andreho.asm.org.objectweb.asm.Label;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static net.andreho.haxxor.Utils.isUninitialized;

/**
 * <br/>Created by a.hofmann on 11.03.2016.<br/>
 */
public class LABEL
    extends AbstractInstruction
    implements HxInstruction {

  private Label asmLabel;
  private List<HxInstruction> references = Collections.emptyList();

  public LABEL() {
    this(new Label());
  }

  public LABEL(Label asmLabel) {
    this(asmLabel, false);
  }

  public LABEL(Label asmLabel, boolean link) {
    super(-1);
    this.asmLabel = asmLabel;
    if(link) {
      this.asmLabel.info = this;
    }
  }

  protected String prefix() {
    return "L";
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Special.LABEL;
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    context.getVisitedLabels()
           .putIfAbsent(this, getPrevious());
    return NO_STACK_PUSH;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.LABEL(this);
  }

  public Label getAsmLabel() {
    return this.asmLabel;
  }

  public <I extends HxInstruction> I addReference(I instruction) {
    if(isUninitialized(references)) {
      references = new ArrayList<>(1);
    }
    references.add(instruction);
    return instruction;
  }

  public String print() {
    int count = 0;
    HxInstruction instruction = getPrevious();
    while (instruction != null && !instruction.isBegin()) {
      if (instruction instanceof LABEL) {
        count++;
      }
      instruction = instruction.getPrevious();
    }
    final String prefix = prefix();
    return ("L".equals(prefix)? "L" : prefix + "#") + count;
  }

  @Override
  public String toString() {
    return "  " + print();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final LABEL that = (LABEL) o;
    return Objects.equals(this.asmLabel, that.asmLabel);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.asmLabel);
  }
}
