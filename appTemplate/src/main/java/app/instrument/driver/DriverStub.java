package app.instrument.driver;

import java.io.IOException;

import org.apache.commons.logging.Log;

/**
 * Default stub for telecom driver
 *
 * @author TAS
 */
public class DriverStub extends AbstractDriver implements Driver {

    private boolean connected;

    @Override
    public final void connect(String address, int timeout) {
        connected = true;
    }

    @Override
    public final void disconnect() {
        connected = false;
    }

    @Override
    public final boolean isConnected() {
        return connected;
    }

    @Override
    public String read() throws IOException {
        if (!isConnected()) {
            throw new IOException("Not connected");
        }
        return "";
    }

    @Override
    public void send(String cmd) throws IOException {
        if (!isConnected()) {
            throw new IOException("Not connected");
        }
    }

    @Override
    public final void checkStatus() throws IOException {
        //Do nothing
    }
}
