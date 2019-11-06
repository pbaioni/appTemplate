package app.instrument;

/**
 * Generic instrument interface
 *
 *@author pbaioni
 */
public interface IInstrument {

    /**
     * Gets instrument name
     *
     * @return Instrument name
     */
    public String getName();
    
    /**
     * Gets model informations
     *
     * @return model informations
     */
    public String getModel();
    
    /**
     * Gets instrument status
     *
     * @return Instrument status
     */
    public InstrumentStatus getStatus();
    

}
