package app.instrument.exception;

/**
 * Unknown model exception
 *
 * @author pbaioni
 */
public class IllegalInstrumentModel extends Exception {

    /**
     * Constructor
     *
     * @param message The detail message
     */
    public IllegalInstrumentModel(String message) {
        super(message);
    }
}
