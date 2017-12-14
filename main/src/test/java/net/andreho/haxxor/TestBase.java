package net.andreho.haxxor;

import difflib.Delta;
import difflib.DiffUtils;
import net.andreho.haxxor.api.HxType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <br/>Created by a.hofmann on 13.12.2017 at 18:55.
 */
public class TestBase extends TestCases {

  protected static void iterateAlongSuperclasses(Class<?> cls, HxType type, BiConsumer<Class<?>, HxType> handler) {
    Class<?> superclass = cls;
    Optional<HxType> superTypeOptional = Optional.of(type);

    while(superclass != null) {
      assertTrue(superTypeOptional.isPresent());
      HxType supertype = superTypeOptional.get();

      handler.accept(superclass, supertype);

      superclass = superclass.getSuperclass();
      superTypeOptional = supertype.getSupertype();
    }

    if(!cls.isInterface()) {
      assertFalse(superTypeOptional.isPresent());
    }
  }

  protected static String transformToArrayClassname(final HxType type) {
    return type.toDescriptor().replace('/', '.');
  }

  protected static void compareClassnames(final Class<?> cls, final HxType type) {
    assertEquals(cls.getName(), cls.isArray()? transformToArrayClassname(type) : type.getName());
  }

  protected static boolean handleFrameDifference(String original, String actual, ModificationType type) {
    if(original.contains(".visitFrame(Opcodes.F_") &&
       actual.contains(".visitFrame(Opcodes.F_")) {
      return true;
    }
    return false;
  }

  protected static boolean hasDifference(final String original,
                                         final String actual) {
    return hasDifference(original, actual, TestBase::handleFrameDifference);
  }

  protected static boolean hasDifference(final String original,
                                         final String actual,
                                         final ConflictHandler conflictHandler) {

    List<String> originalLines = Arrays.asList(original.split("\n"));
    List<String> actualLines = Arrays.asList(actual.split("\n"));

    final difflib.Patch<String> diff = DiffUtils.diff(
      Arrays.asList(original.split("\n")),
      Arrays.asList(actual.split("\n"))
    );

    int deletedNotFound = 0;
    int insertedNotFound = 0;
    int changed = 0;
//    Set<String> deletedDiffSet = new HashSet<>();
//    Set<String> insertedDiffSet = new HashSet<>();
    for (Delta<String> delta : diff.getDeltas()) {
      final String originalFragment = toString(delta.getOriginal().getLines()).trim();
      final String revisedFragment = toString(delta.getRevised().getLines()).trim();

      if (delta.getType() == Delta.TYPE.DELETE) {
        if (!conflictHandler.handleConflict(originalFragment, revisedFragment, ModificationType.DELETE)) {
//          deletedDiffSet.add(revisedFragment);
          deletedNotFound++;
        }
      } else if (delta.getType() == Delta.TYPE.INSERT) {
        if (!conflictHandler.handleConflict(originalFragment, revisedFragment, ModificationType.INSERT)) {
//          insertedDiffSet.add(revisedFragment);
          insertedNotFound++;
        }
      } else {
        if (conflictHandler.handleConflict(originalFragment, revisedFragment, ModificationType.CHANGE)) {
          continue;
        }
        changed++;
      }
    }

    return changed > 0 ||
           deletedNotFound > 0 ||
           insertedNotFound > 0;
//           !deletedDiffSet.isEmpty() ||
//           !insertedDiffSet.isEmpty() ||
//           !insertedDiffSet.containsAll(deletedDiffSet);
  }

  private static String toString(final List<String> strings) {
    int from = 0;
    int to = strings.size();

    for (String s : strings) {
      String trim = s.trim();
      if ("{".equals(trim) || "}".equals(trim)) {
        from++;
      } else {
        break;
      }
    }

    for (int i = strings.size() - 1; i >= 0; i--) {
      String trim = strings.get(i).trim();
      if ("{".equals(trim) || "}".equals(trim)) {
        to--;
      } else {
        break;
      }
    }

    final List<String> subList = strings.subList(from, to);
    final StringJoiner stringJoiner = new StringJoiner("\n");
    subList.forEach(stringJoiner::add);

    final String actual = stringJoiner.toString().trim();
    return actual;
  }

  @FunctionalInterface
  public interface ConflictHandler {

    /**
     * @param original
     * @param actual
     * @param type
     * @return <b>true</b> if conflict can be fixed and isn't critical, <b>false</b> otherwise
     */
    boolean handleConflict(String original,
                           String actual,
                           ModificationType type);
  }

  /**
   * Specifies the type of the delta.
   */
  public enum ModificationType {
    /**
     * A change in the original.
     */
    CHANGE,
    /**
     * A delete from the original.
     */
    DELETE,
    /**
     * An insert into the original.
     */
    INSERT
  }
}
