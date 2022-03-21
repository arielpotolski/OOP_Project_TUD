package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import commons.Connection;
import commons.messages.ErrorMessage;
import commons.messages.JoinMessage;
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
			// TODO begin game
			// TODO send out questions to players
			// TODO track game progress
			// TODO send leaderboard
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

}
