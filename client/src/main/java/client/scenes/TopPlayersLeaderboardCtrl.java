package client.scenes;

import client.utils.ServerUtils;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Polygon;
import javax.inject.Inject;

public class TopPlayersLeaderboardCtrl {
	private MainCtrl mainCtrl;
	private ServerUtils server;

	@FXML
	private Label nicknameFirst;

	@FXML
	private Label nicknameSecond;

	@FXML
	private Label nicknameThird;

	@FXML
	private Polygon backArrow;

	@FXML
	private Polygon crown;

	@FXML
	private Button playAgain;


	/**
	 * Constructor for TopPlayersLeaderboardController.
	 * @param mainCtrl the injected main controller.
	 */
	@Inject
	public TopPlayersLeaderboardCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
		this.server = mainCtrl.getServer();
	}

	/**
	 * this method takes the user directly to the next lobby in the same server,
	 * so that the player can play a new game directly.
	 */
	public void jumpToLobby() {
		this.mainCtrl.joinLobby();
	}

	public void jumpToSplashScreen() {
		this.mainCtrl.showSplashScreen();
	}
}
