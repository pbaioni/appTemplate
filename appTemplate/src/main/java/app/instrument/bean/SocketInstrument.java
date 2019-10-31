package app.instrument.bean;

import app.instrument.AbstractInstrument;
import app.instrument.driver.impl.socketinstrument.ISocketInstrument;
import app.instrument.driver.impl.socketinstrument.SocketInstrumentDriver;

/**
 * Socket instrument bean
 *
 * @author pbaioni
 */
public class SocketInstrument extends AbstractInstrument implements ISocketInstrument{

	public SocketInstrument() {
		this("DefaultSocketInstrument", "SocketInstrumentStub", "127.0.0.1:8080", 5000);

	}

	public SocketInstrument(String name, String model, String address, int timeout) {
		super(address, timeout, model);
		this.setName(name);
	}

	@Override
	public String echo(String msg) {
		return ((SocketInstrumentDriver) getDriver()).echo(msg);
	}

}
