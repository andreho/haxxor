package net.andreho.haxxor.cgen;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public interface HxVisitable {

  /**
   * Dumps this instruction to the given {@link HxCodeStream code stream}
   *
   * @param codeStream to dump this instruction to
   */
  void visit(HxCodeStream codeStream);
}
