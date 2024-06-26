package lin.louis.custom_protocol.lin;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * /!\ By default, Java will look for a class named exactly "Handler".
 */
public class Handler extends URLStreamHandler {

    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        return new LinConnection(u);
    }
}
