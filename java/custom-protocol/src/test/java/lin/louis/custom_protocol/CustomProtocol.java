package lin.louis.custom_protocol;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.junit.jupiter.api.Test;


/**
 * Java will look automatically at the package "sun.net.www.protocol.lin".
 */
class CustomProtocol {

    @Test
    void usingLinProtocol() throws MalformedURLException {
        // You need to set the property "java.protocol.handler.pkgs" in order to tell the System to look at the package "lin.louis.custom_protocol"
        System.setProperty("java.protocol.handler.pkgs", "lin.louis.custom_protocol");

        URL url = URI.create("lin:/foo/bar").toURL();

        assertThat(url.getFile()).isEqualTo("/foo/bar");
    }
}
