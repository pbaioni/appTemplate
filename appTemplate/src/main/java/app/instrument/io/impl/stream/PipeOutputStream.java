package app.instrument.io.impl.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Pipe stream writing
 *
 * @author pbaioni
 */
public class PipeOutputStream extends OutputStream {

    private PipeInputStream is;

    public PipeOutputStream(PipeInputStream is) {
        super();
        this.is = is;
    }

    public InputStream getInputStream() {
        return is;
    }

    public int available() {
        PipeInputStream is = this.is;
        return (is == null) ? -1 : is.available();
    }

    @Override
    public void write(int b) throws IOException {
        write(new byte[]{(byte) b}, 0, 1);
    }

    @Override
    public synchronized void write(byte[] b, int off, int len) throws IOException {
        if (is == null || is.isClosed()) {
            throw new IOException("Pipe closed");
        }
        is.addBytes(b, off, len);
    }

    @Override
    public synchronized void close() throws IOException {
        if (is != null) {
            is.close();
        }
        is = null;
    }

    public boolean isClosed() {
        PipeInputStream is = this.is;
        return is == null || is.isClosed();
    }
}
