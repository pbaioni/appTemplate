package app.instrument.io.impl.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Flux d'écriture dans un "pipe".<br>
 * Permet d'écrire du contenu accessible depuis un
 * {@link PipeInputStream flux de lecture}
 *
 * @author TAS
 * @version 1.00
 * @see PipeInputStream
 */
public class PipeOutputStream extends OutputStream {

    private PipeInputStream is;

    /**
     * Constructeur
     *
     * @param is Flux de lecture à associer
     */
    public PipeOutputStream(PipeInputStream is) {
        super();
        this.is = is;
    }

    /**
     * @return Flux de lecture associé
     */
    public InputStream getInputStream() {
        return is;
    }

    /**
     * @return Nombre d'octets disponible dans le flux de lecture ou retourne -1
     * si fermé
     */
    public int available() {
        PipeInputStream is = this.is;
        return (is == null) ? -1 : is.available();
    }

    @Override
    public void write(int b) throws IOException {
        write(new byte[]{(byte) b}, 0, 1);
    }

    @Override
    public synchronized void write(byte[] b, int off, int len) throws IOException {
        if (is == null || is.isClosed()) {
            throw new IOException("Pipe closed");
        }
        is.addBytes(b, off, len);
    }

    @Override
    public synchronized void close() throws IOException {
        if (is != null) {
            is.close();
        }
        is = null;
    }

    /**
     * Permet de savoir si le flux est fermé
     *
     * @return Vrai si le flux est fermé, sinon faux
     */
    public boolean isClosed() {
        PipeInputStream is = this.is;
        return is == null || is.isClosed();
    }
}
