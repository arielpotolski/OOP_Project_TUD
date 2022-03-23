package client.scenes;

import java.util.Optional;
import java.util.Set;

import client.utils.ServerUtils;
import commons.LobbyResponse;
import static commons.Utility.contentsEqual;

import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class WaitingScreenCtrl {
	private MainCtrl mainCtrl;
	private ServerUtils serverUtils;

	@FXML
	private AnchorPane peopleInTheLeaderBoardPane;

	@FXML
	private Button startButton;

	@FXML
	private Label peopleInTheRoomLabel;

	@FXML
	private ListView<String> usersInLobbyView;

	@Inject
	public WaitingScreenCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
	}

	/**
	 * Start a thread that spams the server with GET /lobby/refresh/
	 * requests which will be used to update the list of players in the lobby
	 * as well as detect when a game starts. Once the thread has detected
	 * a game start it calls `gameBegins()` with the acquired port.
	 *
	 * @param firstResponse The first LobbyResponse from the server
	 *                      after a GET /lobby/register/ request.
	 */
	public void beginActiveRefresh(LobbyResponse firstResponse) {
		this.serverUtils = mainCtrl.getServer();

		Thread thread = new Thread(() -> {
			boolean gameStarted = false;
			int port = -1;
			while (!gameStarted) {
				// Keep refreshing our interest in the lobby.
				Optional<LobbyResponse> maybeLobbyResponse = this.serverUtils.refreshLobby();
				if (maybeLobbyResponse.isPresent()) {
					LobbyResponse response = maybeLobbyResponse.get();
					gameStarted = response.gameStarted();
					port = response.tcpPort();

					/* Refresh the list of users in the lobby.  It's important to remember to clear
					 * the list every loop as if you don't you end up with an infinitely growing
					 * list of users.
					 */
					ObservableList<String> users = this.usersInLobbyView.getItems();
					Set<String> newUsers = response.playersInLobby();
					if (!contentsEqual(users, newUsers)) {
						Platform.runLater(() -> {
							users.removeAll(users);
							users.addAll(newUsers);
						});
					}
				}
			}
			// Game has started.
			gameBegins(port);
		});
		thread.start();
	}

	/**
	 * Create a TCP connection to the server and move to the question screen.
	 * @param port The port which the client should connect to for the game.
	 */
	private void gameBegins(int port) {
		try {
			this.serverUtils.makeConnection(port);
			// TODO Move to game screen.
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	/**
	 * This runs when the START button is pressed.
	 * Sends a GET /lobby/start/ request which starts
	 * the game on the server. The next time client
	 * refreshes they will be given the port to connect to.
	 * (See `beginActiveRefresh()`)
	 */
	public void startGame() {
		this.serverUtils.startMultiplayerGame();
	}
}
