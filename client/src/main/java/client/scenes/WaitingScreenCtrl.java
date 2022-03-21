package client.scenes;

import java.io.IOException;
import java.util.Optional;

import client.utils.ServerUtils;
import commons.LobbyResponse;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

	@Inject
	public WaitingScreenCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
	}

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

					// TODO Update list of players in lobby with `response.playersInLobby()`.
				}
			}
			// Game has started.
			gameBegins(port);
		});
		thread.start();
	}

	private void gameBegins(int port) {
		try {
			this.serverUtils.makeConnection(port);
			// TODO Move to game screen.
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	public void startGame() {
		this.serverUtils.startMultiplayerGame();
	}
}
