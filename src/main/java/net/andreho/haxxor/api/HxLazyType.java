package net.andreho.haxxor.api;

/**
 * <br/>Created by a.hofmann on 03.03.2018 at 23:09.
 */
public interface HxLazyType {

  void addFieldInternally(final HxField field);

  void addMethodInternally(final HxMethod method);
}
