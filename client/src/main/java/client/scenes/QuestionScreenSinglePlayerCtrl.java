package client.scenes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import commons.Player;

import com.google.inject.Inject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class QuestionScreenSinglePlayerCtrl extends QuestionClass implements Initializable {
	/**
	 * Constructor for question screen in single player mode.
	 * @param mainCtrl The injected main controller.
	 */
	@Inject
	public QuestionScreenSinglePlayerCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
		this.server = mainCtrl.getServer();
	}

	/**
	 * Move to the final screen.
	 */
	@Override
	public void showFinalScreen() {
		this.mainCtrl.showSinglePlayerFinalScreen();
	}

	@Override
	public Player getPlayer() {
		return this.mainCtrl.getSinglePlayerPreGameCtrl().getPlayer();
	}

	/**
	 * Getter method for the singleplayer screen.
	 * @return The scene assigned to singleplayer question screen.
	 */
	@Override
	public Parent getScene() {
		return this.mainCtrl.getSinglePlayerPreGameScreen();
	}

	/**
	 * This method shows the intermediate scene.
	 */
	public void showIntermediateScene() {
		IntermediateSceneCtrl intermediateSceneCtrl = this.mainCtrl.getIntermediateSceneCtrl();
		Stage primaryStage = this.mainCtrl.getPrimaryStage();
		Scene primaryScene = this.mainCtrl.getPrimaryScene();

		intermediateSceneCtrl.setQuestionAnswer(this.mainCtrl.getNumberOfQuestionsAnswered());
		intermediateSceneCtrl.setLabelPoint(this.mainCtrl.getPlayer().getPoints());
		intermediateSceneCtrl.setCurrentQuestionPointsEarned(this.mainCtrl.getCurrentPoints());
		primaryStage.setTitle("Intermediate Scene");
		primaryScene.setRoot(this.mainCtrl.getIntermediateScene());

		// This timeline will execute on another thread - run the count-down timer.
		intermediateSceneCtrl.setProgress(1f);

		Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), _e ->
			intermediateSceneCtrl.decreaseProgress(0.25)
		));
		timeLine.setCycleCount(4);
		timeLine.play();
		timeLine.setOnFinished(_e -> {
			try {
				this.mainCtrl.showQuestionScreen(true); // The timeline finish its cycle.
			} catch (IOException err) {
				err.printStackTrace();
			}
		});
	}

	/**
	 * This method shows answer when player click on one of the three buttons.
	 * @param event the player click on the button.
	 */
	public void answerReturn(ActionEvent event) throws IOException {
		super.answerReturn(event); // TODO: Check this
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
	 * This method returns the progress.
	 * @return The progress.
	 */
	public double getProgress() {
		return this.progress;
	}

	/**
	 * This method set up the progress.
	 * @param progress The starting number of for the progress bar (basically progress will be set
	 *                 up 1).
	 */
	public void setProgress(double progress) {
		this.progress = progress;
		this.progressBarTime.setProgress(progress);
	}

	/**
	 * The method sets the CSS style of the first Answer Button.
	 * @param style The CSS style that is to be applied.
	 */
	public void setStyleAnswerButton1(String style) {
		this.answerButton1.setStyle(style);
	}

	/**
	 * The method sets the CSS style of the second Answer Button.
	 * @param style The CSS style that is to be applied.
	 */
	public void setStyleAnswerButton2(String style) {
		this.answerButton2.setStyle(style);
	}

	/**
	 * The method sets the CSS style of the third Answer Button.
	 * @param style The CSS style that is to be applied.
	 */
	public void setStyleAnswerButton3(String style) {
		this.answerButton3.setStyle(style);
	}

	/**
	 * Getter method for timestamp.
	 * @return The last time when the user clicked on an answer.
	 */
	public double getTimestamp() {
		return this.timestamp;
	}

	/**
	 * Getter method for inputButton.
	 * @return The button that was last clicked by the olayer.
	 */
	public Button getInputButton() {
		return this.inputButton;
	}

	/**
	 * Getter method for inputText.
	 * @return The text that was last entered by the player.
	 */
	public TextField getInputText() {
		return this.inputText;
	}

	/**
	 * Setter method for inputText.
	 * @param text The value that is to be assigned to inputText.
	 */
	public void setInputText(TextField text) {
		this.inputText = text;
	}

	/**
	 * Setter method for inputButton.
	 * @param button The button that is to be assigned to inputButton.
	 */
	public void setInputButton(Button button) {
		this.inputButton = button;
	}

	/**
	 * The method sets the CSS style of the answer Label in the estimate game mode.
	 * @param style The CSS style that is to be applied.
	 */
	public void setEstimateAnswerStyle(String style) {
		this.estimateAnswer.setStyle(style);
	}

	/**
	 * Writes a certain string to the Label estimateAnswer.
	 * @param text The text that is to be written to the label.
	 */
	public void setEstimateAnswerLabel(String text) {
		this.estimateAnswer.setText(text);
	}
}