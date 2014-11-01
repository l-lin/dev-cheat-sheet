package lin.louis.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oodrive
 * @author llin
 * @created 27/05/14 17:43
 */
public class ReadFileInClasspath {
    @Test
    public void readFile() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("file/foo.txt");
        assertThat(inputStream).isNotNull();
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer, "UTF-8");

        assertThat(writer.toString()).isEqualTo("foobar");

        readFileFromStatic();
    }

    public static void readFileFromStatic() throws IOException {
        InputStream inputStream = ReadFileInClasspath.class.getClassLoader().getResourceAsStream("file/foo.txt");
        assertThat(inputStream).isNotNull();
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer, "UTF-8");

        assertThat(writer.toString()).isEqualTo("foobar");
    }
}
