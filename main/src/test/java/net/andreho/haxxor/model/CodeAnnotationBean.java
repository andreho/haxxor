package net.andreho.haxxor.model;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <br/>Created by a.hofmann on 02.06.2017 at 13:45.
 */
public class CodeAnnotationBean {

  public void fooBar(List<@AnnotationC("1") String> a,
                     List<@AnnotationC("2") Collection<@AnnotationC("3") String>[]> b)
  throws @AnnotationC("E1") FileNotFoundException, @AnnotationC("E2") MalformedURLException {
    @AnnotationC("4")
    Map<@AnnotationC("5") String, @AnnotationC("6") List<@AnnotationC("7") Collection<@AnnotationC("8") String[]>>> m =
        new @AnnotationC("9") HashMap<>();

    try {
      if (m instanceof @AnnotationC("10") LinkedHashMap) {
        LinkedHashMap<String, List<Collection<String[]>>> lm =
            (@AnnotationC("11") LinkedHashMap<@AnnotationC("12") String,
                @AnnotationC("13") List<@AnnotationC("14") Collection<@AnnotationC("15") String[]>>>) m;
      }

    } catch (@AnnotationC("16") Exception e) {
      CodeAnnotationBean.<
          @AnnotationC("17") String,
          @AnnotationC("18") Integer,
          @AnnotationC("19") List<@AnnotationC("20") Map<@AnnotationC("21") int[], @AnnotationC("22") String[]>>>
          genericFooBar("", 1);
      e.printStackTrace();
    }
  }

  public static <@AnnotationC("23") A, @AnnotationC("24") B, @AnnotationC("25") C> C genericFooBar(A a, B b) {
    return null;
  }
}
