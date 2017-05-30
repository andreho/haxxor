package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.spec.HxAnnotated;
import net.andreho.haxxor.spec.HxAnnotation;
import net.andreho.haxxor.spec.HxMember;
import net.andreho.haxxor.spec.HxOwned;
import net.andreho.haxxor.spec.HxType;

import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxAnnotatedImpl<A extends HxAnnotated<A> & HxMember<A> & HxOwned<A>> extends HxMemberImpl<A> implements HxAnnotated<A> {
   private static final Class<Repeatable> REPEATABLE_ANNOTATION_CLASS = Repeatable.class;
   private static final Collection<HxAnnotation> DEFAULT_ANNOTATION_COLL = Collections.emptySet();
   private static final Collection<HxAnnotated> DEFAULT_SUPER_ANNOTATED_COLL = Collections.emptySet();

   protected Collection<HxAnnotation> annotations = DEFAULT_ANNOTATION_COLL;

   public HxAnnotatedImpl() {
      super();
   }

   @Override
   public A setAnnotations(Collection<HxAnnotation> annotations) {
      if (this.annotations == DEFAULT_ANNOTATION_COLL) {
         this.annotations = new LinkedHashSet<>();
      }

      this.annotations.clear();
      this.annotations.addAll(annotations);

      return (A) this;
   }

   @Override
   public Collection<HxAnnotated> getSuperAnnotated() {
      return DEFAULT_SUPER_ANNOTATED_COLL;
   }

   @Override
   public Collection<HxAnnotation> getAnnotations() {
      return this.annotations;
   }

   @Override
   public Collection<HxAnnotation> annotations(final Predicate<HxAnnotation> predicate, boolean recursive) {
      final List<HxAnnotation> annotationList = new ArrayList<>(this.annotations.size());

      for (HxAnnotation annotation : getAnnotations()) {
         if (predicate.test(annotation)) {
            annotationList.add(annotation);
         }
      }

      if (recursive) {
         Collection<HxAnnotated> superAnnotated = getSuperAnnotated();

         for (HxAnnotated annotated : superAnnotated) {
            Collection<HxAnnotation> annotations = annotated.annotations(predicate, recursive);
            annotationList.addAll(annotations);
         }
      }

      return annotationList;
   }

   @Override
   public Collection<HxAnnotation> getAnnotationsByType(String type) {
      HxType repeatableAnnotationType = null;
      HxType repeatableType = null;

      for (HxAnnotation annotation : getAnnotations()) {
         repeatableAnnotationType = repeatableAnnotationType != null ?
                                    repeatableAnnotationType :
                                    annotation.getType()
                                              .getHaxxor()
                                              .resolve(type);

         HxAnnotation hxAnnotation = repeatableAnnotationType.getAnnotation(REPEATABLE_ANNOTATION_CLASS);

         if (hxAnnotation != null) {
            repeatableType = hxAnnotation.attribute("value");
            break;
         }
      }

      if (repeatableType != null) {
         final HxAnnotation value = getAnnotation(repeatableType);

         if (value != null) {
            HxAnnotation[] annotations = value.attribute("value");
            return new ArrayList<>(Arrays.asList(annotations));
         }
      } else {
         final HxAnnotation annotation = getAnnotation(type);

         if (annotation != null) {
            return new ArrayList<>(Arrays.asList(annotation));
         }
      }

      return Collections.emptySet();
   }
}
