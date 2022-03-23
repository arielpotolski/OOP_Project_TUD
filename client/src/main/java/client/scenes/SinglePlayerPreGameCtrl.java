package client.scenes;

import java.io.IOException;

import client.utils.ServerUtils;
import commons.Player;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SinglePlayerPreGameCtrl {
	public ServerUtils server;
	private MainCtrl mainCtrl;
	private String url;
	private String nickName;
	private Player player;

	@FXML
	private TextField serverURL;

	@FXML
	private TextField nickname;

	@FXML
	private Button startButton;

	/**
	 * Constructor for single player pre-game controller.
	 *
	 * @param mainCtrl the injected main controller.
	 * @param server the injected server.
	 */
	@Inject
	public SinglePlayerPreGameCtrl(MainCtrl mainCtrl, ServerUtils server) {
		this.mainCtrl = mainCtrl;
		this.server = server;
	}

	/**
	 * the method change the single player pre-game screen to question screen.
	 */
	public void changeToQuestionScreen() throws IOException {
		this.url = this.serverURL.getText();
		this.nickName = this.nickname.getText();
		this.player = new Player(this.nickName,0);
		this.server = new ServerUtils(this.url);
		this.mainCtrl.setServer(this.server);
		this.mainCtrl.getQuestions();
		this.mainCtrl.showQuestionScreenSinglePlayer();
	}

	/**
	 * the method return a player
	 *
	 * @return a player
	 */
	public Player getPlayer() {
		return this.player;
	}

	public void jumpToSplashScreen() {
		this.mainCtrl.showSplashScreen();
	}
}