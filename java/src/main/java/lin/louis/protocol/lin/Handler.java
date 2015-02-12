package lin.louis.protocol.lin;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * This name of the class MUST BE Handler.
 * Any other will not work!
 *
 * @author llin
 * @created 12/02/15 10:06
 */
public class Handler extends URLStreamHandler {
    /**
     * {@inheritDoc}
     */
    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        return new LinConnection(u);
    }
}
