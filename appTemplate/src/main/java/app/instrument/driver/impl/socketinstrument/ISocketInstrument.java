package app.instrument.driver.impl.socketinstrument;

import java.io.IOException;

import app.instrument.driver.Driver;


/**
 * Generic Socket instrument driver interface
 *
 * @author pbaioni
 */
public interface ISocketInstrument extends Driver {

	//Add specific instrument methods
	
	public String echo(String msg) throws IOException;

}
