import java.util.ArrayList;

/**
 * This class represents a synchronized circular buffer. Two indices control where data is read from and written to in the structure; when they hit the end of it, they wrap around to the beginning. This is to say, it is "circular" - reading and writing is done in a loop [physically speaking].
 * <p>
 * The size of this data structure is unchanging. Internally, it uses an ArrayList to create a list using generic types. By setting the capacity of this structure, this internally acts the same as a normal array, however the ArrayList data structure allows us to use generics to turn this into a circular buffer of whatever we want. Note while this is *not* a synchronized data structure, we can get away with it as the methods that access it <i>are</i> synchronized.
 * <p>
 * Assistance; <a href="https://en.wikipedia.org/wiki/Circular_buffer">Wikipedia</a>
 */
public class CircularBuffer<T> {
    private final int capacity;
    private int readIndex;
    private int writeIndex;
    private boolean closed;

    private final ArrayList<T> buffer;

    /**
     * Creates our circular buffer.
     * @param capacity The capacity of the buffer.
     */
    public CircularBuffer(int capacity) {
        this.capacity = capacity;
        this.readIndex = 0;
        this.writeIndex = 0;
        this.closed = false;

        this.buffer = new ArrayList<>();
        for(int i = 0; i < capacity; i++) {
            // For some silly reason, the capacity constructor does not automatically create an ArrayList to a given size.
            // So fill it up to that capacity with null.
            buffer.add(null);
        }
    }

    /**
     * Gets the next value in the buffer, and advances the read index.
     * @return The next value in the buffer.
     * @throws InterruptedException Throws if the thread is interrupted before we're done `wait()`ing.
     */
    public synchronized T get() throws InterruptedException {
        while(isEmpty()) {
            if(this.closed) return null;
            wait(); // This causes the threads attempting to read from this buffer to wait until they have the lock.
        }

        T data = buffer.get(this.readIndex++);
        this.buffer.set(this.readIndex - 1, null);
        this.readIndex = this.readIndex % this.capacity;

        notifyAll(); // Wake up the threads waiting for us.
        return data;
    }

    /**
     * Puts data into the next value in the buffer (which should be open), and advances the write index.
     * @param data The data to write.
     * @throws InterruptedException Throws if the thread is interrupted before we're done `wait()`ing.
     */
    public synchronized void put(T data) throws InterruptedException {
        if(this.closed) return;
        while(isFull()) {
            wait(); // This causes the threads attempting to *write* to this buffer (ala, just the master thread) to wait until they have the lock.
        }

        this.buffer.set(this.writeIndex++, data);
        this.writeIndex = this.writeIndex % this.capacity;

        notifyAll(); // Notify the threads waiting for us that theres more data in the buffer.
    }

    /**
     * "Closes" the circular buffer; no new data may be added, and any attempt to grab data from an emptied buffer will yield a null value.
     */
    public synchronized void close() {
        this.closed = true;
    }

    /**
     * Determines if the buffer is empty; that is, it's filled with null.
     * @return True if empty, false otherwise.
     */
    public boolean isEmpty() {
        for (T t : this.buffer) {
            if (t != null) return false;
        }
        return true;
    }

    /**
     * Determines if that buffer is full; that is, it's filled with non-null data.
     * @return True if full, false otherwise.
     */
    private boolean isFull() {
        for (T t : buffer) {
            if (t == null) return false;
        }
        return true;
    }

    /**
     * Determines if the buffer is closed or not.
     * @return Whether the buffer is closed or not.
     */
    public boolean isClosed() {
        return this.closed;
    }
}
