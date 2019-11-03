package app.instrument.driver;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.main.Application;

/**
 * Default stub for telecom driver
 *
 * @author TAS
 */
public class DriverStub extends DriverImpl implements Driver {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DriverStub.class);

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
    public String read() {
        if (!isConnected()) {
            LOGGER.error("Impossible to read from stub driver");
        }
        return "Stub read";
    }

    @Override
    public void send(String cmd) throws IOException {
        if (!isConnected()) {
            throw new IOException("Not connected");
        }
    }

}
