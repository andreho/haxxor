package net.andreho.haxxor.spec;

import net.andreho.asm.org.objectweb.asm.Opcodes;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxConstructor extends HxParameterizable<HxConstructor> {
   enum Modifiers implements HxModifier {
      PUBLIC(0x0001),
      // class, field, method
      PRIVATE(0x0002),
      // class, field, method
      PROTECTED(0x0004),
      // class, field, method
      FINAL(0x0010),
      // class, field, method, parameter
      SYNCHRONIZED(0x0020),
      // method
      BRIDGE(0x0040),
      // method
      VARARGS(0x0080),
      // method
      STRICT(0x0800),
      // method
      SYNTHETIC(0x1000); // class, field, method, parameter

      final int bit;

      Modifiers(int bit) {
         this.bit = bit;
      }

      public static Set<Modifiers> toModifiers(int modifiers) {
         final Set<Modifiers> modifierSet = EnumSet.noneOf(Modifiers.class);

         if ((modifiers & Opcodes.ACC_PUBLIC) != 0) {
            modifierSet.add(PUBLIC);
         } else if ((modifiers & Opcodes.ACC_PROTECTED) != 0) {
            modifierSet.add(PROTECTED);
         } else if ((modifiers & Opcodes.ACC_PRIVATE) != 0) {
            modifierSet.add(PRIVATE);
         }

         if ((modifiers & Opcodes.ACC_FINAL) != 0) {
            modifierSet.add(FINAL);
         }
         if ((modifiers & Opcodes.ACC_VARARGS) != 0) {
            modifierSet.add(VARARGS);
         }
         if ((modifiers & Opcodes.ACC_BRIDGE) != 0) {
            modifierSet.add(BRIDGE);
         }
         if ((modifiers & Opcodes.ACC_SYNTHETIC) != 0) {
            modifierSet.add(SYNTHETIC);
         }
         if ((modifiers & Opcodes.ACC_STRICT) != 0) {
            modifierSet.add(STRICT);
         }

         return modifierSet;
      }

      @Override
      public int toBit() {
         return bit;
      }
   }
}
