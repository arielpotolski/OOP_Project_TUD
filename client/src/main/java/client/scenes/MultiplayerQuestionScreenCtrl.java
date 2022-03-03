package client.scenes;

import com.sun.javafx.geom.Ellipse2D;
import com.sun.javafx.geom.Rectangle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class MultiplayerQuestionScreenCtrl {

	@FXML
	private Rectangle timeLeftBar;

	@FXML
	private Button answerButton1;

	@FXML
	private Button answerButton2;

	@FXML
	private Button answerButton3;

	@FXML
	private Ellipse2D lessTimeJoker;

	@FXML
	private Ellipse2D doublePointsJoker;

	@FXML
	private Ellipse2D eliminateAnswerJoker;

	@FXML
	private ImageView imageFirst;

	@FXML
	private ImageView imageSecond;
}
