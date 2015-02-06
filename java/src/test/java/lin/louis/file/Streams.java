package lin.louis.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * @author Oodrive
 * @author llin
 * @created 06/02/15 10:37
 */
public class Streams {
    @Test
    public void testByteOutpustream() throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            outputStream.write("foobar".getBytes());

            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            int c;
            while ((c = inputStream.read()) != -1) {
                System.out.println(Character.toUpperCase((char) c));
            }
            inputStream.reset();
        }
    }
}
