package app.instrument.driver.impl.socketinstrument;

import app.instrument.driver.DriverImpl;


/**
 * Socket Instrument driver implementation
 *
 * @author pbaioni
 */
public class SocketInstrumentDriver extends DriverImpl implements ISocketInstrument {


    private final static String DELIMITER = "\n";
    
    public SocketInstrumentDriver() {
    }
    
    public SocketInstrumentDriver(String address, int timeout) {
    	super(address, timeout);
    }

	@Override
	public String echo(String msg) {

		return "echoing " + msg;
	}

}
