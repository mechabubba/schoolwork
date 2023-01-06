import java.io.IOException;

/**
 * Main function to launch a WordGameServer.
 */
public class WordGameServerMain {
    public static void main(String[] args) throws Exception {
        WordGameServer server = new WordGameServer(23510, 2);
        server.execute();
    }
}
