package lin.louis.protocol;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

/**
 * Java will look automatically at the package "sun.net.www.protocol.lin".
 */
public class CustomProtocol {
    @Test
    public void usingLinProtocol() throws MalformedURLException {
        // You need to set the property "java.protocol.handler.pkgs" in order to tell the System to look at the package "lin.louis.protocol"
        System.setProperty("java.protocol.handler.pkgs", "lin.louis.protocol");
        URL url = new URL("lin:/foo/bar");
        assertThat(url.getFile()).isEqualTo("/foo/bar");
    }
}
