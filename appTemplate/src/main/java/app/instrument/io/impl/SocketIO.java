package app.instrument.io.impl;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;

import app.instrument.io.AbstractIO;

/**
 * TCP socket
 *
 * @author monpayt
 */
public class SocketIO extends AbstractIO {

    private final Socket socket;
    private final InputStream is;
    private final OutputStream os;

    public SocketIO(URI uri, int timeout) throws IOException {
        this(uri.getHost(), uri.getPort(), timeout);
    }

    public SocketIO(String address, int port, int timeout) throws IOException {
        socket = new Socket(address, port);
        socket.setSoTimeout(timeout);
        is = socket.getInputStream();
        os = socket.getOutputStream();
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void close() throws IOException {
        is.close();
        os.close();
        socket.close();
    }

    @Override
    public boolean isConnected() {
        return socket.isConnected() && !socket.isClosed();
    }

    @Override
    public InputStream getInputStream() {
        return is;
    }

    @Override
    public OutputStream getOutputStream() {
        return os;
    }
}
