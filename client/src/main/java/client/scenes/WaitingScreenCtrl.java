package client.scenes;

import java.util.Optional;
import java.util.Set;

import client.utils.ServerUtils;
import commons.LobbyResponse;
import commons.MessageModel;
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
	private final MainCtrl mainCtrl;
	private ServerUtils serverUtils;
	private static final long REFRESH_DELAY = 500;
	private int port;

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
	 * Start a thread that spams the server with GET /lobby/refresh/ requests which will be used to
	 * update the list of players in the lobby as well as detect when a game starts.  Once the
	 * thread has detected a game start it calls `gameBegins()` with the acquired port.
	 */
	public void beginActiveRefresh() {
		this.serverUtils = this.mainCtrl.getServer();

		Thread thread = new Thread(() -> {
			boolean gameStarted = false;
			this.port = -1;
			while (!gameStarted) {
				// Keep refreshing our interest in the lobby.
				Optional<LobbyResponse> maybeLobbyResponse = this.serverUtils.refreshLobby();
				if (maybeLobbyResponse.isPresent()) {
					LobbyResponse response = maybeLobbyResponse.get();
					gameStarted = response.gameStarted();
					this.port = response.tcpPort();

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

				// Sleep the thread for half a second to avoid a denial of service attack on the
				// server.
				try {
					Thread.sleep(REFRESH_DELAY);
				} catch (InterruptedException err) {
					err.printStackTrace();
					return;
				}
			}
			// Game has started.
			Platform.runLater(this::gameBegins);
		});
		thread.start();
	}

	/**
	 * Create a TCP connection to the server and move to the question screen.
	 */
	private void gameBegins() {
		try {
			this.serverUtils.makeConnection(this.port);

			// Set the same unique seed to all players inside the lobby.
			this.mainCtrl.setSeed(this.port);
			this.mainCtrl.getQuestions();

			this.serverUtils.registerForMessages(
				"/message/receive/" + this.port,
				MessageModel.class,
				messageModel -> this.mainCtrl.renderTheMessageInTheChatBox(
					messageModel.getMessage(), messageModel.getNickname()
				)
			);
			this.mainCtrl.setGameIdInMultiplayerQuestionScreen(this.port);

			this.mainCtrl.startMessageReceiverThread();
			// Move to game screen.
			this.mainCtrl.resetNumberOfQuestionsAnswered();
			this.mainCtrl.showQuestionScreen(false);
			this.mainCtrl.setUpJokers();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	/**
	 * This runs when the START button is pressed.  Sends a GET /lobby/start/ request which starts
	 * the game on the server.  The next time client refreshes they will be given the port to
	 * connect to.  (See `beginActiveRefresh()`)
	 */
	public void startGame() {
		this.serverUtils.startMultiplayerGame();
	}
}