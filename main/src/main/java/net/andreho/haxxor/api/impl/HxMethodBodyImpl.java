package net.andreho.haxxor.api.impl;

import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxMethodBody;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionFactory;
import net.andreho.haxxor.cgen.HxInstructionSort;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.HxLinkedMethodBody;
import net.andreho.haxxor.cgen.HxLocalVariable;
import net.andreho.haxxor.cgen.HxTryCatch;
import net.andreho.haxxor.cgen.impl.ExtendedInstructionCodeStream;
import net.andreho.haxxor.cgen.impl.HxLocalVariableImpl;
import net.andreho.haxxor.cgen.impl.HxTryCatchImpl;
import net.andreho.haxxor.cgen.instr.abstr.BasicJumpInstruction;
import net.andreho.haxxor.cgen.instr.abstr.SwitchJumpInstruction;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static net.andreho.haxxor.utils.CommonUtils.isUninitialized;

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
    HxMethodBodyImpl newBody = createMethodBody(newOwner);

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
  public HxMethodBody copyTo(final HxMethod newOwner) {
    HxMethodBody body = clone(newOwner);
    newOwner.setBody(body);
    return body;
  }

  @Override
  public HxMethodBody clone(HxMethod owner) {
    final HxMethodBody body = this;
    final HxMethodBody otherBody = createMethodBody(owner);
    final HxCodeStream stream = otherBody.build();

    final List<HxTryCatch> tryCatches = body.getTryCatches();
    final List<HxLocalVariable> localVariables = body.getLocalVariables();
    final Map<Object, LABEL> mapping = new IdentityHashMap<>(32);

    copyTryCatches(otherBody, tryCatches, mapping);
    copyLocalVariables(otherBody, localVariables, mapping);
    copyInstructions(body, stream, mapping);

    otherBody.setMaxLocals(body.getMaxLocals());
    otherBody.setMaxStack(body.getMaxStack());
    return otherBody;
  }

  protected HxMethodBodyImpl createMethodBody(final HxMethod owner) {
    return new HxMethodBodyImpl(owner);
  }

  private void copyInstructions(final HxMethodBody body,
                                final HxCodeStream stream,
                                final Map<Object, LABEL> mapping) {
    for (HxInstruction instruction : body) {
      if(instruction.hasSort(HxInstructionSort.Jump)) {
        BasicJumpInstruction jump = (BasicJumpInstruction) instruction;
        instruction = jump.clone(remap(mapping, jump.getLabel()));
      } else if(instruction.hasSort(HxInstructionSort.Switch)) {
        SwitchJumpInstruction aSwitch = (SwitchJumpInstruction) instruction;
        instruction = aSwitch.clone(remap(mapping, aSwitch.getDefaultLabel()), remap(mapping, aSwitch.getLabels()));
      } else if(instruction.hasType(HxInstructionTypes.Special.LABEL)) {
        stream.LABEL(remap(mapping, (LABEL) instruction));
        continue;
      }

      instruction.visit(stream);
    }
  }

  private void copyLocalVariables(final HxMethodBody otherBody,
                                  final List<HxLocalVariable> localVariables,
                                  final Map<Object, LABEL> mapping) {
    for(HxLocalVariable localVariable : localVariables) {
      final LABEL start = remap(mapping, localVariable.getBegin());
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
  }

  private void copyTryCatches(final HxMethodBody otherBody,
                              final List<HxTryCatch> tryCatches,
                              final Map<Object, LABEL> mapping) {
    for(HxTryCatch tryCatch : tryCatches) {
      final LABEL start = remap(mapping, tryCatch.getBegin());
      final LABEL end = remap(mapping, tryCatch.getEnd());
      final LABEL handler = remap(mapping, tryCatch.getCatch());

      otherBody.addTryCatch(new HxTryCatchImpl(start, end, handler, tryCatch.getExceptionType()));
    }
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
  public HxMethodBody addTryCatch(final LABEL begin,
                                  final LABEL end,
                                  final LABEL handler,
                                  final String handledException) {
    return addTryCatch(new HxTryCatchImpl(begin, end, handler, handledException));
  }

  @Override
  public List<HxTryCatch> findTryCatch(final String exceptionType) {
    List<HxTryCatch> list = Collections.emptyList();
    for(HxTryCatch tryCatch : getTryCatches()) {
      if(Objects.equals(exceptionType, tryCatch.getExceptionType())) {
        if(list == Collections.EMPTY_LIST) {
          list = new ArrayList<>(2);
        }
        list.add(tryCatch);
      }
    }
    return list;
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
    HxInstruction instruction = this.getLast();
    if(instruction != null) {
      instruction = instruction.getPrevious();
    } else {
      instruction = this;
    }
    return (S) new ExtendedInstructionCodeStream(this, instruction);
  }

  @Override
  public <S extends HxCodeStream<S>> S build(final boolean rebuild, final Function<HxMethodBody, S> streamFactory) {
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
    if(hasTryCatches()) {
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
