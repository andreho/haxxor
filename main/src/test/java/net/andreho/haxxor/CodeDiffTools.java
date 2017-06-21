package net.andreho.haxxor;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.PatchFailedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 06:43.
 */
public class CodeDiffTools {
  public static String noCodeDiff(String expected, String actual)
  throws PatchFailedException {
    expected = expected.replaceFirst("cw\\.visitSource\\([^\\)]+\\);\\s+", "");
    return checkDiff(expected, actual);
  }

  private static String checkDiff(final String expected,
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

  private static String toString(final List<String> strings) {
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
