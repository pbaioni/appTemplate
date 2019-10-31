package app.instrument.exception;

/**
 * Unknown instrument model exception
 *
 * @author pbaioni
 */
public class IllegalInstrumentModel extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6858659844756816505L;

	/**
     * Constructor
     *
     * @param message The detail message
     */
    public IllegalInstrumentModel(String message) {
        super(message);
    }
}
