package app.instrument.io.impl;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import app.instrument.io.AbstractIO;
import app.instrument.io.impl.stream.PipeInputStream;
import app.instrument.io.impl.stream.PipeOutputStream;

/**
 * IO croisé pour lire ce qui a été envoyé depuis getOutputStream() et envoyer
 * les données qui seront par getInputStream()
 *
 */
public class PipeIO extends AbstractIO {

    /**
     * Flux d'écriture de la connexion
     */
    private final transient PipeOutputStream output;
    /**
     * Flux de l'écrivain (croisé)
     */
    private final transient PipeOutputStream writer;

    public PipeIO() throws IOException {
        this(2048, 0);
    }

    public PipeIO(int pipeSize, int timeout) throws IOException {
        writer = new PipeOutputStream(new PipeInputStream(pipeSize, timeout));
        output = new PipeOutputStream(new PipeInputStream(pipeSize, timeout));
    }

    /**
     * Output stream pour envoyer des données qui seront depuis getInputStream()
     *
     * @return Output stream
     * @see #getInputStream()
     */
    public OutputStream getWriter() {
        return writer;
    }

    /**
     * Input Stream pour lire les données écrites depuis getOutputStream()
     *
     * @return Input stream
     * @see #getOutputStream()
     */
    public InputStream getReader() {
        return output.getInputStream();
    }

    /**
     * Reads a message from getOutputStream
     *
     * @return message read
     * @throws IOException if an I/O error occurs.
     */
    public String readOutput() throws IOException {
        int length = getReader().available();
        byte[] ret = new byte[length];
        length = getReader().read(ret);
        return new String(ret, 0, length, "UTF-8");
    }

    /**
     * Sends a message to getInputStream
     *
     * @param message Message to send
     * @throws IOException if an I/O error occurs.
     */
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
