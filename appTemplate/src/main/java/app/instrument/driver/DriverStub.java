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
    
    public DriverStub() {
    	super();
	}
    
    public DriverStub(String address, int timeout) {
    	super(address, timeout);
	}
    
    @Override
    public final void connect() {
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
        return "Stub read";
    }

    @Override
    public void send(String cmd) throws IOException {
        if (!isConnected()) {
            throw new IOException("Not connected");
        }
    }

    @Override
    public final void checkConnectionStatus() throws IOException {
        //Do nothing
    }
}
