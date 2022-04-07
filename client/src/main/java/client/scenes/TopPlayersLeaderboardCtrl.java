package client.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Polygon;
import javax.inject.Inject;

public class TopPlayersLeaderboardCtrl {
	private final MainCtrl mainCtrl;

	@FXML
	private Label nicknameFirst;

	@FXML
	private Label nicknameSecond;

	@FXML
	private Label nicknameThird;

	@FXML
	private Polygon backArrow;

	@FXML
	private Label back;

	@FXML
	private Polygon crown;

	@FXML
	private Button playAgain;

	/**
	 * Constructor for TopPlayersLeaderboardController.
	 * @param mainCtrl The injected main controller.
	 */
	@Inject
	public TopPlayersLeaderboardCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
	}

	/**
	 * This method takes the user directly to the next lobby in the same server, so that the player
	 * can play a new game directly.
	 */
	public void jumpToLobby() {
		this.mainCtrl.joinLobby();
	}

	public void jumpToSplashScreen() {
		this.mainCtrl.showSplashScreen();
	}
}