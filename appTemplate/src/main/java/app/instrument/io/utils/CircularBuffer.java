package app.instrument.io.utils;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;

/**
 * Classe représentant un buffer circulaire
 *
 * @author TAS
 * @version 1.00
 */
public class CircularBuffer {

    private final byte[] buffer;
    private int head;
    private int tail;
    private int used;

    /**
     * Constructeur
     *
     * @param bufferSize Taille du buffer en octet
     */
    public CircularBuffer(int bufferSize) {
        buffer = new byte[bufferSize];
        tail = 0;
        head = -1;
        used = 0;
    }

    /**
     * @return La taille du buffer en octet
     */
    public final int size() {
        return buffer.length;
    }

    /**
     * @return L'espace libre en octet
     */
    public final int free() {
        return buffer.length - available();
    }

    /**
     * @return L'espace utilisé en octet
     */
    public final int available() {
        return used;
    }

    /**
     * Ajoute toutes ou une partie des données suivant l'espace disponible au
     * buffer
     *
     * @param bytes Données à ajouter
     * @param offset Index de départ
     * @param length Nombre d'octet à ajouter
     * @return Nombre d'octet réellement ajouter au buffer
     */
    public synchronized int addMax(byte[] bytes, int offset, int length) {
        int size = Math.min(length, free());
        addBytes(bytes, offset, size);
        return size;
    }

    /**
     * Ajoute des données au buffer
     *
     * @param bytes Données à ajouter
     * @throws BufferOverflowException Si l'espace libre n'est pas suffisant
     */
    public void add(byte[] bytes) {
        addBytes(bytes, 0, bytes.length);
    }

    /**
     * Ajoute des données au buffer
     *
     * @param bytes Données à ajouter
     * @param offset Index de départ
     * @param length Nombre d'octet à ajouter
     * @throws BufferOverflowException Si l'espace libre n'est pas suffisant
     */
    public synchronized void add(byte[] bytes, int offset, int length) {
        if (free() < length) {
            throw new BufferOverflowException();
        }
        addBytes(bytes, offset, length);
    }

    /**
     * Récupère un octet du buffer
     *
     * @return l'octet lu
     * @throws BufferUnderflowException Si aucune n'est à lire
     */
    public synchronized byte poolFirst() {
        if (available() < 1) {
            throw new BufferUnderflowException();
        }
        byte ret = buffer[tail++];
        used--;
        return ret;
    }

    /**
     * Récupère des données du buffer
     *
     * @param buf Buffer de réception
     * @return Nombre d'octet(s) lu(s)
     */
    public int get(byte[] buf) {
        return get(buf, 0, buf.length);
    }

    /**
     * Récupère des données du buffer
     *
     * @param buf Buffer de réception
     * @param offset Index de départ
     * @param length Nombre d'octet à lire au maximum
     * @return Nombre d'octet(s) lu(s)
     */
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
