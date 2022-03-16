package client.scenes;

import java.net.URL;
import java.util.ResourceBundle;

import client.utils.ServerUtils;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;


public class IntermediateSceneCtrl implements Initializable {
	private MainCtrl mainCtrl;
	private ServerUtils server;

	@FXML
	private ProgressBar timeUntilNextQuestion;

	@FXML
	private Label playerPoints;

	@FXML
	private Label questionsAnswered;

	@FXML
	private Label currentQuestionPointsEarned;

	double progress = 1;

	@Inject
	/**
	 * Constructor for intermediate scene controller.
	 *
	 * @param mainCtrl the injected main controller.
	 * @param server the injected server.
	 */
	public IntermediateSceneCtrl(MainCtrl mainCtrl, ServerUtils server) {
		this.mainCtrl = mainCtrl;
		this.server = server;
	}

	public void setLabelPoint(int point) {
		playerPoints.setText(Integer.toString(point));
	}

	public void setQuestionAnswer(int numberQuestion) {
		questionsAnswered.setText(Integer.toString(numberQuestion) + "/20");
	}

	public void setCurrentQuestionPointsEarned(int point) {
		currentQuestionPointsEarned.setText(Integer.toString(point) + "/1000");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		timeUntilNextQuestion.setStyle("-fx-accent: #00FF00");
	}

	public void decreaseProgress() {
		progress -= 0.1;
		timeUntilNextQuestion.setProgress(progress);
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
		timeUntilNextQuestion.setProgress(progress);
	}
}
