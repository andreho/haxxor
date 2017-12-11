package test;

import net.andreho.resources.Resource;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
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
import test.core.ModifyingClassVisitor;
import test.core.ReadingClassVisitor;

import java.util.concurrent.TimeUnit;

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
public class AsmCoreApiBenchmark
  extends AbstractBenchmark {

  public static void main(String[] args)
  throws Exception {
    loadResources();
    final long totalMemory = getTotalMemory();
    final Blackhole blackhole = createBlackhole();
    final AsmCoreApiBenchmark benchmark = new AsmCoreApiBenchmark();

    testRead(blackhole, benchmark);

    //testWrite(blackhole, benchmark);

    done(blackhole);
  }

  private static void testRead(final Blackhole blackhole,
                               final AsmCoreApiBenchmark benchmark)
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

  private static void testWrite(final Blackhole blackhole,
                                final AsmCoreApiBenchmark benchmark)
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
    Resource current = null;
    try {
      for (final Resource resource : getResourceCollection()) {
        current = resource;
        final ClassReader cr = new ClassReader(resource.toByteArray());
        final ReadingClassVisitor cv = new ReadingClassVisitor(bh);
        cr.accept(cv, 0);

        bh.consume(cv);
      }
    } catch (Exception e) {
      throw new IllegalStateException(current.getName(), e);
    }
  }

  @Override
  public void write(final Blackhole bh)
  throws Exception {

    Resource current = null;
    try {
      for (final Resource resource : getResourceCollection()) {
        current = resource;
        final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        final ClassReader cr = new ClassReader(resource.toByteArray());
        final ClassVisitor wcv = new ModifyingClassVisitor(cw);

        cr.accept(wcv, 0);
        if(bh != null) {
          bh.consume(cw.toByteArray());
        }
      }
    } catch (Exception e) {
      throw new IllegalStateException(current.getName(), e);
    }
  }

}
