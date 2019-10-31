package app.instrument;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.instrument.driver.AbstractDriver;
import app.instrument.driver.Driver;

/**
 * Standard instrument using a <code>Driver</code> to communicate with a
 * specific equipment
 *
 * @param <T> Driver Type
 */
public class InstrumentIO<T extends AbstractDriver>  {

	private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentIO.class);

	private String address;
	private int timeout;
	private final AbstractDriver driver;


	/**
	 * Constructor
	 *
	 * @param name   Instrument name
	 * @param status Default instrument status
	 * @param driver Instrument driver
	 */
	public InstrumentIO(String address, int timeout, AbstractDriver driver) {
		this.address = address;
		this.timeout = timeout;
		this.driver = driver;
	}

	/**
	 * Gets equipment address
	 *
	 * @return Equipment address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets equipment address
	 *
	 * @param address equipment address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets equipment timeout
	 *
	 * @return timeout in ms
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * Sets equipment timeout
	 *
	 * @param timeout timeout in ms
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * Gets driver
	 *
	 * @return driver
	 */
	public Driver getDriver() {
		return driver;
	}

}
