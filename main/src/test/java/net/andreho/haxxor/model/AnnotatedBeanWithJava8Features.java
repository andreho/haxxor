package net.andreho.haxxor.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <br/>Created by a.hofmann on 07.06.2017 at 01:24.
 */
public class AnnotatedBeanWithJava8Features
    extends @AnnotationC("extends")
                GenericBean<
    @AnnotationC("extends-1") String,
    @AnnotationC("extends-2") List<@AnnotationC("extends-3")Integer>,
    @AnnotationC("extends-4") Map<@AnnotationC("extends-5") String, @AnnotationC("extends-6") List<@AnnotationC
        ("extends-7") Collection<@AnnotationC("extends-8") byte[]>>>>
    implements @AnnotationC("implements")
                  GenericInterfaceX<
    @AnnotationC("implements-1") Integer,
    @AnnotationC("implements-2") Set<@AnnotationC("implements-3") String>,
    @AnnotationC("implements-4") Class<@AnnotationC("implements-5")?>[]> {

}
