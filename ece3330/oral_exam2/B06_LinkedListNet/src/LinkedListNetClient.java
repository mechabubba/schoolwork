import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * LinkedListNetServer represents the client-side of the linked list network, handling responses from the server. In this case, the client just displays what the server responds with.
 */
public class LinkedListNetClient extends LinkedListNetEndpoint {
    private DatagramSocket socket; // socket to connect to server
    private final String serverIP;
    private final int serverPort;

    public LinkedListNetClient() {
        this("localhost", 23513);
    }

    /**
     * Creates a linked list net client.
     * @param serverIP The IP to attempt to send packets to.
     * @param serverPort The port of the IP.
     */
    public LinkedListNetClient(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        try {
            this.socket = new DatagramSocket();
        } catch (SocketException socketException) {
            socketException.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Runs infinitely, polls for packets from the server.
     * <p>
     * This case is much simpler than the server side, as it just has to take packets in and serialize them to a String.
     */
    public void run() {
        while (true) {
            try {
                // Set up the packet.
                byte[] packet = new byte[chunkSize];
                DatagramPacket received = new DatagramPacket(packet, packet.length);

                // Wait to receive the packet.
                this.socket.receive(received); // Packet received!
                packet = stripNull(packet); // Strip null bytes automatically added to the end of the packet.

                byte code = packet[0]; // The opcode of the packet.
                log((code == 0x00 ? "Success" : "Failure") + " response recieved!");

                // Parse the string and display it.
                String response = new String(packet, 1, packet.length - 1).trim();
                log("\"" + response + "\"");
            } catch (IOException exception) {
                log(exception + "\n");
                exception.printStackTrace();
            }
        }
    }

    /**
     * Gets the clients' socket.
     * @return The clients' socket.
     */
    public DatagramSocket getSocket() {
        return this.socket;
    }

    /**
     * Gets the IP of the server the client attempts to connect to.
     * @return The servers IP.
     */
    public String getServerIP() {
        return this.serverIP;
    }

    /**
     * Gets the port of the server the client attempts to connect to.
     * @return The servers port.
     */
    public int getServerPort() {
        return this.serverPort;
    }

    /**
     * Makes a client-sided log.
     * @param data The string to log.
     */
    private void log(String data) {
        System.out.println("[CLIENT] " + data);
    }
}
