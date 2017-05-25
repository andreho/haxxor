package net.andreho.haxxor.cgen;

import net.andreho.haxxor.cgen.instr.BEGIN;
import net.andreho.haxxor.cgen.instr.END;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 19.03.2016.<br/>
 */
public class Code implements Iterable<Instruction> {
   //----------------------------------------------------------------------------------------------------------------
   private Instruction first;
   private Instruction last;

   private List<LocalVariable> localVariables = new ArrayList<>();
   private List<TryCatch> tryCatches = new ArrayList<>();

   private int maxStack = -1;
   private int maxLocals = -1;

   //----------------------------------------------------------------------------------------------------------------

   public Code() {
      this(new BEGIN(), new END());
   }

   protected Code(final Instruction first, final Instruction last) {
      setFirst(first);
      setLast(last);
      getFirst().append(getLast());
   }

   //----------------------------------------------------------------------------------------------------------------

   public Code append(Instruction inst) {
      getCurrent().append(inst);
      return this;
   }

   public Instruction getFirst() {
      return this.first;
   }

   //----------------------------------------------------------------------------------------------------------------

   protected void setFirst(final Instruction first) {
      this.first = first;
   }

   public Instruction getCurrent() {
      return this.last.getPrevious();
   }

   public Instruction getLast() {
      return this.last;
   }

   protected void setLast(final Instruction last) {
      this.last = last;
   }

   public List<LocalVariable> getLocalVariables() {
      return this.localVariables;
   }

   public List<TryCatch> getTryCatches() {
      return this.tryCatches;
   }

   public int getMaxStack() {
      return this.maxStack;
   }

   public void setMaxStack(final int maxStack) {
      this.maxStack = maxStack;
   }

   public int getMaxLocals() {
      return this.maxLocals;
   }

   public void setMaxLocals(final int maxLocals) {
      this.maxLocals = maxLocals;
   }

   @Override
   public Iterator<Instruction> iterator() {
      return getFirst().iterator();
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();

      for (TryCatch tryCatch : getTryCatches()) {
         builder.append(tryCatch).append('\n');
      }

      for (Instruction inst : getFirst()) {
         builder.append(inst.toString()).append('\n');
      }

      for (TryCatch tryCatch : getTryCatches()) {

      }

      return builder.toString();
   }
}
