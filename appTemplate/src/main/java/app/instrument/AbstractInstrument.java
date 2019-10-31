package app.instrument;

import app.instrument.driver.DriverImpl;
import app.instrument.driver.Driver;
import app.instrument.driver.helper.DriverHelper;

/**
 * Abstract implementation to communicate with a specific equipment
 *
 *@author pbaioni
 */
public abstract class AbstractInstrument extends DriverImpl implements IGenericInstrument{

    private String name;
    private String model;
    private InstrumentStatus status;
    private DriverImpl driver;
    
    public AbstractInstrument() {

    }
    /**
     * Constructor
     *
     * @param name Instrument name
     * @param status Default instrument status
     */
    public AbstractInstrument(String address, int timeout, String model) {

    	this.model = model;
        setStatus(InstrumentStatus.DISCONNECTED);
        this.driver = DriverHelper.getDriverForModel(address, timeout, model);
    }
    
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getModel() {
		return model;
	}

	@Override
    public  InstrumentStatus getStatus() {
        return status;
    }

    public final void setName(String name) {
        this.name = name;
    }
    private void setStatus(InstrumentStatus status) {
        this.status = status;
    }    

	public DriverImpl getDriver() {
		return driver;
	}
	
	@Override
	public String toString() {
		return "AbstractInstrument [name=" + name + ", model=" + model + ", status=" + status + ", driver=" + driver
				+ "]";
	}

}
