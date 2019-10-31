package app.instrument;

/**
 * Generic instrument representation
 *
 */
public interface IGenericInstrument {

    /**
     * Connect
     *
     * @return true if succeed
     */
    public boolean connect();

    /**
     * Disconnect
     */
    public void disconnect();

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
