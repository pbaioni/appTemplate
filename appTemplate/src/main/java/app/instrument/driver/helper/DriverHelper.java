package app.instrument.driver.helper;

import app.instrument.driver.AbstractDriver;
import app.instrument.driver.DriverStub;
import app.instrument.driver.impl.socketinstrument.SocketInstrumentDriverStub;
import app.instrument.driver.impl.socketinstrument.SocketInstrumentDriver;

public class DriverHelper {

	private static final String MITEQ = "Miteq";
	private static final String DC_STUB = "DcStub";

	public static AbstractDriver getDriverForModel(String model) {

		if (model.equals(MITEQ)) {
			return new SocketInstrumentDriver();
		} else if(model.equals(DC_STUB)){
			return new SocketInstrumentDriverStub();
		}
		return new DriverStub();
	}

}
