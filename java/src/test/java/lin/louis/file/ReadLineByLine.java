package lin.louis.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadLineByLine {
    @Test
    public void readFile() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("file/foobar.txt");
        assertThat(inputStream).isNotNull();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } finally {
            IOUtils.closeQuietly(br);
        }
    }
}
