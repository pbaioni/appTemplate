package app.instrument.driver.impl.clientinstrument;

import java.io.IOException;

import app.instrument.driver.Driver;


/**
 * Generic Client instrument driver interface
 *
 * @author pbaioni
 */
public interface IClientInstrument extends Driver {

	//Add specific instrument methods
	
	public String echo(String msg) throws IOException;

}
