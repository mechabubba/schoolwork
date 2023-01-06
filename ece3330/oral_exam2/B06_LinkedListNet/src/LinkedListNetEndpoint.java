/**
 * This class harbors some methods used by both the client and the server.
 */
public abstract class LinkedListNetEndpoint implements Runnable {
    protected static final int chunkSize = 200; // Size to initialize the packet byte array to on the client and the server.

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void run();

    /**
     * Strips null bytes from the end of the array.
     * <p>
     * When packets are sent in between the server and client, they are received in chunks of 200 bytes (or whatever the length of the default packet array is set to); the bytes at the end are just null. If these bytes are kept on, any data received or transmitted is silently and continually corrupted. This function removes said bytes from the back.
     * @param input The input array of bytes.
     * @return Another array with the null bytes at the end chopped off.
     */
    protected byte[] stripNull(byte[] input) {
        int i = input.length - 1;
        while(i >= 0 && input[i] == 0) {
            i--;
        }
        if(i < 0) return input;

        byte[] result = new byte[i + 1];
        for(int j = 0; j < result.length; j++) {
            result[j] = input[j];
        }
        return result;
    }
}
