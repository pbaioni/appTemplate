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
     * @param address Address or URI
     * @param timeout Timeout (in ms) with the equipment
     * @throws IOException if I/O error occurs
     */
    void connect(String address, int timeout) throws IOException;

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

    /**
     * Checks the status/response from the instrument
     *
     * @throws IOException If I/O error occurs or the check failed
     */
    void checkStatus() throws IOException;
}
