import java.io.IOException;
import java.util.Scanner;

/**
 * This simply tests text communication between the client and a server.
 * <p>
 * <b style="color: red">Before launching:</b> Start a game server on the same machine, on the port defined below.
 */
public class WordGameServerTest {
    public static void main(String[] args) throws IOException {
        WordGameClient client = new WordGameClient("localhost", 23510);

        Scanner input = new Scanner(System.in);
        while(!client.isClosed()) {
            System.out.print("> ");
            String msg = input.nextLine();
            System.out.print("\033[2K"); // Erase line content
            client.send(msg);
            try {
                Thread.sleep(100); // Small amount of wait time to compensate for latency.
            } catch(InterruptedException ex) {
                // uh oh
            }
        }
    }
}
