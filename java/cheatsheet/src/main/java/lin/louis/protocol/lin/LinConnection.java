package lin.louis.protocol.lin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * This will define a new protocol: the "lin:" protocol!!!
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

    @Override
    public Object getContent() {
        System.out.println("getContent()");
        return null;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        System.out.println("getInputStream()");
        return this.getClass().getClassLoader().getResourceAsStream(this.getURL().getFile());
    }
}
