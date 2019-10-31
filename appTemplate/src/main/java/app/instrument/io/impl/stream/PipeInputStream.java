package app.instrument.io.impl.stream;

import java.io.InputStream;

import app.instrument.io.utils.CircularBuffer;



/**
 * Pipe stream reading
 *
 * @author pbaioni
 */
public class PipeInputStream extends InputStream {

    private static final long TIMEOUT = 100;

    private CircularBuffer bytes;
    private final int timeout;

    public PipeInputStream(int pipeSize, int timeout) {
        super();
        bytes = new CircularBuffer(pipeSize);
        this.timeout = timeout;
    }

    @Override
    public int available() {
        CircularBuffer test = this.bytes;
        if (test != null) {
            return test.available();
        }
        return -1;
    }

    public boolean isClosed() {
        return bytes == null;
    }

    @Override
    public synchronized void close() {
        bytes = null;
        notifyAll();
    }

    @Override
    public synchronized int read(byte[] b, int off, int len) {
        int ret = -1;
        if (awaitData()) {
            ret = bytes.get(b, off, len);
        }
        return ret;
    }

    @Override
    public synchronized int read() {
        if (awaitData()) {
            return bytes.poolFirst();
        }
        return -1;
    }

    synchronized void addBytes(byte[] bytes, int offset, int length) {
        CircularBuffer buffer = this.bytes;
        if (bytes == null || buffer == null) {
            return;
        }
        long time = System.currentTimeMillis();
        int pos = addBytes(buffer, bytes, offset, length);
        while (pos < length && !isTimeout(time) && this.bytes != null) {
            pause();
            pos += addBytes(buffer, bytes, offset + pos, length - pos);
        }
    }

    private boolean awaitData() {
        long time = System.currentTimeMillis();
        while (!isTimeout(time) && bytes != null && bytes.available() == 0) {
            notifyAll();
            pause();
        }
        return !isClosed() && bytes.available() > 0;
    }

    private int addBytes(CircularBuffer buffer, byte[] bytes, int offset, int length) {
        int ret = buffer.addMax(bytes, offset, length);
        notifyAll();
        return ret;
    }

    private void pause() {
        try {
            if (timeout <= 0) {
                wait(TIMEOUT);
            } else {
                long time = Math.min(timeout, TIMEOUT);
                if (time > 0) {
                    wait(time);
                }
            }
        } catch (InterruptedException ex) {
        }
    }

    private boolean isTimeout(long time) {
        return timeout > 0 && (System.currentTimeMillis() - time > timeout);
    }
}
