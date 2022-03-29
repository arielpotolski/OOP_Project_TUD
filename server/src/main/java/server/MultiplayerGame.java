package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import commons.Connection;
import commons.messages.ErrorMessage;
import commons.messages.JoinMessage;
import commons.messages.JokerMessage;
import commons.messages.Message;
import commons.messages.MessageType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiplayerGame extends Thread {
	/**
	 * A record for storing the information for each player in the game.
	 */
	private record Player(Connection connection, String name) {}

	/**
	 * Information about the players in the lobby.
	 * This is only used when waiting for the initial TCP connections.
	 */
	private final HashMap<String, LobbyPlayer> lobbyPlayers;
	/**
	 * A list of players in the multiplayer game.
	 */
	private final List<Player> players;
	/**
	 * The TCP socket each client connects to.
	 */
	private final ServerSocket serverSocket;
	/**
	 * Logger for this game.
	 */
	private final Logger logger;

	public MultiplayerGame(ServerSocket serverSocket, HashMap<String, LobbyPlayer> players) {
		this.serverSocket = serverSocket;
		this.lobbyPlayers = players;
		this.players = new ArrayList<>();
		this.logger = LoggerFactory.getLogger(MultiplayerGame.class);
	}

	@Override
	public void run() {
		try {
			waitForEveryoneToJoin();
			// TODO begin game
			// TODO send out questions to players
			// TODO track game progress
			// TODO send leaderboard
			for(Player player : players) {
				receiveMessageFromThePlayer(player);
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	/**
	 * Waits for all players from the lobby to create
	 * TCP connections to the server.
	 *
	 * @throws IOException When something goes wrong with sockets.
	 * @throws ClassNotFoundException When receiving an unrecognized message class.
	 */
	private void waitForEveryoneToJoin() throws IOException, ClassNotFoundException {
		this.logger.debug(String.format("expecting %d players", this.lobbyPlayers.size()));
		// We wait for every player to initiate a TCP connection.
		// Then we wait to receive their JOIN message where they identify themselves.
		for (int i = 0; i < this.lobbyPlayers.size(); i++) {
			// `serverSocket.accept()` blocks until a connection is made.
			// If a player does not make a connection after the game starts
			// the thread could just stall indefinitely.
			// We were told we can trust our clients, so we can ignore this issue.
			Connection connection = Connection.fromSocket(serverSocket.accept());

			// Receive the first message from the client.
			JoinMessage message = (JoinMessage) connection.receiveType(MessageType.JOIN);
			String name = message.getName();
			this.logger.info(name + " has joined the game");

			if (!this.lobbyPlayers.containsKey(name)) {
				// A player joined who was not in the lobby.
				this.logger.error(name + " was not in the original lobby");
				connection.send(new ErrorMessage("you were not in the lobby"));
				// Do one more iteration of the loop.
				i--;
				continue;
			}

			// Save player.
			this.players.add(new Player(connection, name));
		}
	}

	private void receiveMessageFromThePlayer(Player player) {
		Thread thread = new Thread(() -> {
			while (true) {
				Message message = null;
				try {
					message = player.connection().receive();
				} catch (IOException | ClassNotFoundException err) {
					err.printStackTrace();
				}
				switch (message.getType()) {
					case JOKER -> {
						try {
							handleJokerMessage(player, (JokerMessage) message);
						} catch (IOException err) {
							err.printStackTrace();
						}
					}
				}
			}
		});
		thread.start();
	}

	private void handleJokerMessage(Player player, JokerMessage message) throws IOException {
		this.logger.debug(player.toString() + "sent a joker message");
		// TODO track if player used joker
		// TODO handle other stuff
		replyMessageToClient(message, Optional.of(player));
	}

	private void replyMessageToClient(JokerMessage message, Optional<Player> exclude)
			throws IOException {
		for (Player player: this.players) {
			if (exclude.isEmpty() || exclude.get() != player) {
				player.connection.send(message);;
			}
		}
	}
}
