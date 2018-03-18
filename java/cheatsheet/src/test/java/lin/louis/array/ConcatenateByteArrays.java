package lin.louis.array;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

/**
 * @author Oodrive
 * @author llin
 * @created 24/09/15 16:54
 */
public class ConcatenateByteArrays {
    @Test
    public void concatenateByteArrays() throws IOException {
        byte[] a = new byte[2018];
        byte[] b = new byte[2018];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(a);
        byteArrayOutputStream.write(b);
        byte[] c = byteArrayOutputStream.toByteArray();
    }
}
