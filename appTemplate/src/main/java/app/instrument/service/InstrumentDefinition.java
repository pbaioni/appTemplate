package app.instrument.service;

/**
 * Instrument definition
 *
 * @author pbaioni
 */
public class InstrumentDefinition {
	
	private String name;
	
	private String model;
	
	private String address;
	
	private int timeout;

	public InstrumentDefinition() {

	}
	
	public InstrumentDefinition(String name, String model, String address, int timeout) {
		super();
		this.name = name;
		this.model = model;
		this.address = address;
		this.timeout = timeout;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	@Override
	public String toString() {
		return "InstrumentDefinition [name=" + name + ", model=" + model + ", address=" + address + ", timeout="
				+ timeout + "]";
	}
}
