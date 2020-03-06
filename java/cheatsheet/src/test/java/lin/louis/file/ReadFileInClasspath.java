package lin.louis.file;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.junit.Test;

public class ReadFileInClasspath {
    @Test
    public void readFile() throws IOException {
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("file/foo.txt")) {
            readFile(in);
        }

        readFileFromStatic();
    }

    private static void readFileFromStatic() throws IOException {
        try (InputStream in = ReadFileInClasspath.class.getClassLoader().getResourceAsStream("file/foo.txt")) {
            readFile(in);
        }
    }

    private static void readFile(InputStream in) throws IOException {
        assertThat(in).isNotNull();
        try (InputStreamReader inReader = new InputStreamReader(in); BufferedReader br = new BufferedReader(inReader)) {
            String result = br.lines()
                    .parallel()
                    .collect(Collectors.joining("\n"));
            assertThat(result).isEqualTo("foobar");
        }
    }
}
