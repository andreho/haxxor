package net.andreho.aop.spi.impl.matchers;

import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * <br/>Created by a.hofmann on 18.06.2017 at 07:08.
 */
public class SignatureMatcher
    extends AbstractMatcher<HxMethod> {

  private final String[] signature;
  private final boolean any;

  public SignatureMatcher(final Collection<String> signature) {
    this(signature.toArray(new String[0]));
  }

  public SignatureMatcher(final String[] signature) {
    this.signature = signature;
    this.any = signature.length == 1 && "void".equals(signature[0]);
  }

  public SignatureMatcher(final HxType[] signature) {
    this(Stream.of(signature).map(HxType::getName).toArray(String[]::new));
  }

  @Override
  public boolean isAny() {
    return any;
  }

  @Override
  public boolean matches(final HxMethod element) {
    if(isAny()) {
      return true;
    }

    final String[] signature = this.signature;
    if(signature.length != element.getParametersCount()) {
      return false;
    }

    for (int i = 0; i < signature.length; i++) {
      if(!signature[i].equals(element.getParameterAt(i).getName())) {
        return false;
      }
    }
    return true;
  }
}
