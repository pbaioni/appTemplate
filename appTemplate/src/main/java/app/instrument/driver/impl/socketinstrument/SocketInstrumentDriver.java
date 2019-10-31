package app.instrument.driver.impl.socketinstrument;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Locale;

import app.instrument.driver.AbstractDriver;
import app.instrument.io.AbstractIO;


/**
 * Miteq DownConverter driver implementation
 *
 * @author pbaioni
 */
public class SocketInstrumentDriver extends AbstractDriver implements ISocketInstrumentDriver {


    private String password;

    public SocketInstrumentDriver() {
    }

    @Override
    public void connect(AbstractIO io) throws IOException {
        super.connect(io);
        try {
            // login
            String answer = read("?");
            if (!"PASSWORD".equals(answer)) {
                throw new IOException("Connection failed. Read: " + answer);
            }

            send(password);
            answer = read();
            if (!"OK".equals(answer)) {
                throw new IOException("Login failed. Read: " + answer);
            }
        } catch (IOException ex) {
            disconnect();
            throw ex;
        } catch (RuntimeException ex) {
            disconnect();
            throw ex;
        }
    }

    @Override
    public String read() throws IOException {
        try{
            
            return read("\r\n");
            
        } catch(Exception e){

            throw new IllegalStateException ( e.getMessage());
        }

    }

    @Override
    public void send(String cmd) throws IOException {

        try{
            getIO().write(cmd + "\r\n");
        
        } catch(Exception e){

            throw new IllegalStateException ( e.getMessage());
            
        }
    }

    @Override
    public void checkStatus() throws IOException {
    	
    }

    private String read(String delimiter) throws IOException {
        String s = getIO().read(delimiter);
        return s;
    }

}
