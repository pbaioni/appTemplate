package app.instrument.io;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.instrument.io.impl.PipeIO;
import app.instrument.io.impl.SerialIO;
import app.instrument.io.impl.SocketIO;



/**
 * Factory to construct IO object
 *
 */
public class IOFactory {

    private static final Pattern SOCKET_PATTERN = Pattern.compile("\\d{1,3}(?:\\.\\d{1,3}){3}:(?:\\d{1,5})?"); //NOI18N
    private static final Pattern SERIAL_PATTERN = Pattern.compile("(COM\\d+)(?::(\\d+):(\\d):(\\d):(\\w+))?"); //NOI18N
    
    /**
     * Creates a IO object
     *
     * @param address Remote address
     * @param timeout Timeout (ms)
     * @return IO object
     * @throws IOException I/O exception
     */
    public static AbstractIO createIO(String address, int timeout) throws IOException {
    	
    	//tcp connection case
        if (SOCKET_PATTERN.matcher(address).matches()) {
            String add[] = address.split(":"); //NOI18N
            return new SocketIO(add[0], Integer.valueOf(add[1]), timeout);
        }
        
        //serial connection case
        Matcher m = SERIAL_PATTERN.matcher(address);
        if (m.matches()) {
            int baudRate = 115200;
            int dataBits = 8;
            int stopBits = 1;
            SerialIO.Parity parity = SerialIO.Parity.NONE;
            if (m.group(2) != null) {
                baudRate = Integer.valueOf(m.group(2));
                dataBits = Integer.valueOf(m.group(3));
                stopBits = Integer.valueOf(m.group(4));
                parity = SerialIO.Parity.valueOf(m.group(5));
            }
            return new SerialIO(m.group(1), baudRate, dataBits, stopBits, parity, timeout);
        }

        //default case
        return new PipeIO();
    }
    

}
