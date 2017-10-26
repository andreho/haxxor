package net.andreho.haxxor;

import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.api.HxTypeReference;
import net.andreho.haxxor.spi.HxClassnameNormalizer;
import net.andreho.haxxor.spi.HxElementFactory;
import net.andreho.haxxor.spi.HxVerificationResult;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 07.06.2017 at 22:31.
 */
public interface Hx extends HxClassnameNormalizer, HxElementFactory {

  HxType initialize(HxType type);

  HxField initialize(HxField field);

  HxMethod initialize(HxMethod method);

  HxVerificationResult verify(HxType type);

  HxVerificationResult verify(HxField field);

  HxVerificationResult verify(HxMethod method);

  boolean hasReference(String classname);

  HxTypeReference reference(String classname);

  HxTypeReference reference(Class<?> aClass);

  List<HxType> referencesAsList(String... classnames);

  HxType[] referencesAsArray(String... classnames);

  boolean hasResolved(String classname);

  HxType resolve(String classname);

  HxType resolve(Class<?> aClass);

  HxType resolve(String classname,
                 int flags);

  HxType resolve(String classname,
                 byte[] byteCode,
                 int flags);

  HxType register(String classname,
                  HxType typeOrReference);
}
