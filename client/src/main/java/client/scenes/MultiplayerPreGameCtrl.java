package client.scenes;

import java.util.Optional;

import client.utils.ServerUtils;
import commons.LobbyResponse;
import commons.Player;

import com.google.inject.Inject;
import jakarta.ws.rs.ProcessingException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class MultiplayerPreGameCtrl {
	private final ServerUtils server;
	private final MainCtrl mainCtrl;
	private Player player;

	@FXML
	private TextField serverURL;

	@FXML
	private TextField nickname;

	@FXML
	private Button enterButton;

	/**
	 * Constructor for multiplayer pre-game controller
	 *
	 * @param server the injected server.
	 * @param mainCtrl the injected main controller.
	 */
	@Inject
	public MultiplayerPreGameCtrl(ServerUtils server, MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
		this.server = server;
	}

	public void jumpToSplashScreen() {
		this.mainCtrl.showSplashScreen();
	}

	/**
	 * This runs when the ENTER button is pressed.
	 * Sends a GET /lobby/register/ request to the server which registers
	 * this client's interest in the multiplayer game.
	 *
	 * If the client tries joining with a name that is already in the lobby it will
	 * receive a 400 BAD REQUEST. They should be notified that the name is already in use.
	 *
	 * After a successful request, this method sets the server URL in the ServerUtils
	 * of MainCtrl and moves to the waiting screen.
	 */
	public void joinLobby() {
		String url = this.serverURL.getText();
		String name = this.nickname.getText();
		this.player = new Player(name,0);

		ServerUtils serverUtils = new ServerUtils(url);

		Optional<LobbyResponse> maybeResponse;
		try {
			maybeResponse = serverUtils.connectToLobby(name);
		} catch (ProcessingException err) {
			// Alert the user if sending the request failed.
			Alert alert = new Alert(
				Alert.AlertType.ERROR,
				"Could not contact the server. Is your URL correct?",
				ButtonType.OK
			);
			alert.showAndWait();
			return;
		}

		if (maybeResponse.isPresent()) {
			this.mainCtrl.setServer(serverUtils);
			this.mainCtrl.showWaitingScreen(maybeResponse.get());
		} else {
			// Tell user the name is already taken.
			Alert alert = new Alert(
				Alert.AlertType.INFORMATION,
				"That name is already in use",
				ButtonType.OK
			);
			alert.showAndWait();
		}
	}

	/**
	 * Getter method for the player.
	 * @return the player.
	 */
	public Player getPlayer() {
		return this.player;
	}
}