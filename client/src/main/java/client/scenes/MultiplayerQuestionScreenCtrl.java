package client.scenes;

import com.sun.javafx.geom.Rectangle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Ellipse;

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
	private Ellipse lessTimeJoker;

	@FXML
	private Ellipse doublePointsJoker;

	@FXML
	private Ellipse eliminateAnswerJoker;

	@FXML
	private ImageView imageFirst;

	@FXML
	private ImageView imageSecond;
}
