package test;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import net.andreho.resources.Resource;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Timeout;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * <br/>Created by a.hofmann on 23.11.2017 at 17:20.
 */
@State(Scope.Thread)
@Threads(2)
@Timeout(time = 5)
@Fork(value = 1, jvmArgs = "-Xms8g -Xmx8g")
@Warmup(time = 1, timeUnit = TimeUnit.MINUTES)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.MINUTES)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
public class JavassistBenchmark
  extends AbstractBenchmark {

  public static void main(String[] args)
  throws Exception {
    final long totalMemory = getTotalMemory();
    final Blackhole blackhole = createBlackhole();
    final JavassistBenchmark benchmark = new JavassistBenchmark();

    //testRead(benchmark, blackhole);

    testWrite(benchmark, blackhole);

    done(blackhole);
  }

  private static void testRead(final JavassistBenchmark benchmark,
                               final Blackhole blackhole)
  throws Exception {
    for (int i = 0; i < 100; i++) {
      System.gc();

      long startTime = System.nanoTime();
      long startMemory = getCurrentlyUsedMemory();

      benchmark.read(blackhole);

      long stopTime = System.nanoTime();
      long stopMemory = getCurrentlyUsedMemory();

      System.out.println(i + "\t" + (stopTime - startTime) + "\t" + (stopMemory - startMemory));
    }
  }

  private static void testWrite(final JavassistBenchmark benchmark,
                                final Blackhole blackhole)
  throws Exception {
    for (int i = 0; i < 100; i++) {
      System.gc();

      long startTime = System.nanoTime();
      long startMemory = getCurrentlyUsedMemory();

      benchmark.write(blackhole);

      long stopTime = System.nanoTime();
      long stopMemory = getCurrentlyUsedMemory();

      System.out.println(i + "\t" + (stopTime - startTime) + "\t" + (stopMemory - startMemory));
    }
  }

  @Override
  @Benchmark
  public void read(final Blackhole bh)
  throws Exception {
    final ClassPool cp = new ClassPool();
    Resource current = null;
    try {
      for (final Resource resource : getResourceCollection()) {
        current = resource;
        final CtClass ctClass = cp.makeClass(resource.getInputStream());
        ctClass.getFields();
        ctClass.getMethods();

        if (ctClass.getName().equals("*")) {
          bh.consume(ctClass);
          System.out.println("Not reachable.");
        }
      }
    } catch (Exception e) {
      throw new IllegalStateException(current.getName(), e);
    }
  }

  @Override
  public void write(final Blackhole bh)
  throws Exception {
    final ClassPool cp = new ClassPool(true);
    cp.importPackage("java.util.logging");

    Resource current = null;
    try {
      for (final Resource resource : getResourceCollection()) {
        current = resource;
        final CtClass ctClass = cp.makeClass(resource.getInputStream());

        modifyClass(cp, ctClass);

        ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
        ctClass.toBytecode(new DataOutputStream(out));
      }
    } catch (Exception e) {
      throw new IllegalStateException(current.getName(), e);
    }
  }

  private void modifyClass(final ClassPool classPool,
                           final CtClass ctClass)
  throws NotFoundException, CannotCompileException {
    if (!ctClass.isInterface()) {
      ctClass.addInterface(classPool.get(LoggableInterface.class.getName()));
      
      try {
        ctClass.getDeclaredField("$LOG");
      } catch (NotFoundException e) {
        ctClass.addField(
          CtField.make("private static final " + Logger.class.getName() + " $LOG;", ctClass)
        );
      }

      final String clinitSrc =
        ctClass.getName() + ".$LOG = " + Logger.class.getName() + ".getLogger(\"" + ctClass.getName() + "\");";

      CtConstructor classInitializer = ctClass.getClassInitializer();
      if (classInitializer == null) {
        classInitializer = ctClass.makeClassInitializer();
      }
      classInitializer.insertAfter(clinitSrc);
      ctClass.addMethod(
        CtMethod.make(
          "public " + Logger.class.getName() + " log() { return $LOG; }", ctClass)
      );
    }
  }
}
