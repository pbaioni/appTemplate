package app.instrument.driver.helper;

import app.instrument.driver.DriverImpl;
import app.instrument.driver.DriverStub;
import app.instrument.driver.impl.socketinstrument.SocketInstrumentDriverStub;
import app.instrument.driver.impl.socketinstrument.SocketInstrumentDriver;

/**
 * Creates driver from model
 *
 * @author pbaioni
 */
public class DriverHelper {

	private static final String SOCKET_INSTRUMENT = "SocketInstrument";
	private static final String SOCKET_INSTRUMENT_STUB = "SocketInstrumentStub";

	public static DriverImpl getDriverForModel(String address, int timeout, String model) {

		switch(model) {
		case SOCKET_INSTRUMENT:
			return new SocketInstrumentDriver(address, timeout);
		case SOCKET_INSTRUMENT_STUB:
			return new SocketInstrumentDriverStub(address, timeout);
		default:
			return new DriverStub();
		}

	}

}
