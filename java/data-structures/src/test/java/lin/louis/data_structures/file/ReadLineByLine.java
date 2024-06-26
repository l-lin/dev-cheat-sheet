package lin.louis.data_structures.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;


class ReadLineByLine {
    @Test
    void readFile() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("file/foobar.txt");
        assertThat(inputStream).isNotNull();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
