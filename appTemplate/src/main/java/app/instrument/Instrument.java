package app.instrument;

import app.instrument.driver.DriverImpl;
import app.instrument.helper.InstrumentHelper;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.instrument.driver.Driver;

/**
 * Abstract implementation to communicate with a specific equipment
 *
 *@author pbaioni
 */
public abstract class Instrument extends DriverImpl implements IInstrument{

	private static final Logger LOGGER = LoggerFactory.getLogger(Instrument.class);
	
    private String name;
    private String model;
    private InstrumentStatus status;
    
    public Instrument() {

    }
    /**
     * Constructor
     *
     * @param name Instrument name
     * @param status Default instrument status
     */
    public Instrument(String address, int timeout, String model) {
        super(address, timeout);
    	this.model = model;
        setStatus(InstrumentStatus.DISCONNECTED);
    }
    
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getModel() {
		return model;
	}

	@Override
    public  InstrumentStatus getStatus() {
        return status;
    }
	
	@Override
	public String read() {
		
		String response = super.read();
		LOGGER.info(this.name + " has received: " + response);
		return response;
	}

	@Override
	public void send(String cmd) throws IOException {
		LOGGER.info(this.name + " is sending >>> " + cmd);
		super.send(cmd);
	}
	
	public String sendAndRead(String cmd) {
		try {
			send(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return read();
	}

    public final void setName(String name) {
        this.name = name;
    }
    private void setStatus(InstrumentStatus status) {
        this.status = status;
    }    
	
	@Override
	public String toString() {
		return "AbstractInstrument [name=" + name + ", model=" + model + ", status=" + status + "]";
	}

}
