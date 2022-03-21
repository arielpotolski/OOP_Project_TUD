package client.scenes;

import java.util.Optional;

import client.utils.ServerUtils;
import commons.LobbyResponse;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.NotImplementedException;

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

	public void joinLobby() {
		String url = this.serverURL.getText();
		String name = this.nickname.getText();

		ServerUtils serverUtils = new ServerUtils(url);
		Optional<LobbyResponse> maybeResponse = serverUtils.connectToLobby(name);
		if (maybeResponse.isPresent()) {
			this.mainCtrl.setServer(serverUtils);
			this.mainCtrl.showWaitingScreen(maybeResponse.get());
		} else {
			// TODO tell user that the name is not available or that something went wrong
			throw new NotImplementedException();
		}
	}
}
