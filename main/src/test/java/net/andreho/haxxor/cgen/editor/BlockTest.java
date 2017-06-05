package net.andreho.haxxor.cgen.editor;

import net.andreho.haxxor.cgen.editor.vars.BooleanVar;
import net.andreho.haxxor.cgen.editor.vars.StringVar;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * <br/>Created by a.hofmann on 01.04.2017 at 19:35.
 */
class BlockTest {

   @Test
   @Disabled
   void testBooleanApi() {
      final Block µ = new Block(null);

      final BooleanVar a = µ.boolean_("a").as(false);
      final BooleanVar b = µ.boolean_("b").as(true);
      final BooleanVar c = µ.boolean_("c").as(false);

      final StringVar h = µ.string("h").as("Hallo ");
      final StringVar w = µ.string("w").as("World!");
      final StringVar r = h.invokeTyped("concat", w.load()).toVar(µ.string("r"));

      println(µ, r);
   }

   private void println(final Block µ, final StringVar r) {
      µ.static_(System.class).fields().getTyped("out").invokeVoid("println", r.load());
   }
}