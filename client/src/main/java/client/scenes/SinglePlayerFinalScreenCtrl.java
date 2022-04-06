package client.scenes;

import client.utils.ServerUtils;
import client.utils.SoundHandler;
import commons.Player;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Polygon;

public class SinglePlayerFinalScreenCtrl {
	MainCtrl mainCtrl;
	ServerUtils server;

	@FXML
	private Label correctAnswers;

	@FXML
	private Label totalScore;

	@FXML
	private Button playAgain;

	@FXML
	private Button multiplayer;

	@FXML
	private Polygon backArrow;

	/**
	 * Constructor for single player final screen controller.
	 * @param mainCtrl The injected main controller.
	 */
	@Inject
	public void singlePlayerFinalScreenCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
		this.server = mainCtrl.getServer();
	}

	/**
	 * This method sets up the number of correct answers after the player finished the game.
	 * @param numberOfCorrectAnswers The number of correct answers.
	 */
	public void setCorrectAnswers(int numberOfCorrectAnswers) {
		this.correctAnswers.setText(numberOfCorrectAnswers + "/20");
	}

	/**
	 * This method sets up the point of player after the player finished the game.
	 * @param point the point of the player
	 */
	public void setTotalScore(int point) {
		this.totalScore.setText(Integer.toString(point));
	}

	/**
	 * This method changes the final screen to pre-game screen.
	 */
	public void jumpToPreGameScene() {
		SoundHandler.clickSound();
		this.mainCtrl.showSinglePlayerPreGameScreen();
	}

	/**
	 * This method changes the final screen to splash screen.
	 */
	public void jumpToSplashScreen() {
		this.mainCtrl.showSplashScreen();
	}

	/**
	 * This method adds a player to database.
	 * @param player A player.
	 */
	public void addPlayer(Player player) {
		this.server.addPlayer(player);
	}
}