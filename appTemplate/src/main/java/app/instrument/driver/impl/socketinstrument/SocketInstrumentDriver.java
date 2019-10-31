package app.instrument.driver.impl.socketinstrument;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Locale;

import app.instrument.driver.AbstractDriver;
import app.instrument.io.AbstractIO;


/**
 * Socket Instrument driver implementation
 *
 * @author pbaioni
 */
public class SocketInstrumentDriver extends AbstractDriver implements ISocketInstrumentDriver {


    private final static String DELIMITER = "\n";
    
    public SocketInstrumentDriver() {
    }
    
    public SocketInstrumentDriver(String address, int timeout) {
    	super(address, timeout);
    }

    @Override
    public void connect(AbstractIO io) throws IOException {
        super.connect(io);
    }

    @Override
    public String read() throws IOException {
        try{
            
            return read(DELIMITER);
            
        } catch(Exception e){

            throw new IllegalStateException ( e.getMessage());
        }

    }

    @Override
    public void send(String cmd) throws IOException {

        try{
            getIO().write(cmd + DELIMITER);
        
        } catch(Exception e){

            throw new IllegalStateException ( e.getMessage());
            
        }
    }

    @Override
    public void checkConnectionStatus() throws IOException {
    	
    }

    private String read(String delimiter) throws IOException {
        String s = getIO().read(delimiter);
        return s;
    }

}
