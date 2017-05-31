package net.andreho.haxxor.spi;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 08.04.2017 at 08:50.
 */
public interface HxElementFactory {

  /**
   * @param typeName
   * @return
   */
  HxType createType(final Haxxor haxxor,
                    final String typeName);

  HxType createReference(final Haxxor haxxor,
                         final String typeName);

  HxField createField(final Haxxor haxxor,
                      final String typeName,
                      final String name);

  HxConstructor createConstructor(final Haxxor haxxor,
                                  final String... parameterTypes);

  HxConstructor createConstructorReference(final Haxxor haxxor,
                                           final String... parameterTypes);

  HxMethod createMethod(final Haxxor haxxor,
                        final String name,
                        final String returnType,
                        final String... parameterTypes);

  HxMethod createMethodReference(final Haxxor haxxor,
                                 final String name,
                                 final String returnType,
                                 final String... parameterTypes);

  HxParameter createParameter(final Haxxor haxxor,
                              final String typeName);

  HxAnnotation createAnnotation(final Haxxor haxxor,
                                final String typeName,
                                final boolean visible);
}
