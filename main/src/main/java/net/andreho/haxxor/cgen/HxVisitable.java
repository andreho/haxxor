package net.andreho.haxxor.cgen;

/**
 * <br/>Created by a.hofmann on 18.03.2016.<br/>
 */
public interface HxVisitable {

  /**
   * Visits the implementing visitable element and executes some operations on the given {@link HxCodeStream code stream}
   *
   * @param codeStream to use
   */
  void visit(HxCodeStream codeStream);
}
