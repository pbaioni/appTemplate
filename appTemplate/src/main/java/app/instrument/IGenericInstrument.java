package app.instrument;

/**
 * Generic instrument representation
 *
 *@author pbaioni
 */
public interface IGenericInstrument {

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
