package app.instrument.bean;

import java.io.IOException;

import app.instrument.Instrument;
import app.instrument.driver.impl.socketinstrument.ISocketInstrument;
import app.instrument.service.InstrumentDefinition;

/**
 * Socket instrument bean
 *
 * @author pbaioni
 */
public class SocketInstrument extends Instrument implements ISocketInstrument{

	public SocketInstrument() {
		this(new InstrumentDefinition("DefaultSocketInstrument", "SocketInstrumentStub", "127.0.0.1:8080", 5000));

	}

	public SocketInstrument(InstrumentDefinition def) {
		super(def.getAddress(), def.getTimeout(), def.getModel());
		this.setName(def.getName());
	}

	@Override
	public String echo(String msg) throws IOException {
			send("echoing " + msg);
			return read();
	}

}
