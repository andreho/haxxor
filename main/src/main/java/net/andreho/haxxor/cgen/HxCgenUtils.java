package net.andreho.haxxor.cgen;

import net.andreho.haxxor.cgen.instr.abstr.LocalAccessInstruction;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 03:14.
 */
public abstract class HxCgenUtils {

  private HxCgenUtils() {
  }

  /**
   * @param instruction
   * @param slotOffset
   * @param variableSize
   */
  public static void shiftAccessToLocalVariable(final HxInstruction instruction,
                                                final int slotOffset,
                                                final int variableSize) {
    instruction.forEachNext(
      ins -> ins.hasSort(HxInstructionSort.Load) || ins.hasSort(HxInstructionSort.Store),
      ins -> shiftAccessToLocalVariables(ins, slotOffset, variableSize)
    );
  }

  private static void shiftAccessToLocalVariables(final HxInstruction instruction,
                                                  final int parametersOffset,
                                                  final int offsetAddition) {
    final LocalAccessInstruction accessInstruction = (LocalAccessInstruction) instruction;
    int slotIndex = accessInstruction.getOperand();
    if (slotIndex >= parametersOffset) {
      instruction.replaceWith(accessInstruction.clone(slotIndex + offsetAddition));
    }
  }
}
