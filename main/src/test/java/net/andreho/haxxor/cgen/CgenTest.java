package net.andreho.haxxor.cgen;

import difflib.PatchFailedException;
import net.andreho.haxxor.CodeDiffTools;
import net.andreho.haxxor.Debugger;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.utils.CommonUtils;
import net.andreho.resources.Resource;
import net.andreho.resources.ResourceResolver;
import net.andreho.resources.ResourceScanner;
import net.andreho.resources.ResourceSourceLocator;
import net.andreho.resources.ResourceType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 06:20.
 */
public class CgenTest {

  private static Hx haxxor;

  @ParameterizedTest
  @MethodSource("availableClasses")
  void checkClass(final Resource resource)
  throws IOException, PatchFailedException {
    String name = resource.getName();
    name = name.substring(0, name.length() - ".class".length()).replace('/', '.');

    System.out.println(name);

    final HxType hxType = haxxor.resolve(name);
    final byte[] bytes = hxType.toByteCode();

    final StringWriter original = new StringWriter();
    final StringWriter made = new StringWriter();

    Debugger.trace(CommonUtils.toByteArray(resource.getInputStream()), new PrintWriter(original), Debugger.SKIP_DEBUG);
    Debugger.trace(bytes, new PrintWriter(made), Debugger.SKIP_DEBUG);

    String expected = original.toString().trim();
    String actual = made.toString();

    CodeDiffTools.noCodeDiff(expected, actual);
//    System.out.println(actual);
  }

  @Test
  @Disabled
  void specificTestCase()
  throws URISyntaxException, ExecutionException, InterruptedException, IOException, PatchFailedException {
//    System.out.println(HxType.Modifiers.toSet(EmbeddingClassesBean.InnerClass.class.getModifiers()));
//    System.out.println(HxType.Modifiers.toSet(EmbeddingClassesBean.StaticNestedClass.class.getModifiers()));
//    System.out.println(HxType.Modifiers.toSet(new EmbeddingClassesBean().getAnonymousClass().getClass().getModifiers()));
//    System.out.println(HxType.Modifiers.toSet(new EmbeddingClassesBean().getLocalClassFromMethod().getClass().getModifiers()));
//    System.out.println(HxType.Modifiers.toSet(new EmbeddingClassesBean().getLocalClassFromConstructor().getClass().getModifiers()));
    Resource resource = makeResourceMap().get("net/andreho/haxxor/SandBoxTest$Testing.class");
    checkClass(resource);
  }

  @BeforeAll
  static void setup() {
    haxxor = Hx.builder().withClassLoader(CgenTest.class.getClassLoader()).build();
  }

  private static Iterable<Resource> availableClasses()
  throws IOException, URISyntaxException, ExecutionException, InterruptedException {
    final Map<String, Resource> result = makeResourceMap();

    return result.values();
  }

  private static Map<String, Resource> makeResourceMap()
  throws IOException, URISyntaxException, ExecutionException, InterruptedException {
    return ResourceScanner.newScanner(
      ResourceScanner.Parallelism.CONCURRENT,
      ResourceSourceLocator.usingClassLoader(),
      ResourceResolver.with(
        ResourceResolver.newFileResourceResolver(),
        ResourceResolver.newJarResourceResolver()
      ),
      CgenTest::filterClasses,
      ResourceType.CLASS_TYPE
    ).scan(CgenTest.class.getClassLoader());
  }

  private static boolean filterClasses(String name,
                                       Supplier<InputStream> supplier) {
    return name.endsWith(".class");
  }
}
