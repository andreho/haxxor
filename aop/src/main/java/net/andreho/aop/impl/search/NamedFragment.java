package net.andreho.aop.impl.search;

import net.andreho.haxxor.spec.api.HxNamed;

import java.util.regex.Pattern;

/**
 * <br/>Created by a.hofmann on 15.06.2017 at 01:42.
 */
public class NamedFragment<T extends HxNamed>
    extends AbstractFragment<T> {

  private final Pattern pattern;

  public NamedFragment(final String pattern,
                       final boolean asRegExp) {
    if(asRegExp) {
      this.pattern = Pattern.compile(pattern);
    } else {
      this.pattern = Pattern.compile(toAntLikePattern(pattern));
    }
  }

  private static String toAntLikePattern(final String pattern) {
    StringBuilder antPattern = new StringBuilder();
    char prev = 0;
    for (int i = 0, len = pattern.length(); i < len; i++) {
      char c = pattern.charAt(i);
      if(c == '*') {
        if(prev != '\\') {
          if(i + 1 < len && '*' == pattern.charAt( i + 1)) {
            antPattern.append("(:?[\\p{L}\\d_]+\\.?)*");
            i++;
          } else {
            antPattern.append("(:?[\\$\\p{L}\\d_])*");
          }
          continue;
        }
      } else if (c == '?') {
        if(prev != '\\') {
          antPattern.append("(:?[\\$\\p{L}\\d_])");
          continue;
        }
      } else if (c == '.') {
        if(prev != '\\') {
          antPattern.append('\\');
        }
      }

      antPattern.append(c);
      prev = c;
    }
    return antPattern.toString();
  }

  @Override
  public boolean test(final T named) {
    return pattern.matcher(named.getName()).matches();
  }

  @Override
  public String toString() {
    return pattern.toString();
  }
}
