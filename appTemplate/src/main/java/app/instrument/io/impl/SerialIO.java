package app.instrument.io.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import app.instrument.io.AbstractIO;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import jssc.SerialPortTimeoutException;

/**
 * IO communication with Serial Port
 *
 * @author pbaioni
 */
public class SerialIO extends AbstractIO {

    private static final int BUFFER_READ_MAX = 1024;

    private SerialPort port;
    private SerialInputStream is;
    private SerialOutputStream os;
    private int timeout;

    /**
     * Parity settings
     */
    public enum Parity {

        /**
         * No parity
         */
        NONE(SerialPort.PARITY_NONE),
        /**
         * Even
         */
        EVEN(SerialPort.PARITY_EVEN),
        /**
         * ODD
         */
        ODD(SerialPort.PARITY_ODD),
        /**
         * Mark
         */
        MARK(SerialPort.PARITY_MARK),
        /**
         * Space
         */
        SPACE(SerialPort.PARITY_SPACE);

        private final int value;

        Parity(int value) {
            this.value = value;
        }

        /**
         * @return Parity value
         */
        public int getValue() {
            return value;
        }
    }

    public SerialIO(String deviceName, int baudRate, int dataBits, int stopBits,
            Parity parity, int timeout) throws IOException {
        try {
            port = new SerialPort(deviceName);
            port.openPort();
            port.setParams(baudRate, dataBits, stopBits,
                    parity.getValue(), false, false);
            is = new SerialInputStream();
            os = new SerialOutputStream();
            this.timeout = timeout;
        } catch (SerialPortException e) {
            throw new IOException(e);
        }
    }

    /**
     * Gets the list of all available port's names
     *
     * @return all available port's names
     */
    public static String[] getSerialPortNames() {
        return SerialPortList.getPortNames();
    }

    @Override
    public void close() throws IOException {
        try {
            port.closePort();
        } catch (SerialPortException e) {
            throw new IOException(e);
        }
    }

    @Override
    public boolean isConnected() {
        return port.isOpened();
    }

    @Override
    public InputStream getInputStream() {
        return is;
    }

    @Override
    public OutputStream getOutputStream() {
        return os;
    }

    private class SerialInputStream extends InputStream {

        @Override
        public int read() throws IOException {
            try {
                return port.readIntArray(1, timeout)[0];
            } catch (SerialPortException e) {
                SerialIO.this.close();
                throw new IOException(e);
            } catch (SerialPortTimeoutException ex) {
                return -1;
            }
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            try {
                if (len > BUFFER_READ_MAX) {
                    //Split the frame to prevent full input buffer from serial port
                    int ret = read(b, off, BUFFER_READ_MAX);
                    if (ret > 0) {
                        int ret2 = read(b, off + ret, len - ret);
                        if (ret2 > 0) {
                            ret += ret2;
                        } else {
                            ret = ret2;
                        }
                    }
                    return ret;
                }
                byte[] read = port.readBytes(len, timeout);
                if (read.length > 0) {
                    System.arraycopy(read, 0, b, off, read.length);
                }
                return read.length;
            } catch (SerialPortException e) {
                SerialIO.this.close();
                throw new IOException(e);
            } catch (SerialPortTimeoutException ex) {
                return -1;
            }
        }

        @Override
        public int available() throws IOException {
            try {
                return port.getInputBufferBytesCount();
            } catch (SerialPortException e) {
                throw new IOException(e);
            }
        }
    }

    private class SerialOutputStream extends OutputStream {

        @Override
        public void write(int b) throws IOException {
            try {
                port.writeInt(b);
            } catch (SerialPortException e) {
                throw new IOException(e);
            }
        }

        @Override
        public void write(byte[] buffer, int off, int len) throws IOException {
            try {
                if (buffer.length == len && off == 0) {
                    port.writeBytes(buffer);
                } else {
                    port.writeBytes(Arrays.copyOfRange(buffer, off, off + len));
                }
            } catch (SerialPortException e) {
                throw new IOException(e);
            }
        }
    }
}
