package net.andreho.haxxor.cgen.editor;

import net.andreho.haxxor.cgen.editor.vars.BooleanVar;
import net.andreho.haxxor.cgen.editor.vars.ByteVar;
import net.andreho.haxxor.cgen.editor.vars.CharVar;
import net.andreho.haxxor.cgen.editor.vars.DoubleVar;
import net.andreho.haxxor.cgen.editor.vars.FloatVar;
import net.andreho.haxxor.cgen.editor.vars.IntVar;
import net.andreho.haxxor.cgen.editor.vars.LongVar;
import net.andreho.haxxor.cgen.editor.vars.ShortVar;
import net.andreho.haxxor.cgen.editor.vars.StringVar;
import net.andreho.haxxor.cgen.editor.vars.TypedVar;
import net.andreho.haxxor.spec.api.HxType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <br/>Created by a.hofmann on 01.04.2017 at 17:22.
 */
public class Block {

  private Block parent;
  private final Editor editor;
  private final Map<String, Var<?, ?, ?>> vars;
  private int locals;

  public Block(final Editor editor) {
    this(editor, null, 0);
  }

  public Block(final Editor editor, final int locals) {
    this(editor, null, locals);
  }

  public Block(final Editor editor, final Block parent, final int locals) {
    this.editor = editor;
    this.vars = new LinkedHashMap<>();
    this.parent = parent;

    if (parent != null) {
      this.locals = parent.locals;
    } else {
      this.locals = locals;
    }
  }

  /**
   * @param typeName
   * @return
   */
  public HxType type(String typeName) {
    return editor.getHaxxor()
                 .reference(typeName);
  }

  /**
   * @param name
   * @return
   */
  public boolean hasVar(String name) {
    if (vars.containsKey(name)) {
      return true;
    }
    if (parent != null) {
      return parent.hasVar(name);
    }
    return false;
  }

  /**
   * @param name
   * @param <T>
   * @param <V>
   * @param <S>
   * @return
   */
  public <T, V extends Var<T, V, S>, S extends Val<T, V, S>> Var<T, V, S> var(String name) {
    final Var<T, V, S> var = (Var<T, V, S>) vars.get(name);
    if (var != null) {
      return var;
    }
    if (parent != null) {
      return parent.var(name);
    }
    throw new IllegalStateException("Unresolvable variable: " + name);
  }

  public Static static_(final HxType type) {
    return null;
  }

  public Static static_(final String name) {
    return static_(type(name));
  }

  public Static static_(final Class<?> type) {
    return static_(type(type.getName()));
  }

  public BooleanVar boolean_(String name) {
    return new BooleanVar(this, name, reserveLocalSpace());
  }

  public ByteVar byte_(String name) {
    return new ByteVar(this, name, reserveLocalSpace());
  }

  public CharVar char_(String name) {
    return new CharVar(this, name, reserveLocalSpace());
  }

  public ShortVar short_(String name) {
    return new ShortVar(this, name, reserveLocalSpace());
  }

  public IntVar int_(String name) {
    return new IntVar(this, name, reserveLocalSpace());
  }

  public FloatVar float_(String name) {
    return new FloatVar(this, name, reserveLocalSpace());
  }

  public LongVar long_(String name) {
    return new LongVar(this, name, reserveDoubleLocalSpace());
  }

  public DoubleVar double_(String name) {
    return new DoubleVar(this, name, reserveDoubleLocalSpace());
  }

  public StringVar string(String name) {
    return new StringVar(this, name, reserveLocalSpace());
  }

  public <T> TypedVar<T> typed(String name, String type) {
    return typed(name, type(type));
  }

  public <T> TypedVar<T> typed(String name, Class<T> type) {
    return typed(name, type(type.getName()));
  }

  public <T> TypedVar<T> typed(String name, HxType type) {
    return new TypedVar<>(this, type, name, reserveLocalSpace());
  }

  private int reserveLocalSpace() {
    return reserveLocals(1);
  }

  private int reserveDoubleLocalSpace() {
    return reserveLocals(2);
  }

  private int reserveLocals(int size) {
    if (size != 1 || size != 2) {
      throw new IllegalArgumentException("Invalid size: " + size);
    }
    int locals = this.locals;
    this.locals = locals + size;
    return locals;
  }

  <T, V extends Var<T, V, S>, S extends Val<T, V, S>> boolean var(final Var<T, V, S> var) {
    if (hasVar(var.getName())) {
      return false;
    }
    return null == vars.putIfAbsent(var.getName(), var);
  }

}
