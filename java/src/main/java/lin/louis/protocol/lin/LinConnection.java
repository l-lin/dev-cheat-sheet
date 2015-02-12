package lin.louis.protocol.lin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * This will define a new protocol: the "lin:" protocol!!!
 *
 * @author llin
 * @created 12/02/15 10:06
 */
public class LinConnection extends URLConnection {
    /**
     * Constructs a URL connection to the specified URL. A connection to
     * the object referenced by the URL is not created.
     *
     * @param url the specified URL.
     */
    protected LinConnection(URL url) {
        super(url);
    }

    @Override
    public void connect() throws IOException {
        System.out.println("connect()");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getContent() {
        System.out.println("getContent()");
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getInputStream() throws IOException {
        System.out.println("getInputStream()");
        return this.getClass().getClassLoader().getResourceAsStream(this.getURL().getFile());
    }
}
