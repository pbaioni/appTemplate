package app.instrument.io.impl;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import app.instrument.io.AbstractIO;

/**
 * Process launcher
 *
 * @author pbaioni
 */
public class ProcessIO extends AbstractIO {

    private final Process process;

    public ProcessIO(URI uri, int timeout) throws IOException {
        ArrayList<String> args = new ArrayList<String>();
        args.add(uri.getSchemeSpecificPart());
        args.addAll(Arrays.asList(uri.getFragment().split("&")));
        process = Runtime.getRuntime().exec(args.toArray(new String[args.size()]), null, null);
    }

    /**
     * Gets process
     *
     * @return process info
     */
    public Process getProcess() {
        return process;
    }

    @Override
    public void close() throws IOException {
        process.destroy();
    }

    @Override
    public InputStream getInputStream() {
        return process.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() {
        return process.getOutputStream();
    }

    @Override
    public boolean isConnected() {
        try {
            process.exitValue();
            return false;
        } catch (IllegalThreadStateException ex) {
            return true;
        }
    }
}
