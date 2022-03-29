package client.scenes;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;

public class IntLeaderboardCtrl implements Initializable {
	@FXML
	private ProgressBar timeUntilNextQuestion;

	double progress = 1f;

	@Override
	public void initialize(URL location, ResourceBundle resources) {}

	/**
	 * Decreases the progess in the progress bar by a certain, given, amount.
	 * @param amount The amount of progress that the bar loses.
	 */
	public void decreaseProgress(double amount) {
		this.progress -= amount;
		this.timeUntilNextQuestion.setProgress(this.progress);
	}

	/**
	 * Setter method for the progress.
	 * @param amount The amount set to progress.
	 */
	public void setProgress(double amount) {
		this.progress = amount;
		this.timeUntilNextQuestion.setProgress(this.progress);
	}
}