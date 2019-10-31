package app.instrument.bean;

import app.instrument.AbstractInstrument;

/**
 * Socket instrument bean
 *
 * @author pbaioni
 */
public class SocketInstrument extends AbstractInstrument {

	public SocketInstrument() {
		this("DefaultSocketInstrument", "SocketInstrumentStub", "127.0.0.1:8080", 5000, "password");

	}

	public SocketInstrument(String name, String model, String address, int timeout, String password) {
		super(address, timeout, model);
		this.setName(name);
	}

	@Override
	public boolean connect() {
		return false;
	}

	@Override
	public void disconnect() {

	}

}
