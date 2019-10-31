package app.instrument.driver;

import java.io.IOException;

import app.instrument.io.AbstractIO;
import app.instrument.io.IOFactory;
import app.instrument.io.IOStub;

/**
 * Abstract driver using a AbstractIO for communicate
 *
 */
public abstract class AbstractDriver implements Driver {

    private String address;
    
    private int timeout;
    
    private AbstractIO io;
    
    public AbstractDriver() {
    	io = new IOStub();
    }

    public AbstractDriver(String address, int timeout) {
		this.address = address;
		this.timeout = timeout;
	}
    @Override
    public void connect() throws IOException {
        connect(IOFactory.createIO(address, timeout));
    }

    /**
     * Connects equipment
     *
     * @param io AbstractIO to use
     * @throws IOException if I/O error occurs
     */
    public void connect(AbstractIO io) throws IOException {
        if (isConnected()) {
            disconnect();
        }
        this.io = io;
    }

    @Override
    public void disconnect() throws IOException {
        if (io != null) {
            io.close();
        }
    }

    @Override
    public boolean isConnected() {
        return io != null && io.isConnected();
    }

    /**
     * Gets IO communicator
     *
     * @return IO communicator
     */
    protected AbstractIO getIO() {
        return io;
    }
}
