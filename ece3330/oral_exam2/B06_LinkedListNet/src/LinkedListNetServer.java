import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

/**
 * LinkedListNetServer represents the server-side of the linked list network, handling all commands attempting to modify the linked list.
 */
public class LinkedListNetServer extends LinkedListNetEndpoint {
    private DatagramSocket socket;
    private final EnhancedLinkedList<String> list;

    /**
     * Constructs a linked list net server with a default port.
     */
    public LinkedListNetServer() {
        this(23513);
    }

    /**
     * Constructs a linked list net server.
     * @param port The port to start the server on.
     */
    public LinkedListNetServer(int port) {
        this.list = new EnhancedLinkedList<>();
        try {
            this.socket = new DatagramSocket(port);
        } catch (SocketException socketException) {
            socketException.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Runs infinitely, polls for packets from the client.
     * <p>
     * One note about this implementation; all numbers, bytes, etc. sent to the client are serialized into strings, as this is the datatype the client expects and automatically serializes it to.
     */
    public void run() {
        while(true) {
            try {
                // Set up the packet.
                byte[] packet = new byte[chunkSize];
                DatagramPacket received = new DatagramPacket(packet, packet.length);

                // Wait to receive the packet.
                socket.receive(received); // Packet received!
                packet = stripNull(packet); // Strip null bytes from the end of the packet.

                log("Packet recieved!");
                byte code = packet[0]; // The opcode of the packet.

                // Do something based on the command and data we received from the client.
                switch(code) {
                    case 0x01:   // Insert data at front.
                    case 0x02: { // Insert data at back.
                        String value = new String(packet, 1, packet.length - 1);
                        if(code == 0x01) {
                            this.list.insertAtFront(value);
                        } else {
                            this.list.insertAtBack(value);
                        }

                        byte[] arr = { 0x00 };
                        sendDataToClient(received, arr);
                        break;
                    }

                    case 0x03:   // Remove data from front.
                    case 0x04: { // Remove data from back.
                        byte[] response;
                        try {
                            if(code == 0x03) {
                                this.list.removeFromFront();
                            } else {
                                this.list.removeFromBack();
                            }

                            response = new byte[1];
                            response[0] = 0x00;
                        } catch(ArrayIndexOutOfBoundsException e) {
                            byte[] message = e.getMessage().getBytes();
                            response = new byte[message.length + 1];
                            response[0] = 0x01;
                            for(int i = 0; i < message.length; i++) {
                                response[i + 1] = message[i];
                            }
                        }
                        sendDataToClient(received, response);
                        break;
                    }

                    case 0x05: { // Get size of list.
                        byte[] value = String.valueOf(this.list.size()).getBytes(StandardCharsets.US_ASCII);
                        byte[] response = new byte[value.length + 1];
                        response[0] = 0x00;
                        for(int i = 0; i < value.length; i++) {
                            response[i + 1] = value[i];
                        }

                        sendDataToClient(received, response);
                        break;
                    }

                    case 0x06: { // Get value at index.
                        byte[] response;
                        try {
                            byte[] value = this.list.get(packet[1]).getBytes();
                            response = new byte[value.length + 1];
                            response[0] = 0x00;
                            for(int i = 0; i < value.length; i++) {
                                response[i + 1] = value[i];
                            }

                            sendDataToClient(received, response);
                            break;
                        } catch(ArrayIndexOutOfBoundsException e) {
                            byte[] message = e.getMessage().getBytes();
                            response = new byte[message.length + 1];
                            response[0] = 0x01;
                            for(int i = 0; i < message.length; i++) {
                                response[i + 1] = message[i];
                            }
                        }
                        sendDataToClient(received, response);
                        break;
                    }

                    case 0x07: { // Display whole list.
                        String value = this.list.toString();
                        byte[] response = new byte[value.length() + 1];
                        response[0] = 0x00;
                        for(int i = 0; i < value.length(); i++) {
                            response[i + 1] = (byte) value.charAt(i);
                        }

                        sendDataToClient(received, response);
                        break;
                    }
                }
            } catch (IOException ioException) {
                log(ioException + "\n");
                ioException.printStackTrace();
            }
        }
    }

    /**
     * Sends an array of bytes to the client that requested it.
     * @param packet The packet that was originally sent to the server - includes information about the client that sent it.
     * @param data The data to respond with.
     * @throws IOException Thrown if something goes wrong with sending the packet through the socket.
     */
    private void sendDataToClient(DatagramPacket packet, byte[] data) throws IOException {
        DatagramPacket sendPacket = new DatagramPacket(
            data, data.length,
            packet.getAddress(), packet.getPort()
        );
        log("Sending response...");
        socket.send(sendPacket); // send packet to client
    }

    /**
     * Makes a server-sided log.
     * @param data The string to log.
     */
    private void log(String data) {
        System.out.println("[SERVER] " + data);
    }
}
