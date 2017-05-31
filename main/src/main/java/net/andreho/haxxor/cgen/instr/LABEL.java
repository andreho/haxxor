package net.andreho.haxxor.cgen.instr;

import net.andreho.asm.org.objectweb.asm.Label;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.Instruction;
import net.andreho.haxxor.cgen.instr.abstr.AbstractPseudoInstruction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 11.03.2016.<br/>
 */
public class LABEL
    extends AbstractPseudoInstruction
    implements Instruction {
  //----------------------------------------------------------------------------------------------------------------

  private Label asmLabel;
  private Instruction next;
  private Instruction previous;
  private List<Instruction> references = new ArrayList<>();

  //----------------------------------------------------------------------------------------------------------------

  public LABEL() {
    this(new Label());
  }

  public LABEL(Label asmLabel) {
    super();
    this.asmLabel = asmLabel;
  }

  //----------------------------------------------------------------------------------------------------------------

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    context.getVisitedLabels()
           .putIfAbsent(this, getPrevious());
    codeStream.LABEL(this);
  }

  //----------------------------------------------------------------------------------------------------------------

  public Label getAsmLabel() {
    return this.asmLabel;
  }

  public LABEL addReference(Instruction instruction) {
    references.add(instruction);
    return this;
  }

  @Override
  public Instruction getPrevious() {
    return previous;
  }

  @Override
  public void setPrevious(Instruction previous) {
    this.previous = previous;
  }

  @Override
  public Instruction getNext() {
    return next;
  }

  @Override
  public void setNext(Instruction next) {
    this.next = next;
  }

  //----------------------------------------------------------------------------------------------------------------

  @Override
  public String toString() {
    int count = 0;
    Instruction instruction = getPrevious();
    while (instruction != null) {
      if (instruction.getOpcode() == -1 &&
          instruction instanceof LABEL) {
        count++;
      }
      instruction = instruction.getPrevious();
    }

    return "L" + count;
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

  //----------------------------------------------------------------------------------------------------------------
}
