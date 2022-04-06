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
import commons.messages.KillerMessage;
import commons.messages.LeaderboardMessage;
import commons.messages.Message;
import commons.messages.MessageType;
import commons.messages.PointMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiplayerGame extends Thread {
	/**
	 * A record for storing the information for each player in the game.
	 */
	private record PlayerConnection(Connection connection, String name) {}

	/**
	 * Information about the players in the lobby.
	 * This is only used when waiting for the initial TCP connections.
	 */
	private final HashMap<String, LobbyPlayer> lobbyPlayers;

	/**
	 * Scores of the players with relation to their nickname
	 */
	private final HashMap<String, Integer> scores;

	/**
	 * A list of players and their socket connections.
	 */
	private final List<PlayerConnection> players;

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
		this.scores = new HashMap<>();
	}

	@Override
	public void run() {
		try {
			this.waitForEveryoneToJoin();
			this.players.forEach(this::receiveMessageFromThePlayer);
			// TODO begin game
			// TODO send out questions to players
			// TODO track game progress
			this.sendMessageToAllPlayers(new LeaderboardMessage(new HashMap<>(this.scores)));
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	/**
	 * Waits for all players from the lobby to create TCP connections to the server.
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
			Connection connection = Connection.fromSocket(this.serverSocket.accept());

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
			this.players.add(new PlayerConnection(connection, name));
		}
		this.initializeScore();
	}

	/**
	 * Initializes everybody's score to 0.
	 */
	private void initializeScore() {
		for (PlayerConnection player : this.players) {
			this.scores.put(player.name(), 0);
		}
	}

	/**
	 * Receives a message from the player.
	 * @param player The sender of the message.
	 */
	private void receiveMessageFromThePlayer(PlayerConnection player) {
		Thread thread = new Thread(() -> {
			message_loop: while (true) {
				try {
					Message message = player.connection().receive();
					this.logger.debug(String.format(
						"Received message (%s) from %s",
						message.getType().toString(),
						player.name()
					));
					switch (message.getType()) {
						case JOKER -> this.handleJokerMessage(player, (JokerMessage) message);
						case POINTS -> this.handlePointMessage(player, (PointMessage) message);
						case KILLER -> {
							this.handleKillerMessage(player, (KillerMessage) message);
							break message_loop;
						}
						default -> this.logger.error("Received an unexpected message: " + message);
					}
				} catch (IOException | ClassNotFoundException err) {
					err.printStackTrace();
					this.removePlayer(player);
				}
			}
		});
		thread.start();
	}

	private void handleJokerMessage(PlayerConnection player, JokerMessage message) {
		// TODO track who used joker
		this.sendMessageToAllPlayers(message, player);
	}

	private void handlePointMessage(PlayerConnection player, PointMessage message) {
		this.scores.put(
			player.name(),
			message.getPoints() + this.scores.get(player.name())
		);
		this.haveAnswered.put(
			player.name(),
			this.haveAnswered.get(player.name()) + 1
		);
		// Send an updated leaderboard to all players.
		this.sendMessageToAllPlayers(new LeaderboardMessage(new HashMap<>(this.scores)));
	}

	private void handleKillerMessage(PlayerConnection player, KillerMessage message) {
		try {
			// Send back their killer message.
			if (message.shouldSendBack()) {
				player.connection().send(new KillerMessage(false));
			}
		} catch (IOException err) {
			// Don't have to do anything. Client is closing anyway.
			this.logger.debug("Error while sending KillerMessage: " + err.getMessage());
		}
		this.removePlayer(player);
	}

	/**
	 * Sends a message to all players.
	 * @param message The message for the players.
	 * @param exclude Exclude a specific player.
	 *                Pass in `null` if you do not want to exclude any player.
	 */
	private void sendMessageToAllPlayers(Message message, PlayerConnection exclude) {
		for (PlayerConnection player : this.players) {
			if (!player.equals(exclude)) {
				try {
					player.connection().send(message);
				} catch (IOException err) {
					this.logger.debug(String.format(
						"Error while sending message to player: %s\n%s",
						player.name(),
						err.getMessage()
					));
					this.removePlayer(player);
				}
			}
		}
	}

	/**
	 * Sends a message to all players.
	 * @param message The message for the players.
	 */
	private void sendMessageToAllPlayers(Message message) {
		this.sendMessageToAllPlayers(message, null);
	}

	/**
	 * Remove a player. Called when a player disconnects or otherwise loses connection.
	 * @param player PlayerConnection object of the player to remove.
	 */
	private void removePlayer(PlayerConnection player) {
		this.logger.info(
			String.format("Removing player %s from the game.", player.name())
		);
		this.players.remove(player);
		this.haveAnswered.remove(player.name());
		// Uncomment the line below if scores should also be removed when a player leaves.
		// this.scores.remove(player.name());
	}
}