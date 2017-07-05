package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionFactory;
import net.andreho.haxxor.cgen.HxInstructionSort;
import net.andreho.haxxor.cgen.HxInstructionsType;
import net.andreho.haxxor.cgen.HxLinkedMethodBody;
import net.andreho.haxxor.cgen.HxLocalVariable;
import net.andreho.haxxor.cgen.HxTryCatch;
import net.andreho.haxxor.cgen.impl.ExtendedInstructionCodeStream;
import net.andreho.haxxor.cgen.impl.HxLocalVariableImpl;
import net.andreho.haxxor.cgen.impl.HxTryCatchImpl;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSimpleJumpInstruction;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSwitchJumpInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxMethodBody;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static net.andreho.haxxor.Utils.isUninitialized;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public class HxMethodBodyImpl
  extends HxLinkedMethodBody
  implements HxMethodBody {

  private static final HxInstructionFactory DEFAULT_INSTRUCTION_FACTORY = new HxInstructionFactory() {
  };

  private final HxMethod owner;

  public HxMethodBodyImpl(final HxMethod owner) {
    super();
    this.owner = Objects.requireNonNull(owner);
  }

  @Override
  public HxMethodBody moveTo(final HxMethod newOwner) {
    HxMethodBodyImpl newBody = new HxMethodBodyImpl(newOwner);

    newBody.first = first;
    newBody.last = last;
    newBody.tryCatches = tryCatches;
    newBody.localVariables = localVariables;
    newBody.maxLocals = maxLocals;
    newBody.maxStack = maxStack;

    this.reset();

    newOwner.setBody(newBody);
    return newBody;
  }

  @Override
  public HxMethodBody clone(HxMethod owner) {
    final HxMethodBody body = this;
    final HxMethodBody otherBody = new HxMethodBodyImpl(owner);
    final HxCodeStream stream = otherBody.build();

    final List<HxTryCatch> tryCatches = body.getTryCatches();
    final List<HxLocalVariable> localVariables = body.getLocalVariables();
    final Map<Object, LABEL> mapping = new IdentityHashMap<>(32);

    for(HxTryCatch tryCatch : tryCatches) {
      final LABEL start = remap(mapping, tryCatch.getStart());
      final LABEL end = remap(mapping, tryCatch.getEnd());
      final LABEL handler = remap(mapping, tryCatch.getHandler());

      otherBody.addTryCatch(new HxTryCatchImpl(start, end, handler, tryCatch.getType()));
    }

    for(HxLocalVariable localVariable : localVariables) {
      final LABEL start = remap(mapping, localVariable.getStart());
      final LABEL end = remap(mapping, localVariable.getEnd());

      otherBody.addLocalVariable(
        new HxLocalVariableImpl(
          localVariable.getIndex(),
          localVariable.getName(),
          localVariable.getDescriptor(),
          localVariable.getSignature(),
          start,
          end
        )
      );
    }

    for (HxInstruction instruction : body) {
      if(instruction.hasSort(HxInstructionSort.Jump)) {
        AbstractSimpleJumpInstruction jump = (AbstractSimpleJumpInstruction) instruction;
        instruction = jump.clone(remap(mapping, jump.getLabel()));
      } else if(instruction.hasSort(HxInstructionSort.Switches)) {
        AbstractSwitchJumpInstruction aSwitch = (AbstractSwitchJumpInstruction) instruction;
        instruction = aSwitch.clone(remap(mapping, aSwitch.getDefaultLabel()), remap(mapping, aSwitch.getLabels()));
      } else if(instruction.hasType(HxInstructionsType.Special.LABEL)) {
        stream.LABEL(remap(mapping, (LABEL) instruction));
        continue;
      }

      instruction.visit(stream);
    }

    otherBody.setMaxLocals(body.getMaxLocals());
    otherBody.setMaxStack(body.getMaxStack());
    return null;
  }

  private static LABEL remap(final Map<Object, LABEL> mapping, final LABEL label) {
    return mapping.computeIfAbsent(label, key -> new LABEL());
  }

  private static LABEL[] remap(final Map<Object, LABEL> mapping, final LABEL[] labels) {
    LABEL[] remappedLabels = new LABEL[labels.length];
    for (int i = 0; i < labels.length; i++) {
      remappedLabels[i] = remap(mapping, labels[i]);
    }
    return remappedLabels;
  }


  @Override
  public HxMethodBody addLocalVariable(final HxLocalVariable localVariable) {
    List<HxLocalVariable> localVariables = this.localVariables;
    if (isUninitialized(localVariables)) {
      this.localVariables = localVariables = new ArrayList<>(2);
    }
    localVariables.add(localVariable);
    return this;
  }

  @Override
  public HxMethodBody addTryCatch(final HxTryCatch tryCatch) {
    List<HxTryCatch> tryCatches = this.tryCatches;
    if (isUninitialized(tryCatches)) {
      this.tryCatches = tryCatches = new ArrayList<>(2);
    }
    tryCatches.add(tryCatch);
    return this;
  }

  @Override
  public HxMethod getMethod() {
    return this.owner;
  }

  @Override
  public HxInstructionFactory getInstructionFactory() {
    return DEFAULT_INSTRUCTION_FACTORY;
  }



  @Override
  public <S extends HxCodeStream<S>> S build(final boolean rebuild) {
    if (rebuild) {
      reset();
    }
    return (S) new ExtendedInstructionCodeStream(this);
  }

  @Override
  public <S extends HxCodeStream<S>> S build(final boolean rebuild,
                                             final Function<HxMethodBody, S> streamFactory) {
    if (rebuild) {
      reset();
    }
    return streamFactory.apply(this);
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append(getFirst()).append('\n');

    //TRY-CATCH
    if(hasTryCatch()) {
      builder.append('\n');
      for(HxTryCatch tryCatch : getTryCatches()) {
        builder.append(tryCatch).append('\n');
      }
      builder.append('\n');
    }

    //CODE
    for(HxInstruction instruction : getFirst().getNext()) {
      if(instruction.isEnd()) {
        //LOCAL-VARIABLES
        if(hasLocalVariables()) {
          builder.append('\n');
          for(HxLocalVariable localVariable : getLocalVariables()) {
            builder.append(localVariable).append('\n');
          }
          builder.append('\n');
        }
        builder.append("MAX(").append(getMaxStack()).append(",").append(getMaxLocals()).append(")\n");
      }
      builder.append(instruction).append('\n');
    }
    return builder.toString();
  }
}
