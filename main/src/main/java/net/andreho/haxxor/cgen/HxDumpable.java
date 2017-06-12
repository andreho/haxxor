package net.andreho.haxxor.cgen;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public interface HxDumpable {

  /**
   * Dumps this instruction to the given {@link HxCodeStream code stream} using the provided {@link HxComputingContext context}
   *
   * @param context    to use
   * @param codeStream to dump this instruction to
   */
  void dumpTo(HxComputingContext context, HxCodeStream codeStream);
}
