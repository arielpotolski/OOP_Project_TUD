package client.scenes;

import client.utils.ServerUtils;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MultiplayerPreGameCtrl {
	private final ServerUtils server;
	private final MainCtrl mainCtrl;

	@FXML
	private TextField uRLTextField;

	@FXML
	private TextField nicknameTextField;

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
}
