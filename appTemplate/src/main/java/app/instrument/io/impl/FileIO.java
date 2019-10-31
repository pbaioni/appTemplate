package app.instrument.io.impl;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import app.instrument.io.AbstractIO;

/**
 * File reader
 *
 * @author TAS
 */
public class FileIO extends AbstractIO {

    private final InputStream in;
    private final OutputStream out;
    private boolean open;

    public FileIO(URI uri, int timeout) throws IOException {
        this(uri.getPath());
    }

    public FileIO(String filepath) throws IOException {
        in = new BufferedInputStream(new FileInputStream(filepath));
        out = new BufferedOutputStream(new FileOutputStream(filepath + "out"));
        open = true;
    }

    @Override
    public void close() throws IOException {
        open = false;
        in.close();
    }

    @Override
    public InputStream getInputStream() {
        return in;
    }

    @Override
    public OutputStream getOutputStream() {
        return out;
    }

    @Override
    public boolean isConnected() {
        return open;
    }
}
