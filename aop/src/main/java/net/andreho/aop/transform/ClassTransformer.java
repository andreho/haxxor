package net.andreho.aop.transform;

import net.andreho.common.utils.OrderUtils;

import java.lang.instrument.ClassFileTransformer;

/**
 * <br/>Created by a.hofmann on 26.09.2015.<br/>
 */
public interface ClassTransformer extends ClassFileTransformer, Comparable<ClassTransformer> {
   /**
    * @return
    */
   default String getName() {
      return getClass().getName();
   }

   @Override
   default int compareTo(ClassTransformer other) {
      return OrderUtils.order(this, other);
   }
}
