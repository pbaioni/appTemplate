package app.instrument.io.utils;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;

/**
 * Circular buffer
 *
 * @author pbaioni
 */
public class CircularBuffer {

    private final byte[] buffer;
    private int head;
    private int tail;
    private int used;

    public CircularBuffer(int bufferSize) {
        buffer = new byte[bufferSize];
        tail = 0;
        head = -1;
        used = 0;
    }

    public final int size() {
        return buffer.length;
    }

    public final int free() {
        return buffer.length - available();
    }

    public final int available() {
        return used;
    }

    public synchronized int addMax(byte[] bytes, int offset, int length) {
        int size = Math.min(length, free());
        addBytes(bytes, offset, size);
        return size;
    }

    public void add(byte[] bytes) {
        addBytes(bytes, 0, bytes.length);
    }

    public synchronized void add(byte[] bytes, int offset, int length) {
        if (free() < length) {
            throw new BufferOverflowException();
        }
        addBytes(bytes, offset, length);
    }

    public synchronized byte poolFirst() {
        if (available() < 1) {
            throw new BufferUnderflowException();
        }
        byte ret = buffer[tail++];
        used--;
        return ret;
    }

    public int get(byte[] buf) {
        return get(buf, 0, buf.length);
    }

    public synchronized int get(byte[] buf, int offset, int length) {
        int l = Math.min(available(), length);
        if (l > 0) {
            int bytesToTransfer = l;
            while (bytesToTransfer > 0) {
                int size = Math.min(bytesToTransfer, buffer.length - tail);
                System.arraycopy(buffer, tail, buf, offset, size);
                offset += size;
                bytesToTransfer -= size;
                tail = (tail + size) % buffer.length;
            }
            used -= l;
        }
        return l;
    }

    @Override
    public String toString() {
        return "CircularBuffer(size=" + buffer.length + ", head=" + head + ", tail=" + tail + ")";
    }

    private void addBytes(byte[] bytes, int offset, int length) {
        int bytesToTransfer = length;
        if (head == -1) {
            head = 0;
        }
        while (bytesToTransfer > 0) {
            int size = Math.min(bytesToTransfer, buffer.length - head);
            System.arraycopy(bytes, offset, buffer, head, size);
            offset += size;
            bytesToTransfer -= size;
            head = (head + size) % buffer.length;
        }
        used += length;
    }
}
