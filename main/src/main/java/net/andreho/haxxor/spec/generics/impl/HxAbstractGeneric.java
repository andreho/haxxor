package net.andreho.haxxor.spec.generics.impl;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.asm.org.objectweb.asm.signature.SignatureVisitor;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxGeneric;
import net.andreho.haxxor.spec.impl.HxAnnotatedImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <br/>Created by andreho on 3/26/16 at 7:17 PM.<br/>
 */
public abstract class HxAbstractGeneric
    extends HxAnnotatedImpl {

  private static void parseType(final Haxxor haxxor, final String signature, final SignatureVisitor v) {
    int len = signature.length();
    int pos;
    char c;

    if (signature.charAt(0) == '<') {
      pos = 2;
      do {
        int end = signature.indexOf(':', pos);
        v.visitFormalTypeParameter(signature.substring(pos - 1, end));
        pos = end + 1;

        c = signature.charAt(pos);

        if (c == 'L' || c == '[' || c == 'T') {
          pos = parseType(haxxor, signature, pos, v.visitClassBound(), null);
        }

        while ((c = signature.charAt(pos++)) == ':') {
          pos = parseType(haxxor, signature, pos, v.visitInterfaceBound(), null);
        }
      }
      while (c != '>');
    } else {
      pos = 0;
    }

    if (signature.charAt(pos) == '(') {
      pos++;
      while (signature.charAt(pos) != ')') {
        pos = parseType(haxxor, signature, pos, v.visitParameterType(), null);
      }
      pos = parseType(haxxor, signature, pos + 1, v.visitReturnType(), null);
      while (pos < len) {
        pos = parseType(haxxor, signature, pos + 1, v.visitExceptionType(), null);
      }
    } else {
      pos = parseType(haxxor, signature, pos, v.visitSuperclass(), null);
      while (pos < len) {
        pos = parseType(haxxor, signature, pos, v.visitInterface(), null);
      }
    }
  }

  private static int parseType(final Haxxor haxxor, final String signature, int pos, final SignatureVisitor v,
                               HxGeneric target) {
    char c;
    int start, end;
    boolean visited, inner;
    String name;

    switch (c = signature.charAt(pos++)) {
      case 'Z':
      case 'C':
      case 'B':
      case 'S':
      case 'I':
      case 'F':
      case 'J':
      case 'D':
      case 'V':
        v.visitBaseType(c);
        return pos;

      case '[':
        return parseType(haxxor, signature, pos, v.visitArrayType(), target);

      case 'T':
        end = signature.indexOf(';', pos);
        v.visitTypeVariable(signature.substring(pos, end));
        return end + 1;

      default: // case 'L':
        start = pos;
        visited = false;
        inner = false;
        for (; ; ) {
          switch (c = signature.charAt(pos++)) {
            case '.':
            case ';':
              if (!visited) {
                name = signature.substring(start, pos - 1);
                if (inner) {
                  v.visitInnerClassType(name);
                } else {
                  v.visitClassType(name);
                }
              }
              if (c == ';') {
                v.visitEnd();
                return pos;
              }
              start = pos;
              visited = false;
              inner = true;
              break;

            case '<':
              name = signature.substring(start, pos - 1);
              if (inner) {
                v.visitInnerClassType(name);
              } else {
                v.visitClassType(name);
              }
              visited = true;
              top:
              for (; ; ) {
                switch (c = signature.charAt(pos)) {
                  case '>':
                    break top;
                  case '*':
                    ++pos;
                    v.visitTypeArgument();
                    break;
                  case '+': //extends
                  case '-': //super
                    pos = parseType(haxxor, signature, pos + 1, v.visitTypeArgument(c), target);
                    break;
                  default:
                    pos = parseType(haxxor, signature, pos, v.visitTypeArgument('='), target);
                    break;
                }
              }
          }
        }
    }
  }

  public static List<HxGeneric> retrieveFormalParameters(Haxxor haxxor, String genericSignature) {
    //<B::Ljava/io/Serializable;>Ljava/lang/Object;
    //<T:Ljava/lang/Number;A:Ljava/lang/Object;B:Ljava/lang/Object;>Ljava/util/AbstractCollection<TT;>;
    // Ljava/lang/Comparable<Lnet/andreho/haxxor/objects/Node<+TT;TA;TB;>;>;Ljava/io/Serializable;
    // Ljava/util/concurrent/Callable<Ljava/util/concurrent/Callable<-TT;>;>;
    if (genericSignature.startsWith("<")) {
      final List<HxGeneric> fp = new ArrayList<>(1);
      parseType(haxxor, genericSignature, new SignatureVisitor(Opcodes.ASM5) {
      });
    }

    return Collections.emptyList();
  }

  public abstract HxGeneric attach(HxGeneric generic);
}
