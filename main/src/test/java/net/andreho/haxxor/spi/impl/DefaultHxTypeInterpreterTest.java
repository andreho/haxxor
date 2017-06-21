package net.andreho.haxxor.spi.impl;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.PatchFailedException;
import net.andreho.haxxor.Debugger;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.model.AnnotationA;
import net.andreho.haxxor.model.AnnotationB;
import net.andreho.haxxor.model.AnnotationC;
import net.andreho.haxxor.model.ComplexBean;
import net.andreho.haxxor.model.EnumA;
import net.andreho.haxxor.model.GenericInterfaceA;
import net.andreho.haxxor.model.MinimalBean;
import net.andreho.haxxor.model.SpecificBean;
import net.andreho.haxxor.model.SupportedSpecBean;
import net.andreho.haxxor.spec.api.HxType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <br/>Created by a.hofmann on 14.06.2017 at 02:32.
 */
class DefaultHxTypeInterpreterTest {
  static List<Class<?>> testClasses() {
    return Arrays.asList(
        MinimalBean.class,
        EnumA.class,
        SpecificBean.class,
        ComplexBean.class,
        GenericInterfaceA.class,
        SupportedSpecBean.class,
        AnnotationA.class,
        AnnotationB.class,
        AnnotationC.class
//        OverAnnotatedValueBean.class
    );
  }

  @ParameterizedTest
  @MethodSource(names = "testClasses")
  void test(Class<?> cls)
  throws PatchFailedException {
    Haxxor haxxor = new Haxxor();
    HxType hxType = haxxor.resolve(cls.getName());
    byte[] bytes = hxType.toByteCode();

    StringWriter original = new StringWriter();
    Debugger.trace(cls, new PrintWriter(original));
    StringWriter made = new StringWriter();
    Debugger.trace(bytes, new PrintWriter(made));

    String expected = original.toString().trim();
    String actual = made.toString();

    expected = expected.replaceFirst("cw\\.visitSource\\([^\\)]+\\);\\s+", "");

    actual = checkDiff(expected, actual);

    System.out.println(actual);
  }

  @Test
  @Disabled
  void failedTestCase()
  throws PatchFailedException {
    test(SupportedSpecBean.class);
  }

  private String checkDiff(final String expected,
                           final String actual)
  throws PatchFailedException {
    List<String> originalLines = new ArrayList<>(Arrays.asList(expected.split("\n")));
    List<String> madeLines  = new ArrayList<>(Arrays.asList(actual.split("\n")));

    difflib.Patch<String> diff = DiffUtils.diff(originalLines, madeLines);

    int deletedNotFound = 0;
    int insertedNotFound = 0;
    int changed = 0;

    Set<String> deletedDiffSet = new HashSet<>();
    Set<String> insertedDiffSet = new HashSet<>();

    for(Delta<String> delta : diff.getDeltas()) {
      if(delta.getType() == Delta.TYPE.DELETE ) {
        String fragment = toString(delta.getOriginal().getLines());
        if(!actual.contains(fragment)) {
          deletedDiffSet.add(fragment);
          deletedNotFound++;
        }
      } else if(delta.getType() == Delta.TYPE.INSERT) {
        String fragment = toString(delta.getOriginal().getLines());
        if(!actual.contains(fragment)) {
          insertedDiffSet.add(fragment);
          insertedNotFound++;
        }
      } else if(delta.getType() == Delta.TYPE.INSERT) {
        changed++;
      }
    }

    boolean failed = changed > 0 || !deletedDiffSet.isEmpty() || !insertedDiffSet.isEmpty() || !insertedDiffSet.containsAll(deletedDiffSet);

    if(failed) {
      assertEquals(expected, actual);
    }

    return toString(madeLines);
  }

  private String toString(final List<String> strings) {
    int from = 0;
    int to = strings.size();

    for(String s : strings) {
      String trim = s.trim();
      if("{".equals(trim) || "}".equals(trim)) {
        from++;
      } else {
        break;
      }
    }

    for (int i = strings.size() - 1; i >= 0; i--) {
      String trim = strings.get(i).trim();
      if("{".equals(trim) || "}".equals(trim)) {
        to--;
      } else {
        break;
      }
    }

    final List<String> subList = strings.subList(from, to);
    final String actual;StringJoiner stringJoiner = new StringJoiner("\n");
    subList.forEach(stringJoiner::add);
    actual = stringJoiner.toString().trim();
    return actual;
  }
}