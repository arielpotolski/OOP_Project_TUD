package client.scenes;

import java.io.IOException;

import client.Main;
import client.utils.ServerUtils;
import commons.Player;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SinglePlayerPreGameCtrl {
	public ServerUtils server;
	private MainCtrl mainCtrl;
	private Player player;

	@FXML
	private TextField nickname;

	@FXML
	private Button startButton;

	/**
	 * Constructor for single player pre-game controller.
	 *
	 * @param mainCtrl the injected main controller.
	 */
	@Inject
	public SinglePlayerPreGameCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
		this.server = mainCtrl.getServer();
	}

	/**
	 * the method change the single player pre-game screen to question screen.
	 */
	public void changeToQuestionScreen() throws IOException {
		setNickname();
		this.player = new Player(this.mainCtrl.getNickname(),0);
		this.mainCtrl.getQuestions();
		this.mainCtrl.showQuestionScreen(true);
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

	/**
	 * setter for the player's nickname from the MainCtrl nickname's field.
	 */
	public void setNickname() {
		this.mainCtrl.setNickname(this.nickname.getText());
	}
}