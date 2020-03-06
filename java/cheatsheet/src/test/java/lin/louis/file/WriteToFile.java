package lin.louis.file;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class WriteToFile {
    @Test
    @Ignore
    public void writeToFile() throws IOException {
        Path path = Paths.get("/home/llin/tmp", "foobar.txt");
        List<String> content = new ArrayList<>();
        content.add("foo");
        content.add("bar");
        Files.write(path, content, Charset.defaultCharset(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        File file = new File("/home/llin/tmp/foobar.txt");
        assertThat(file.exists()).isTrue();
    }

    @Test
    @Ignore
    public void writeUsingFileOutputStream() throws IOException {
        try (OutputStream out = new FileOutputStream("/home/llin/tmp/foobar.txt")) {
            out.write("foobar".getBytes());
        }
    }
}
