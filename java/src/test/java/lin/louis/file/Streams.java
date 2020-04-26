package lin.louis.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;


class Streams {
    @Test
    void usingByteStreams() throws IOException {
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
