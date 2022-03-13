package client.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.shape.Rectangle;

public class IntermediateSceneCtrl {

	@FXML
	private ProgressBar timeUntilNextQuestion;

	@FXML
	private Label playerPoints;

	@FXML
	private Label questionsAnswered;

	@FXML
	private Label currentQuestionPointsEarned;

}
