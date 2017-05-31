package net.andreho.haxxor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <br/>Created by a.hofmann on 30.05.2017 at 14:35.
 */
public abstract class Utils {

  /**
   * Reads the content of given stream into a byte array
   * <b>ATTENTION:</b> input still must be closed by caller.
   *
   * @param inputStream
   * @return
   */
  public static byte[] toByteArray(InputStream inputStream)
  throws IOException {
    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    int count;
    final byte[] buffer = new byte[1028];
    while ((count = inputStream.read(buffer)) > -1) {
      outputStream.write(buffer, 0, count);
    }
    return outputStream.toByteArray();
  }

  private Utils() {
  }
}
