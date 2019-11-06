package app.instrument;

/**
 * List of status
 *
 *@author pbaioni
 */
public enum InstrumentStatus {

    /**
     * Unknown
     */
    UNKNOWN,
    /**
     * Disconnected
     */
    DISCONNECTED,
    /**
     * Connected and ready
     */
    READY,
    /**
     * Connected and processing...
     */
    RUNNING;

    /**
     * Gets connected information
     *
     * @return True if connected
     */
    public boolean isConnected() {
        return this == READY || this == RUNNING;
    }

}
