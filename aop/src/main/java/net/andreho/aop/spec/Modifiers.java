package net.andreho.aop.spec;

/**
 * Created by a.hofmann on 20.05.2016.
 */
public interface Modifiers {
   int ANY = -1;

   int PUBLIC = 0x0001;
   // class, field, method
   int PRIVATE = 0x0002;
   // class, field, method
   int PROTECTED = 0x0004;
   // class, field, method
   int STATIC = 0x0008;
   // field, method
   int FINAL = 0x0010;
   // class, field, method, parameter
   int SUPER = 0x0020;
   // class
   int SYNCHRONIZED = 0x0020;
   // method
   int VOLATILE = 0x0040;
   // field
   int BRIDGE = 0x0040;
   // method
   int VARARGS = 0x0080;
   // method
   int TRANSIENT = 0x0080;
   // field
   int NATIVE = 0x0100;
   // method
   int INTERFACE = 0x0200;
   // class
   int ABSTRACT = 0x0400;
   // class, method
   int STRICT = 0x0800;
   // method
   int SYNTHETIC = 0x1000;
   // class, field, method, parameter
   int ANNOTATION = 0x2000;
   // class
   int ENUM = 0x4000;
   // field inner
   int MANDATED = 0x8000; // parameter
}
