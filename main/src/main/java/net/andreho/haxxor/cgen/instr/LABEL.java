package net.andreho.haxxor.cgen.instr;

import net.andreho.asm.org.objectweb.asm.Label;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.instr.abstr.AbstractPseudoInstruction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static net.andreho.haxxor.Utils.isUninitialized;

/**
 * <br/>Created by a.hofmann on 11.03.2016.<br/>
 */
public class LABEL
    extends AbstractPseudoInstruction
    implements HxInstruction {

  private Label asmLabel;
  private HxInstruction next;
  private HxInstruction previous;
  private List<HxInstruction> references = Collections.emptyList();

  public LABEL() {
    this(new Label());
  }

  public LABEL(Label asmLabel) {
    this(asmLabel, false);
  }

  public LABEL(Label asmLabel, boolean link) {
    super();
    this.asmLabel = asmLabel;
    if(link) {
      this.asmLabel.info = this;
    }
  }

  @Override
  public void dumpTo(HxComputingContext context, HxCodeStream codeStream) {
    context.getVisitedLabels()
           .putIfAbsent(this, getPrevious());

    codeStream.LABEL(this);
  }

  public Label getAsmLabel() {
    return this.asmLabel;
  }

  public LABEL addReference(HxInstruction instruction) {
    if(isUninitialized(references)) {
      references = new ArrayList<>(1);
    }
    references.add(instruction);
    return this;
  }

  @Override
  public HxInstruction getPrevious() {
    return previous;
  }

  @Override
  public void setPrevious(HxInstruction previous) {
    this.previous = previous;
  }

  @Override
  public HxInstruction getNext() {
    return next;
  }

  @Override
  public void setNext(HxInstruction next) {
    this.next = next;
  }

  @Override
  public String toString() {
    int count = 0;
    HxInstruction instruction = getPrevious();
    while (instruction != null && !instruction.isBegin()) {
      if (instruction instanceof LABEL) {
        count++;
      }
      instruction = instruction.getPrevious();
    }

    return "  L" + count;
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
