package app.instrument.bean;

import java.io.IOException;

import app.instrument.Instrument;
import app.instrument.driver.impl.clientinstrument.IClientInstrument;
import app.instrument.service.InstrumentDefinition;

/**
 * Socket instrument bean
 *
 * @author pbaioni
 */
public class ClientInstrument extends Instrument implements IClientInstrument{

	public ClientInstrument() {
		this(new InstrumentDefinition("DefaultSocketInstrument", "SocketInstrumentStub", "127.0.0.1:8080", 5000));

	}

	public ClientInstrument(InstrumentDefinition def) {
		super(def.getAddress(), def.getTimeout(), def.getModel());
		this.setName(def.getName());
	}

	@Override
	public String echo(String msg) throws IOException {

			return sendAndRead("echoing " + msg);
	}

}
