import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.*;

/**
 * The WordGameServer class. Handles all serverside operations, including the game's function itself.
 * <p>
 * This class is capable of handling multiple clients, messaging between the clients, and intercepting slash-formatted commands from them. We're dealing with a lot of threads here, so heres a helpful rundown;
 * <ul>
 *     <li>The master thread simply initiates the main game loop; this is frozen at certain times using the ReentrantLock gameLock.</li>
 *     <li>Each client has its own thread that runs in the Player <code>run()</code> class. This polls for messages from each client indefinitely, and sends them to the servers <code>processMessage()</code> method.</li>
 *     <li>Finally, a thread is spawned from WordGameServer to watch for new connections, and let them in if theres room.</li>
 * </ul>
 * <p>
 * This is inspired by the TicTacToe textbook example (<code>fig21_11_14</code>).
 * @see java.util.concurrent.locks.ReentrantLock
 */
public class WordGameServer implements Runnable {
	private final int port;
	private final int backlog;
	private ServerSocket server;
	private final Player[] players;
	private final ExecutorService clients;

	private int playerNumber;
	private Player host;

	private WordGameSettings settings;
	private gameMode game;
	private String letterSequence;

	private Lock gameLock; // to lock game for synchronization
	private Condition waitingForPlayers;
	private boolean gameInProgress;

	/**
	 * Constructs a server.
	 * @param port The port to host the server on.
	 * @param backlog The maximum connections allowed in the ServerSocket.
	 */
	public WordGameServer(int port, int backlog) throws InterruptedException {
		this.port = port; // The port to host the server on.
		this.backlog = backlog; // The amount of incoming connections to accept.
		this.players = new Player[this.backlog];
		this.clients = Executors.newCachedThreadPool();
		this.playerNumber = 0;

		this.settings = new WordGameSettings(); // Object that keeps track of a specific games settings.
		this.gameLock = new ReentrantLock();
		this.gameInProgress = false;

		// Create the server socket.
		try {
			this.server = new ServerSocket(this.port, this.backlog);
		} catch(IOException exception) {
			System.out.println("Error occured whilst starting the server socket;");
			exception.printStackTrace();
			System.exit(1);
		}

		// Start polling for players in another thread.
		ExecutorService polling = Executors.newCachedThreadPool();
		polling.execute(this);
		polling.shutdown();

		execute();
	}

	/**
	 * Polls for players indefinitely.
	 */
	public void run() {
		gameLock.lock();
		while(true) {
			int nextSlot = getNextPlayerIndex();
			if(nextSlot >= 0) {
				try {
					Player p = new Player(server.accept(), nextSlot);
					p.send("/welcome");
					if(this.gameInProgress) {
						// If a game is in progress, send the game start signal - this should put it in the game window.
						// Should automatically start ticking down.
						p.send(String.format("/gameStart %s", this.letterSequence));
					}
					if(this.host == null) {
						// First player to enter the server is the "host".
						this.resetHost();
					}
					broadcast(String.format("/userJoin %s", p.getName()));

					this.players[nextSlot] = p;
					this.clients.execute(this.players[nextSlot]); // Start polling for incoming messages from this player in a new thread.
					if(isFullServer() && !this.gameInProgress) {
						this.waitingForPlayers.signal();
						gameLock.unlock(); // Release the main thread.
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * Gets the next available player index.
	 * @return The next available player index, or -1 if the player list is full.
	 */
	private int getNextPlayerIndex() {
		for(int i = 0; i < this.players.length; i++) {
			if(this.players[i] == null) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Determines if the server is full or not.
	 * @return Whether the server is full or not.
	 */
	private boolean isFullServer() {
		for(int i = 0; i < this.players.length; i++) {
			if(players[i] == null) return false;
		}
		return true;
	}

	/**
	 * The main game loop. Runs as follows;
	 * <ol>
	 * <li>Wait for the game to start.</li>
	 * <li>Let the host of the lobby choose the type of game you want to play; a different thread will actually instantiate it.</li>
	 * <li>The game is set up, start a count down (?) and let the connected users play.</li>
	 * </ol>
	 * @throws InterruptedException Thrown if the current thread is interrupted whilst waiting.
	 */
	public void execute() throws InterruptedException {
		this.waitingForPlayers = gameLock.newCondition();
		this.gameLock.lock();
		this.waitingForPlayers.await();

		if(this.settings.getGamemode().equals("WORD")) {
			this.game = new gameModeWords(this.settings.getLanguage(), this.players.length);
		} else {
			this.game = new gameModeLetters(this.settings.getLanguage(), this.players.length);
		}

		// Generate the letters, build a list to generate to the players.
		randomLetters rand = new randomLetters();
		char[] letters = rand.getRandomWord();

		StringBuilder seq = new StringBuilder();
		for(char a : letters) {
			seq.append(a);
		}
		this.letterSequence = seq.toString();
		broadcast(String.format("/gameStart %s", this.letterSequence));
		this.gameInProgress = true; // les go

		// Game timer.
		for(int i = 60; i > 0; i--) {
			// @todo send tick to client
			Thread.sleep(1000);
		}
		broadcast("/gameEnd");

		// Games over, get the top three.
		HashMap<Integer, HashMap> map = game.getGameStats();
		HashMap<Integer, Integer> sortable = new HashMap<>();
		for (Map.Entry<Integer, HashMap> entry : map.entrySet()) {
			int key = entry.getKey();
			HashMap<String, scoringPoints> submap = entry.getValue();
			int sum = sumScores(submap);
			sortable.put(key, sum);
		}

		// fuck
		Set<Map.Entry<Integer, Integer>> set = sortable.entrySet();
		List<Map.Entry<Integer, Integer>> list = new ArrayList<>(set);
		list.sort(new Comparator<Map.Entry<Integer, Integer>>() {
			public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		String[] names = new String[3];
		for(int i = 0; i < 3; i++) {
			if(list.size() <= i) {
				names[i] = "N/A";
			} else {
				Map.Entry<Integer, Integer> entry = list.get(i);
				Player p = getPlayerByID(entry.getKey());
				names[i] = p.getName();
			}
		}

		broadcast(String.format("/sendLeaders %s %s %s", names[0], names[1], names[2]));
	}

	private int sumScores(Map<String, scoringPoints> map) {
		int sum = 0;
		for(Map.Entry<String, scoringPoints> entry : map.entrySet()) {
			scoringPoints score = entry.getValue();
			sum += score.getPoint();
		}
		return sum;
	}

	/**
	 * Processes a string message sent from a client. If a string is returned, it will be sent only to whoever sent the message to the server.
	 * @param sender The Player representation that sent the message.
	 * @param message The message they sent.
	 * @return A message to be returned to the sender, or <code>null</code> otherwise.
	 */
	private String processMessage(Player sender, String message) {
		String[] args = message.split(" ");
		// Commands start with a slash '/' character.
		if(message.charAt(0) == '/') {
			// The user issued a command.
			System.out.printf("%s issued command: %s\n", sender.getName(), message);
			String command = args[0] = args[0].substring(1).toLowerCase(); // The command.

			// Handle the command the user sent.
			switch(args[0]) {
				case "ping": { // Responds with a ping message; useful to determine if the client is connected with the server.
					return "Pong!\n";
				}
				case "rename": { // Renames the player.
					String name = message.substring(String.format("/%s ", command).length());
					sender.setName(name);
					return String.format("Renamed to \"%s\".\n", name);
				}
				case "terminate": { // Terminates the client; closes it on the server side, which sends a termination message to close it on the client side.
					sender.send("CLIENT>>> TERMINATE");
					sender.close();
					break;
				}
				case "settings": {
					if(args.length < 3) return null; // shouldn't happen, but ignore it if it does
					if(sender != this.host) return null; // only host can change settings
					switch(args[1]) {
						case "language": settings.setLanguage(args[2]); break;
						case "time": settings.setTime(Integer.parseInt(args[2])); break;
						case "gamemode": settings.setGamemode(args[2]); break;
					}
					break;
				}
				default: { // Unknown.
					return "Unknown command.\n";
				}
			}
		} else {
			// The user sent an arbitrary piece of text; this can be a text message or a word response to the game.
			// Whatever it is, reflect it back to the rest of the clients.

			// If a game is going, consider the first word to be a guess.
			if(this.gameInProgress) {
				String result = game.findWord(args[0].toLowerCase(), sender.getID());
				return result;
			}
			//broadcast(String.format("<%s> %s\n", sender.getName(), message));
		}
		return null;
	}

	/**
	 * Broadcasts a string to all connected players.
	 * @param message The string to broadcast
	 */
	public void broadcast(String message) {
		System.out.print(message);
		for(Player player : this.players) {
			if(player == null) continue;
			player.send(message);
		}
	}

	/**
	 * Sets the host to the next available player.
	 */
	public void resetHost() {
		for(Player player : this.players) {
			if(player != null) {
				this.host = player;
				this.host.send("/notifyAsHost");
				return;
			}
		}
		System.out.println("Server empty... :(");
		this.host = null;
	}

	/**
	 * Gets a player by their ID.
	 * @param id
	 * @return
	 */
	public Player getPlayerByID(int id) {
		for(Player p : this.players) {
			if(p.getID() == id) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Smaller version of the Player class in the TicTacToeServer example.
	 * <p>
	 * This is a *representation* of a Player on the servers side. Private class so to remain in scope of the servers commands.
	 */
	private class Player implements Runnable {
		private String name;
		private int id; // This is just a continually incrementing ID for the player.
		private Socket socket;
		private ObjectInputStream input;
		private ObjectOutputStream output;
		private boolean closed;

		/**
		 * Constructs a player.
		 * @param socket The socket the player connected with.
		 * @param id The ID of the player.
		 * @throws IOException Thrown if there's an issue grabbing the input/output streams.
		 */
		public Player(Socket socket, int id) throws IOException {
			this.socket = socket;
			this.id = id;
			this.name = "Player " + id;
			this.closed = false;

			try {
				// Obtain input and output streams from the socket.
            	this.output = new ObjectOutputStream(this.socket.getOutputStream());
				this.output.flush(); // Flush output buffer to send header information.
				this.input = new ObjectInputStream(this.socket.getInputStream());
        	} catch(IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

		/**
		 * Polls for messages from <code>this</code> client in another thread, and deals with them accordingly.
		 */
		public void run() {
			try {
				// Poll for messages until the user has sent one, then process it.
				do {
					try {
						// Read in a new message, process an initial response.
						String message = (String) input.readObject();
						String response = processMessage(this, message);
						if (response != null) {
							send(response); // If the response isn't null, assume its a message for the sender (such as a command response) and send it directly to them.
						}
					} catch (ClassNotFoundException ex) {
						// Thrown from input.readObject() if it cant cast the provided object to a string.
						System.err.println("An unknown object type was received.");
						ex.printStackTrace();
					} catch (IOException ex) {
						// Thrown from input.readObject(). If it's a SocketException, assume we've lost connection and close.
						if(ex instanceof SocketException) {
							close();
						}
					}
				} while (!closed);
			} finally {
				// The connections been terminated, or a terrible error of another sort has come up.
			    try {
					this.socket.close(); // Close the socket on the server.

					// Remove this player instance from the player list.
					for(int i = 0; i < players.length; i++) {
						if(players[i] == this) {
							players[i] = null;
							break;
						}
					}

					if(this == host) {
						// The host got disconnected! Reset the host to another player.
						resetHost();
					}
					broadcast(String.format("%s disconnected.\n", this.name));
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		}

		/**
		 * Sends a string to the player through the output stream.
		 * @param message The message to send.
		 */
		public void send(String message) {
			try {
				this.output.writeObject(message);
				this.output.flush();
			} catch(IOException ex) {
				System.err.println(String.format("Error occurred whilst trying to send something to %s;", this.name));
				ex.printStackTrace();
				System.exit(1);
			}
		}

		/**
		 * Gets the display name of the player.
		 * @return The display name of the player.
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * Sets the display name of the player.
		 * @param name The display name of the player.
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * Gets the players ID.
		 * @return The players ID.
		 */
		public int getID() {
			return this.id;
		}

		/**
		 * Closes the socket connection.
		 * Note that this method
		 */
		public void close() {
			this.closed = true;
		}
	}
}

/**
 * Misc. bugs;
 * - Host disconnecting whilst a decision is being made will kill the game.
 * - ???
 */
