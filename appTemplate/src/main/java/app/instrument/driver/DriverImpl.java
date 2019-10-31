package app.instrument.driver;

import java.io.IOException;

import app.instrument.io.AbstractIO;
import app.instrument.io.IOFactory;
import app.instrument.io.IOStub;

/**
 * Abstract driver using a AbstractIO for communicate
 *
 */
public abstract class DriverImpl implements Driver {

    private String address;
    
    private int timeout;
    
    private AbstractIO io;
    
    private String DELIMITER = "\n";
    
    public DriverImpl() {
    	io = new IOStub();
    }

    public DriverImpl(String address, int timeout) {
		this.address = address;
		this.timeout = timeout;
	}
    
    @Override
    public void connect() throws IOException {
        connect(IOFactory.createIO(address, timeout));
    }

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
    
	@Override
	public String read() throws IOException {
			return io.read(DELIMITER);
	}

	@Override
	public void send(String cmd) throws IOException {
		io.write(cmd + DELIMITER);
	}
	
	/* Getters and Setters */
	
    public void setDELIMITER(String delimiter) {
		DELIMITER = delimiter;
	}
    
	public String getAddress() {
		return address;
	}

	public int getTimeout() {
		return timeout;
	}

	public AbstractIO getIo() {
		return io;
	}

}
