package app.instrument.io.impl;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import app.instrument.io.AbstractIO;
import app.instrument.io.impl.stream.PipeInputStream;
import app.instrument.io.impl.stream.PipeOutputStream;

/**
 * Reads the output stream and sends in the InputStream
 *
 *@author pbaioni
 */
public class PipeIO extends AbstractIO {

    private final transient PipeOutputStream output;

    private final transient PipeOutputStream writer;

    public PipeIO() throws IOException {
        this(2048, 0);
    }

    public PipeIO(int pipeSize, int timeout) throws IOException {
        writer = new PipeOutputStream(new PipeInputStream(pipeSize, timeout));
        output = new PipeOutputStream(new PipeInputStream(pipeSize, timeout));
    }

    public OutputStream getWriter() {
        return writer;
    }


    public InputStream getReader() {
        return output.getInputStream();
    }

    public String readOutput() throws IOException {
        int length = getReader().available();
        byte[] ret = new byte[length];
        length = getReader().read(ret);
        return new String(ret, 0, length, "UTF-8");
    }

    public void sendInput(String message) throws IOException {
        byte[] bytes = message.getBytes();
        getWriter().write(bytes, 0, bytes.length);
        getWriter().flush();
    }

    @Override
    public InputStream getInputStream() {
        return writer.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() {
        return output;
    }

    @Override
    public boolean isConnected() {
        return !output.isClosed();
    }

    @Override
    public void close() throws IOException {
        output.close();
        writer.close();
    }
}
