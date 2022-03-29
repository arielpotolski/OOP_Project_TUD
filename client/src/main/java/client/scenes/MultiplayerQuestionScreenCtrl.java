package client.scenes;

import java.io.IOException;

import commons.Player;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MultiplayerQuestionScreenCtrl extends QuestionClass{
	@FXML
	private Ellipse lessTimeJoker;

	@FXML
	private Ellipse doublePointsJoker;

	@FXML
	private Ellipse eliminateAnswerJoker;

	@FXML
	private Circle innerCircleEmoticon;

	@FXML
	private Pane reactionPane;

	@Override
	public void showFinalScreen() {
		this.mainCtrl.showMultiPlayerFinalScreen();
	}

	@Override
	public Player getPlayer() {
		return this.mainCtrl.getMultiplayerPreGameCtrl().getPlayer();
	}

	@Override
	public Scene getScene() {
		return this.mainCtrl.getMultiplayerQuestionScreen();
	}

	@Override
	public void initialize() {
		super.initialize();
	}

	/**
	 * This method shows the intermediate scene.
	 */
	@Override
	public void showIntermediateScene() {
		IntermediateLeaderboardCtrl intermediateLeaderboardCtrl =
				this.mainCtrl.getIntermediateLeaderboardCtrl();
		Stage primaryStage = this.mainCtrl.getPrimaryStage();

		intermediateLeaderboardCtrl.setProgress(1f);
		Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), _e -> {
			intermediateLeaderboardCtrl.decreaseProgress(0.25);
		}));

		primaryStage.setTitle("IntermediateScene");
		primaryStage.setScene(this.mainCtrl.getIntermediateLeaderboardScene());

		// This timeline will execute on another thread - run the count-down timer.
		intermediateLeaderboardCtrl.setProgress(1f);
		timeLine.setCycleCount(4);
		timeLine.play();
		timeLine.setOnFinished(_e -> {
			try {
				this.mainCtrl.showQuestionScreen(false); // the timeline finish its cycle.
			} catch (IOException err) {
				err.printStackTrace();
			}
		});
	}

	/**
	 * This method shows answer when player click on one of the three buttons.
	 * @param event The player click on the button.
	 */
	@Override
	public void answerReturn(ActionEvent event) {
		this.mainCtrl.clearButtons(this);
		super.answerReturn(event);
	}
}