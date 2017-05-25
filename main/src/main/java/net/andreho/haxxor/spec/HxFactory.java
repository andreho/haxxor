package net.andreho.haxxor.spec;

/**
 * <br/>Created by a.hofmann on 08.04.2017 at 08:50.
 */
interface HxFactory {
   /**
    * @param type
    * @return
    */
   HxType createType(final String type);

   HxType createReference(final String type);

   HxField createField(final HxType owner, final String name, final String type);

   HxConstructor createConstructor(final HxType owner, final String... params);

   HxConstructor createConstructorReference(final HxType owner, final String... params);

   HxMethod createMethod(final HxType owner, final String name, final String returnType, final String... params);

   HxMethod createMethodReference(final HxType owner,
                                  final String name,
                                  final String returnType,
                                  final String... params);

   HxParameter createParameter(final HxParameterizable owner, int index);

   HxAnnotation createAnnotation(final String type, final boolean visible);
}
