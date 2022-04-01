package client.scenes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class IntermediateSceneCtrl implements Initializable {
	private final MainCtrl mainCtrl;

	@FXML
	private ProgressBar timeUntilNextQuestion;

	@FXML
	private Label playerPoints;

	@FXML
	private Label questionsAnswered;

	@FXML
	private Label currentQuestionPointsEarned;

	double progress = 1;

	/**
	 * Constructor for intermediate scene controller.
	 * @param mainCtrl The injected main controller.
	 */
	@Inject
	public IntermediateSceneCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
	}

	public void setLabelPoint(int point) {
		this.playerPoints.setText(Integer.toString(point));
	}

	public void setQuestionAnswer(int numberQuestion) {
		this.questionsAnswered.setText(numberQuestion + "/20");
	}

	public void setCurrentQuestionPointsEarned(int point) {
		this.currentQuestionPointsEarned.setText(point + "/1000");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.timeUntilNextQuestion.setStyle("-fx-accent: #00FF00");
	}

	/**
	 * Decreases the progess in the progress bar by a certain, given, amount.
	 * @param amount The amount of progress that the bar loses.
	 */
	public void decreaseProgress(double amount) {
		this.progress -= amount;
		this.timeUntilNextQuestion.setProgress(this.progress);
	}

	/**
	 * Getter method for progress of the progress bar.
	 * @return The progress.
	 */
	public double getProgress() {
		return this.progress;
	}

	/**
	 * Set the progress of the time until the next question.
	 * @param progress The progress.
	 */
	public void setProgress(double progress) {
		this.progress = progress;
		this.timeUntilNextQuestion.setProgress(progress);
	}

	/**
	 * Changes the screen to leaderboard.
	 * @throws IOException If something went wrong with the socket.
	 * @throws ClassNotFoundException If the class was not found.
	 */
	public void changeToIntLeaderboard() throws IOException, ClassNotFoundException {
		this.mainCtrl.changeToLeaderboard();
	}
}