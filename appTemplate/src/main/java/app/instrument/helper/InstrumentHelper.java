package app.instrument.helper;

import app.instrument.Instrument;
import app.instrument.bean.SocketInstrument;
import app.instrument.service.InstrumentDefinition;

/**
 * Creates driver from model
 *
 * @author pbaioni
 */
public class InstrumentHelper {

	private static final String SOCKET_INSTRUMENT = "SocketInstrument";
	private static final String SIMULATED_SOCKET_INSTRUMENT = "SimulatedSocketInstrument";

	public static Instrument getBeanForModel(InstrumentDefinition definition) {

		switch(definition.getModel()) {
		case SOCKET_INSTRUMENT:
			return new SocketInstrument(definition);
		case SIMULATED_SOCKET_INSTRUMENT:
			return null;
		default:
			return null;
		}

	}

}
