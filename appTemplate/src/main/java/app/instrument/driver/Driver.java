package app.instrument.driver;

import java.io.IOException;

/**
 * Driver implementation to communicate with equipment
 *
 */
public interface Driver {

    /**
     * Connects equipment
     *
     * @throws IOException if I/O error occurs
     */
    void connect() throws IOException;

    /**
     * Disconnects equipment
     *
     * @throws IOException if I/O error occurs
     */
    void disconnect() throws IOException;

    /**
     * Gets connection status
     *
     * @return true if connected
     */
    boolean isConnected();

    /**
     * Reads text from instrument
     *
     * @return Text read
     * @throws IOException If I/O error occurs
     */
    String read() throws IOException;

    /**
     * Sends a text
     *
     * @param cmd Text to send
     * @throws IOException If I/O error occurs
     */
    void send(String cmd) throws IOException;

}
