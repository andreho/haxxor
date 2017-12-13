package net.andreho.haxxor.spi.impl;

import difflib.PatchFailedException;
import net.andreho.haxxor.CodeDiffTools;
import net.andreho.haxxor.Debugger;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.model.AnnotationA;
import net.andreho.haxxor.model.AnnotationB;
import net.andreho.haxxor.model.AnnotationC;
import net.andreho.haxxor.model.ComplexBean;
import net.andreho.haxxor.model.EnumA;
import net.andreho.haxxor.model.GenericInterfaceA;
import net.andreho.haxxor.model.MinimalBean;
import net.andreho.haxxor.model.SpecificBean;
import net.andreho.haxxor.model.SupportedSpecBean;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 14.06.2017 at 02:32.
 */
class DefaultHxTypeSerializerTest {
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
  @MethodSource("testClasses")
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

    actual = CodeDiffTools.noCodeDiff(expected, actual);
    //System.out.println(actual);
  }
}