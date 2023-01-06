import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The main class creates a client and server in their own threads, and allows you to use the console to have the two classes interact with eachother.
 */
public class LinkedListNetMain {
    private static LinkedListNetClient client;
    private static DatagramSocket socket;

    /**
     * The main method.
     * @param args Provided command line arguments.
     */
    public static void main(String[] args) {
        // Attempt to start and connect to server.
        System.out.println("Attempting to create a server...");

        // We're using multiple threads to host a server and a client in one go.
        // This is necessary as each class loops infinitely to poll the sockets.
        // The client and server run in their own threads.
        LinkedListNetServer server = new LinkedListNetServer();
        client = new LinkedListNetClient();
        socket = client.getSocket(); // We will make requests from this main class.

        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(server);
        service.execute(client);
        service.shutdown();

        // UX stuff.
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the networked linked list experience.\n");
        System.out.println("# COMMANDS");
        System.out.println("1. Insert data at front.");
        System.out.println("2. Insert data at back.");
        System.out.println("3. Remove data from front.");
        System.out.println("4. Remove data from back.");
        System.out.println("5. Get size of list.");
        System.out.println("6. Get value at index.");
        System.out.println("7. Display whole list.");
        System.out.println("8. Exit.");

        // Loop until
        while(true) {
            System.out.print("\nInput a command; >>> ");
            int value = input.nextInt();
            input.nextLine(); // Clear newline from nextInt().

            // How this works;
            // - Create a list of bytes.
            // - Insert an "opcode" byte at the beginning that represents the command on the serverside.
            // - Place all other data after that.
            // Then we send that to the server for processing.

            switch(value) {
                case 1:   // Insert data at front.
                case 2: { // Insert data at back.
                    System.out.print("Input a string; > ");
                    String data = input.nextLine();

                    byte[] request = new byte[data.length() + 1];
                    request[0] = (byte) value;
                    for(int i = 0; i < data.length(); i++) {
                        request[i + 1] = (byte) data.charAt(i);
                    }

                    sendDataToServer(request);
                    break;
                }

                case 3:   // Remove data from front.
                case 4:   // Remove data from back.
                case 5:   // Get size of list.
                case 7: { // Display whole list.
                    byte[] request = { (byte) value };
                    sendDataToServer(request);
                    break;
                }

                case 6: { // Get value at index.
                    System.out.print("Input an index; > ");
                    byte index = input.nextByte();

                    // One extra byte is added at the end of this request to stop the null byte removal, in case it attempts to remove the index we're looking for.
                    byte[] request = { (byte) value, index, (byte) 0xFF};
                    sendDataToServer(request);
                    break;
                }

                case 8: { // Exit.
                    System.out.println("Exiting!");
                    System.exit(0);
                }

                default: { // ???
                    System.out.println("Error: unknown choice selected.");
                    break;
                }
            }

            // Purely for the experience of the demo, a few hundred ms of sleep time is added in the main thread to keep things in the console formatted without issue.
            // This should never cause issues with the server and client; as they're hosted on the same machine, latency between request and response should not be an issue.
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Sends data to the server through the clients' socket.
     * @param data The byte array to send to the server.
     */
    private static void sendDataToServer(byte[] data) {
        try {
            // Create the packet.
            String ip = client.getServerIP();
            InetAddress host = ip.equals("localhost") ? InetAddress.getLocalHost() : InetAddress.getByAddress(ip.getBytes());
            DatagramPacket sendPacket = new DatagramPacket(
                data, data.length,
                host, client.getServerPort()
            );

            // Send it through the client socket.
            socket.send(sendPacket);
        } catch (IOException ioException) {
            System.err.println("An exception occurred; " + ioException + "\n");
            ioException.printStackTrace();
        }
    }
}