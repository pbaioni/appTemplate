/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.instrument.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author classauvetat
 */
public class IOStub extends AbstractIO {

    @Override
    public void close() throws IOException {
        throw new IllegalStateException("Input/Output is not available. The driver may be down.");
    }

    @Override
    public InputStream getInputStream() {
        throw new IllegalStateException("Input/Output is not available. The driver may be down.");
    }

    @Override
    public OutputStream getOutputStream() {
        throw new IllegalStateException("Input/Output is not available. The driver may be down.");
    }

    @Override
    public boolean isConnected() {
        return false;
    }

}
