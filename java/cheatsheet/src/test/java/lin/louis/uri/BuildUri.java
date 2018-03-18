package lin.louis.uri;

import java.net.URI;

import org.junit.Test;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oodrive
 * @author llin
 * @created 25/07/14 09:56
 */
public class BuildUri {
    @Test
    public void buildUriWithSpring() {
        String user = "foobar";
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/foobar")
                .path("/api/foo")
                .queryParam("user", user)
                .build().toUri();
        assertThat(uri.toString()).isEqualTo("http://localhost:8080/foobar/api/foo?user=foobar");
    }
}
