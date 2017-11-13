package examples;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.instr.misc.COMPOUND;
public class ToDegrees {
  public static HxInstruction createToDegreesCode() {
    COMPOUND compound = new COMPOUND();
    compound.asStream()
            .DLOAD(0)
            .LDC(180.0)
            .DMUL()
            .LDC(Math.PI)
            .DDIV()
            .DRETURN();
    return compound;
  }
}
