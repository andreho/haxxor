package test;
import java.util.logging.Logger;

public interface LoggableInterface {
  Logger log();
}
class LoggableCodeFragment implements LoggableInterface {
  private static final Logger $LOG;

  static {
    $LOG = Logger.getLogger("<Name_der_Zielklasse>");
  }

  @Override
  public Logger log() {
    return $LOG;
  }
}
