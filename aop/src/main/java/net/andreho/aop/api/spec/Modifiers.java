package net.andreho.aop.api.spec;

/**
 * Created by a.hofmann on 20.05.2016.
 */
public interface Modifiers {

  int ANY = -1;

  // class, field, method
  int PUBLIC = 0x0001;
  // class, field, method
  int PRIVATE = 0x0002;
  // class, field, method
  int PROTECTED = 0x0004;
  // field, method
  int STATIC = 0x0008;
  // class, field, method, parameter
  int FINAL = 0x0010;
  // class
  int SUPER = 0x0020;
  // method
  int SYNCHRONIZED = 0x0020;
  // field
  int VOLATILE = 0x0040;
  // method
  int BRIDGE = 0x0040;
  // method
  int VARARGS = 0x0080;
  // field
  int TRANSIENT = 0x0080;
  // method
  int NATIVE = 0x0100;
  // class
  int INTERFACE = 0x0200;
  // class, method
  int ABSTRACT = 0x0400;
  // method
  int STRICT = 0x0800;
  // class, field, method, parameter
  int SYNTHETIC = 0x1000;
  // class
  int ANNOTATION = 0x2000;
  // field inner
  int ENUM = 0x4000;
  // parameter
  int MANDATED = 0x8000;
}
