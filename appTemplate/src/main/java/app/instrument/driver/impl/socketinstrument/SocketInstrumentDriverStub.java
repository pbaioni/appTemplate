package app.instrument.driver.impl.socketinstrument;

import app.instrument.driver.DriverStub;

/**
 *Socket Instrument driver stub
 *
 * @author pbaioni
 */
public class SocketInstrumentDriverStub extends DriverStub implements ISocketInstrument {

	public SocketInstrumentDriverStub() {
		
	}
	
	public SocketInstrumentDriverStub(String address, int timeout) {
		super(address, timeout);
	}
	
	//stub specific instrument methods here
	
	@Override
	public String echo(String msg) {

		return "echoing " + msg;
	}
}
