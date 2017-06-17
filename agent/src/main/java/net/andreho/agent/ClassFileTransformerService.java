package net.andreho.agent;

import java.lang.instrument.ClassFileTransformer;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 20:01.
 */
public interface ClassFileTransformerService extends ClassFileTransformer, Comparable<ClassFileTransformerService> {

}
