package client.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

public class MultiplayerQuestionScreenCtrl {
	@FXML
	private ProgressBar progressBarTime;

	@FXML
	private Button answerButton1;

	@FXML
	private Button answerButton2;

	@FXML
	private Button answerButton3;

	@FXML
	private Ellipse lessTimeJoker;

	@FXML
	private Ellipse doublePointsJoker;

	@FXML
	private Ellipse eliminateAnswerJoker;

	@FXML
	private ImageView imageQuestion;

	@FXML
	private ImageView imageFirst;

	@FXML
	private ImageView imageSecond;

	@FXML
	private ImageView imageThird;

	@FXML
	private Circle innerCircleEmoticon;

	@FXML
	private Circle innerCircleTutorial;

	@FXML
	private Pane reactionPane;

	/**
	 *  Decreases the progress of progress bar (aka the timer)
	 */
	public void decreaseProgress() {
		// TODO
	}
}