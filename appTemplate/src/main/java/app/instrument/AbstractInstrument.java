package app.instrument;

import app.instrument.driver.AbstractDriver;
import app.instrument.driver.Driver;
import app.instrument.driver.helper.DriverHelper;

/**
 * Abstract implementation to communicate with a specific equipment
 *
 */
public abstract class AbstractInstrument implements IGenericInstrument{

    private String name;
    private String model;
    private InstrumentStatus status;
    private AbstractDriver driver;
    
    public AbstractInstrument() {

    }
    /**
     * Constructor
     *
     * @param name Instrument name
     * @param status Default instrument status
     */
    public AbstractInstrument(String address, int timeout, Driver driver) {

        this.status = InstrumentStatus.DISCONNECTED;
        this.driver = DriverHelper.getDriverForModel(model);
    }

    public final String getName() {
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
    
    public final InstrumentStatus getStatus() {
        return status;
    }
    
    protected void setStatus(InstrumentStatus status) {
        this.status = status;
    }    

	public AbstractDriver getDriver() {
		return driver;
	}
	public void setDriver(AbstractDriver driver) {
		this.driver = driver;
	}
	@Override
    public String toString() {
        return getName() + "@" + getClass().getSimpleName();
    }

}
