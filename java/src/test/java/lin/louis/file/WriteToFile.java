package lin.louis.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oodrive
 * @author llin
 * @created 07/07/14 16:07
 */
public class WriteToFile {
    @Test
    public void writeToFile() throws IOException {
        Path path = Paths.get("/home/llin/tmp", "foobar.txt");
        List<String> content = new ArrayList<>();
        content.add("foo");
        content.add("bar");
        Files.write(path, content, Charset.defaultCharset());

        File file = new File("/home/llin/tmp/foobar.txt");
        assertThat(file.exists()).isTrue();
    }
}
