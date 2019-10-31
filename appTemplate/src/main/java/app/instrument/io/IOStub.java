package app.instrument.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author pbaioni
 */
public class IOStub extends AbstractIO {

    @Override
    public void close() throws IOException {
        throw new IllegalStateException("Input/Output is not available.");
    }

    @Override
    public InputStream getInputStream() {
        throw new IllegalStateException("Input/Output is not available.");
    }

    @Override
    public OutputStream getOutputStream() {
        throw new IllegalStateException("Input/Output is not available.");
    }

    @Override
    public boolean isConnected() {
        return false;
    }

}
