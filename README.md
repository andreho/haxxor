# Haxx0r
Project is currently under active development and 
represents an `experimental` implementation of 
a byte-code manipulation framework with an AOP-module.
The main idea is to evaluate some concepts around AOP and 
GC-free method-calls' interception.

Modules
 -
 - `agent` is the implementation for a Java-Agent
 - `aop` covers the AOP part of the project
 - `arguments` is experimental and needed for arguments wrapping and passing
 - `asm` is the embedded version of ASM - framework itself (v 5.0.2)
 - `dynclpath` allows to extend the classpath of running JVM at runtime
 - `func` is experimental and don't used at the moment
 - `magic` is the package with API to hidden Java functionality
 - `main` is the Haxx0r itself
 - `resources` is a tiny layer to manage available classes and resources of an ClassLoader   

More documentation comes a bit later.


