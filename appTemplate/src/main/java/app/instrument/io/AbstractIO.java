package app.instrument.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Abstract class to use and Input/output interface such as TCP socket, UDP
 * datagram or Serial port, etc.
 *
 * @author pbaioni
 */
public abstract class AbstractIO {

    /**
     * Close the communication
     *
     * @throws IOException if an I/O error occurs.
     */
    public abstract void close() throws IOException;

    /**
     * Returns the input stream
     *
     * @return the input stream
     */
    public abstract InputStream getInputStream();

    /**
     * Returns the output stream
     *
     * @return the output stream
     */
    public abstract OutputStream getOutputStream();

    /**
     * Returns the connection state
     *
     * @return <code>true</code> if is connected otherwise returns
     * <code>false</code>
     */
    public abstract boolean isConnected();

    /**
     * Writes string <code>data</code> from the output stream
     *
     * @param data String to write
     * @throws IOException if an I/O error occurs.
     */
    public void write(String data) throws IOException {
        byte[] bytes = data.getBytes();
        getOutputStream().write(bytes, 0, bytes.length);
        getOutputStream().flush();
    }

    /**
     * Reads the next byte of data from the input stream. The value byte is
     * returned as an <code>int</code> in the range <code>0</code> to
     * <code>255</code>. If no byte is available because the end of the stream
     * has been reached, the value <code>-1</code> is returned.
     *
     * @return the next byte of data, or <code>-1</code> if the end of the
     * stream is reached.
     * @exception IOException if an I/O error occurs.
     */
    public int read() throws IOException {
        return getInputStream().read();
    }

    /**
     * Reads up to <code>len</code> bytes of data from the input stream into an
     * array of bytes. An attempt is made to read as many as <code>len</code>
     * bytes, but a smaller number may be read. The number of bytes actually
     * read is returned as an integer.
     *
     * @param b the buffer into which the data is read.
     * @param off the start offset in array <code>b</code> at which the data is
     * written.
     * @param len the maximum number of bytes to read.
     * @return the total number of bytes read into the buffer, or
     * <code>-1</code> if there is no more data because the end of the stream
     * has been reached.
     *
     * @throws IOException if an I/O error occurs.
     */
    public int read(byte b[], int off, int len) throws IOException {
        return getInputStream().read(b, off, len);
    }

    /**
     * Read data coming from the target machine while the specified delimiter
     * occurs
     *
     * @param delimiter
     * @return data read without specified delimiter
     * @throws IOException when an I/O communication fault occurs
     */
    public String read(String delimiter) throws IOException {
        StringBuilder s = new StringBuilder();
        while (s.indexOf(delimiter) == -1) {
            s.append(readChar());
        }
        return s.substring(0, s.length() - delimiter.length());
    }

    /**
     * Read a single integer
     *
     * @return the next four bytes of this input stream, interpreted as an
     * <code>int</code>.
     * @exception IOException when an I/O communication fault occurs
     */
    public int readInt() throws IOException {
        int ch1 = getInputStream().read();
        int ch2 = getInputStream().read();
        int ch3 = getInputStream().read();
        int ch4 = getInputStream().read();
        if ((ch1 | ch2 | ch3 | ch4) < 0) {
            throw new EOFException();
        }
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
    }

    /**
     * Read a single integer (little Endian)
     *
     * @return the next four bytes of this input stream, interpreted as an
     * <code>int</code>.
     * @exception IOException when an I/O communication fault occurs
     */
    public int readIntLE() throws IOException {
        int ch1 = getInputStream().read();
        int ch2 = getInputStream().read();
        int ch3 = getInputStream().read();
        int ch4 = getInputStream().read();

        if ((ch1 | ch2 | ch3 | ch4) < 0) {
            throw new EOFException();
        }
        return ((ch4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1 << 0));
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    public float readFloatLE() throws IOException {
        return Float.intBitsToFloat(readIntLE());
    }

    public long readLong() throws IOException {
        byte[] readBuffer = new byte[8];
        read(readBuffer, 0, 8);
        return (((long) readBuffer[0] << 56)
                + ((long) (readBuffer[1] & 255) << 48)
                + ((long) (readBuffer[2] & 255) << 40)
                + ((long) (readBuffer[3] & 255) << 32)
                + ((long) (readBuffer[4] & 255) << 24)
                + ((readBuffer[5] & 255) << 16)
                + ((readBuffer[6] & 255) << 8)
                + ((readBuffer[7] & 255)));
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble((this.readLong()));
    }

    public char readChar() throws IOException {
        int ch1 = getInputStream().read();
        if ((ch1) < 0) {
            throw new EOFException();
        }
        return (char) ch1;
    }

    /**
     * Clears completly the inputstream buffer
     */
    public void clearBuffer() {
        try {
            while (getInputStream().available() > 0) {
                getInputStream().read();
            }
        } catch (IOException ex) {
            //Ignores
        }
    }
}
