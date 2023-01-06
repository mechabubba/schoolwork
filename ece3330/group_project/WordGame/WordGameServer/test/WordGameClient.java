import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A copy of the WordGame client, kept in here as an individual class to test text-based communication between the server and client.
 * <p>
 * You shouldn't run this individually - run the WordGameServerTest classes instead!
 * <p>
 * Heavily inspired by TicTacToe textbook example (fig21_11_14).
 */
public class WordGameClient implements Runnable {
    private static final String terminationString = "CLIENT>>> TERMINATE";

    private final String host;
    private final int port;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private boolean closed;

    /**
     * Constructs a word game client.
     * @param host The host to connect to.
     * @param port The port of the host.
     */
    public WordGameClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.closed = false;
        start();
    }

    /**
     * Instantiates a connection to the server; creates the socket and starts polling for responses in another thread via the thread pool.
     */
    public void start() {
        try {
            this.socket = new Socket(InetAddress.getByName(this.host), this.port);
            this.input = new ObjectInputStream(this.socket.getInputStream());
            this.output = new ObjectOutputStream(this.socket.getOutputStream());
        } catch(IOException exception) {
            System.err.println("An error occured whilst attempting to connect to the server;");
            exception.printStackTrace();
        }

        ExecutorService worker = Executors.newCachedThreadPool();
        worker.execute(this); // Execute *this* classes' run() method.
        worker.shutdown();
    }

    /**
     * Process a message from the server. Right now, this only prints it to the console.
     * @param message The message to process.
     */
    public void processMessage(String message) {
        if(message.equals(terminationString)) {
            this.close();
        } else {
            String[] args = message.split(" ");
            // Commands start with a slash '/' character.
            if(message.charAt(0) == '/') {
                String command = args[0].substring(1);
                switch(command) {
                    // asdf
                }
            }
            System.out.print(message);
        }
    }

    /**
     * Sends a message to the server.
     * @param message The message to send.
     * @throws IOException Thrown if there's an issue sending the message.
     */
    public void send(String message) throws IOException {
        this.output.writeObject(message);
        this.output.flush();
    }

    /**
     * Polls responses from the server and handles them accordingly.
     */
    public void run() {
        do {
            try {
                String message = (String) input.readObject(); // Read in the object and cast it to a string.
                processMessage(message); // Process it.
            } catch (ClassNotFoundException ex) {
                System.err.println("An unknown object type was received.");
                ex.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } while (!closed);
    }

    public void close() {
        try {
            this.closed = true;
            this.socket.close();
        } catch(IOException ex) {
            // Something happened whilst trying to close the client, so just end it.
            System.exit(1);
        }
    }

    public boolean isClosed() {
        return this.closed;
    }
}
