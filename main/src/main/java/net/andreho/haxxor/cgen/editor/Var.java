package net.andreho.haxxor.cgen.editor;

import net.andreho.haxxor.spec.HxType;

/**
 * <br/>Created by a.hofmann on 28.03.2017 at 22:38.
 */
public abstract class Var<TYPE, VAR extends Var<TYPE, VAR, VAL>, VAL extends Val<TYPE, VAR, VAL>> {
   protected final Block block;
   protected final HxType type;
   protected final int index;
   protected final String name;

   protected Var(final Block block, final HxType type, final String name, final int index) {
      this.block = block;
      this.name = name;
      this.index = index;
      this.type = type;

      if (!block.var(this)) {
         throw new IllegalStateException("Already present: " + this);
      }
   }

   public int getIndex() {
      return index;
   }

   public String getName() {
      return name;
   }

   public Block getBlock() {
      return block;
   }

   public HxType getType() {
      return type;
   }

   public VAL load() {
      return null;
   }
}
