package client.scenes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.utils.ServerUtils;
import commons.Player;

import com.google.inject.Inject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class QuestionScreenSinglePlayerCtrl extends QuestionClass implements Initializable {
	/**
	 * Constructor for question screen in single player mode.
	 *
	 * @param mainCtrl the injected main controller.
	 * @param server the injected server.
	 */
	@Inject
	public QuestionScreenSinglePlayerCtrl(MainCtrl mainCtrl,ServerUtils server) {
		this.mainCtrl = mainCtrl;
		this.server = server;
	}

	/**
	 *  Move to the final screen.
	 */
	@Override
	public void showFinalScreen() {
		this.mainCtrl.showSinglePlayerFinalScreen();
	}

	@Override
	public Player getPlayer() {
		return (this.mainCtrl.getSinglePlayerPreGameCtrl()).getPlayer();
	}

	/**
	 * Getter method for the singleplayer screen
	 *
	 * @return the Scene assigned to singleplayer question screen.
	 */
	@Override
	public Scene getScene() {
		return this.mainCtrl.getSinglePlayerPreGameScreen();
	}

	/**
	 * This method shows the intermediate scene.
	 */
	public void showIntermediateScene() {
		IntermediateSceneCtrl intermediateSceneCtrl = this.mainCtrl.getIntermediateSceneCtrl();
		Stage primaryStage = this.mainCtrl.getPrimaryStage();

		intermediateSceneCtrl.setQuestionAnswer(this.mainCtrl.getNumberOfQuestionAnswered());
		intermediateSceneCtrl.setLabelPoint(this.mainCtrl.getPlayer().getPoint());
		intermediateSceneCtrl.setCurrentQuestionPointsEarned(this.mainCtrl.getCurrentPoint());
		primaryStage.setTitle("IntermediateScene");
		primaryStage.setScene(this.mainCtrl.getIntermediateScene());

		// This timeline will execute on another thread - run the count-down timer.
		intermediateSceneCtrl.setProgress(1f);

		Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), _e -> {
			intermediateSceneCtrl.decreaseProgress(0.25);
		}));
		timeLine.setCycleCount(4);
		timeLine.play();
		timeLine.setOnFinished(_e -> {
			try {
				this.mainCtrl.showQuestionScreen(true);     // the timeline finish its cycle.
			} catch (IOException err) {
				err.printStackTrace();
			}
		});
	}

	/**
	 * This method shows answer when player click on one of the three buttons.
	 *
	 * @param event the player click on the button.
	 */
	public void answerReturn(ActionEvent event) {
		Object source = event.getSource();
		timeStamp = getProgress();

		mainCtrl.clearButtons(this);

		if (answerButton1.equals(source)) {
			inputButton = answerButton1;
			inputText = null;
		} else if (answerButton2.equals(source)) {
			inputButton = answerButton2;
			inputText = null;
		} else if (answerButton3.equals(source)) {
			inputButton = answerButton3;
			inputText = null;
		} else if (textField.equals(source)) {
			inputButton = null;
			inputText = textField;
		}

		this.mainCtrl.updatePoints(inputButton, inputText, this);

		super.answerReturn(event); //TODO CHECK THIS
	}

	/**
	 * This method set up the color for the progress bar.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.progressBarTime.setStyle("-fx-accent: #00FF00");
		super.initialize();
	}

	/**
	 * 	Decreases the progress bar by a certain, given, amount
	 *
	 * @param amount the amount to be decreased
	 */
	public void decreaseProgress(double amount) {
		this.progress -= amount;
		this.progressBarTime.setProgress(this.progress);
	}

	/**
	 * This method returns the progress
	 *
	 * @return the progress.
	 */
	public double getProgress() {
		return this.progress;
	}

	/**
	 * This method set up the progress
	 *
	 * @param progress the starting number of for the progress bar
	 *                    (basically progress will be set up 1).
	 */
	public void setProgress(double progress) {
		this.progress = progress;
		this.progressBarTime.setProgress(progress);
	}

	/**
	 *  The method sets the CSS style of the first Answer Button
	 *
	 * @param style the CSS style that is to be applied
	 */
	public void setStyleAnswerButton1(String style) {
		answerButton1.setStyle(style);
	}

	/**
	 *  The method sets the CSS style of the second Answer Button
	 *
	 * @param style the CSS style that is to be applied
	 */
	public void setStyleAnswerButton2(String style) {
		answerButton2.setStyle(style);
	}

	/**
	 *  The method sets the CSS style of the third Answer Button
	 *
	 * @param style the CSS style that is to be applied
	 */
	public void setStyleAnswerButton3(String style) {
		answerButton3.setStyle(style);
	}

	/**
	 * 	Getter method for timeStamp
	 *
	 * @return The last time when the user clicked on an answer
	 */
	public double getTimeStamp() {
		return timeStamp;
	}

	/**
	 * 	Getter method for inputButton
	 *
	 * @return the button that was last clicked by the Player
	 */
	public Button getInputButton() {
		return inputButton;
	}

	/**
	 * 	Getter method for inputText
	 *
	 * @return the text that was last entered by the player
	 */
	public TextField getInputText() {
		return inputText;
	}

	/**
	 * 	Setter method for inputText
	 *
	 * @param text The value that is to be assigned to inputText
	 */
	public void setInputText(TextField text) {
		inputText = text;
	}

	/**
	 * 	Setter method for inputButton
	 *
	 * @param button The button that is to be assigned to inputButton
	 */
	public void setInputButton(Button button) {
		inputButton = button;
	}

	/**
	 * 	The method sets the CSS style of the answer Label in the estimate game mode
	 *
	 * @param style the CSS style that is to be applied
	 */
	public void setEstimateAnswerStyle(String style) {
		estimateAnswer.setStyle(style);
	}

	/**
	 * 	Writes a certain string to the Label estimateAnswer
	 *
	 * @param text The text that is to be written to the label
	 */
	public void setEstimateAnswerLabel(String text) {
		estimateAnswer.setText(text);
	}
}