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
	private TextField serverURL;

	@FXML
	private TextField nickname;

	@FXML
	private Button startButton;

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
}
