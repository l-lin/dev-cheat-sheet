package lin.louis.data_structures.array;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConcatenateByteArrays {

    @Test
    void concatenateByteArrays() throws IOException {
        byte[] a = new byte[2018];
        byte[] b = new byte[2018];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(a);
        byteArrayOutputStream.write(b);
        byte[] c = byteArrayOutputStream.toByteArray();

        Assertions.assertEquals(4036, c.length);
    }
}
